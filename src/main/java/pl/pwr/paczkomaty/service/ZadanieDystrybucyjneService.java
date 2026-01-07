package pl.pwr.paczkomaty.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.paczkomaty.model.entity.*;
import pl.pwr.paczkomaty.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ZadanieDystrybucyjneService {

    @Autowired
    private ZadanieDystrybucyjneRepository zadanieRepository;

    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    @Autowired
    private SortowniaRepository sortowniaRepository;

    @Autowired
    private PrzesylkaRepository przesylkaRepository;

    @Autowired
    private StatusPrzesylkiRepository statusRepository;

    @Autowired
    private HistoriaStatusuRepository historiaRepository;

    public List<ZadanieDystrybucyjne> pobierzWszystkieZadania() {
        return zadanieRepository.findAll();
    }

    public Optional<ZadanieDystrybucyjne> znajdzZadanie(Integer id) {
        return zadanieRepository.findById(id);
    }

    public List<ZadanieDystrybucyjne> znajdzZadaniaKuriera(Integer kurierId) {
        return zadanieRepository.findByKurierId(kurierId);
    }

    public List<ZadanieDystrybucyjne> znajdzZadaniaPoStatusie(String status) {
        return zadanieRepository.findByStatus(status);
    }

    public ZadanieDystrybucyjne utworzZadanie(ZadanieDystrybucyjne zadanie) {
        if (zadanie.getStatus() == null) zadanie.setStatus("ZAPLANOWANE");
        if (zadanie.getDataStartu() == null) zadanie.setDataStartu(LocalDateTime.now());
        return zadanieRepository.save(zadanie);
    }

    public void edytujZadanie(Integer id, String typ, String status) {
        ZadanieDystrybucyjne zadanie = zadanieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brak zadania"));

        if(typ != null) zadanie.setTyp(typ);

        if(status != null) {
            if ("W_TRAKCIE".equals(status) && zadanie.getDataStartu() == null) {
                zadanie.setDataStartu(LocalDateTime.now());
            }
            if ("ZAKONCZONE".equals(status) && zadanie.getDataKonca() == null) {
                if (zadanie.getDataStartu() == null) zadanie.setDataStartu(LocalDateTime.now());
                zadanie.setDataKonca(LocalDateTime.now());
            }
            zadanie.setStatus(status);
        }
    }



    public void przydzielKuriera(Integer zadanieId, Integer kurierId) {
        ZadanieDystrybucyjne zadanie = zadanieRepository.findById(zadanieId)
                .orElseThrow(() -> new EntityNotFoundException("Brak zadania: " + zadanieId));

        Uzytkownik kurier = uzytkownikRepository.findById(kurierId)
                .orElseThrow(() -> new EntityNotFoundException("Brak kuriera: " + kurierId));

        if (!"KURIER".equals(kurier.getRola())) {
            throw new IllegalArgumentException("Użytkownik nie jest kurierem");
        }

        zadanie.setKurier(kurier);
        zadanieRepository.save(zadanie);
    }


    public void przydzielSortownie(Integer zadanieId, Integer sortowniaId) {
        ZadanieDystrybucyjne zadanie = zadanieRepository.findById(zadanieId)
                .orElseThrow(() -> new EntityNotFoundException("Brak zadania: " + zadanieId));

        Sortownia sortownia = sortowniaRepository.findById(sortowniaId)
                .orElseThrow(() -> new EntityNotFoundException("Brak sortowni: " + sortowniaId));

        zadanie.setSortownia(sortownia);
        zadanieRepository.save(zadanie);
    }


    public void dodajPrzesylkiDoZadania(Integer zadanieId, List<Integer> przesylkaIds) {
        ZadanieDystrybucyjne zadanie = zadanieRepository.findById(zadanieId)
                .orElseThrow(() -> new EntityNotFoundException("Brak zadania: " + zadanieId));

        if (zadanie.getPrzesylki() == null) {
            zadanie.setPrzesylki(new ArrayList<>());
        }

        for (Integer przesylkaId : przesylkaIds) {
            przesylkaRepository.findById(przesylkaId).ifPresent(przesylka -> {
                if (!zadanie.getPrzesylki().contains(przesylka)) {
                    if (przesylka.getZadania() == null) {
                        przesylka.setZadania(new ArrayList<>());
                    }
                    przesylka.getZadania().add(zadanie);
                    zadanie.getPrzesylki().add(przesylka);
                }
            });
        }
        zadanieRepository.save(zadanie);
    }


    public void usunPrzesylkeZZadania(Integer zadanieId, Integer przesylkaId) {
        ZadanieDystrybucyjne zadanie = zadanieRepository.findById(zadanieId)
                .orElseThrow(() -> new EntityNotFoundException("Brak zadania: " + zadanieId));

        if (zadanie.getPrzesylki() != null) {
            zadanie.getPrzesylki().removeIf(p -> p.getId().equals(przesylkaId));
        }
        zadanieRepository.save(zadanie);
    }


    public void wydajPrzesylkiKurierowi(Integer zadanieId, Integer uzytkownikId) {
        ZadanieDystrybucyjne zadanie = zadanieRepository.findById(zadanieId)
                .orElseThrow(() -> new EntityNotFoundException("Brak zadania: " + zadanieId));

        if (zadanie.getKurier() == null) {
            throw new IllegalStateException("Zadanie nie ma przypisanego kuriera");
        }

        StatusPrzesylki statusWTransporcie = znajdzStatusPoKodzie("W_TRANSPORCIE");

        Uzytkownik uzytkownik = uzytkownikId != null ?
                uzytkownikRepository.findById(uzytkownikId).orElse(null) : null;

        if (zadanie.getPrzesylki() != null) {
            for (Przesylka przesylka : zadanie.getPrzesylki()) {
                zmienStatusPrzesylki(przesylka, statusWTransporcie, uzytkownik,
                        "Wydano kurierowi: " + zadanie.getKurier().getLogin());
            }
        }

        zadanie.setStatus("W_TRAKCIE");
        if (zadanie.getDataStartu() == null) {
            zadanie.setDataStartu(LocalDateTime.now());
        }
        zadanieRepository.save(zadanie);
    }


    public void odbierzOdKuriera(Integer zadanieId, Integer uzytkownikId) {
        ZadanieDystrybucyjne zadanie = zadanieRepository.findById(zadanieId)
                .orElseThrow(() -> new EntityNotFoundException("Brak zadania: " + zadanieId));

        StatusPrzesylki statusWSortowni = znajdzStatusPoKodzie("W_SORTOWNI");

        Uzytkownik uzytkownik = uzytkownikId != null ?
                uzytkownikRepository.findById(uzytkownikId).orElse(null) : null;

        if (zadanie.getPrzesylki() != null) {
            for (Przesylka przesylka : zadanie.getPrzesylki()) {
                zmienStatusPrzesylki(przesylka, statusWSortowni, uzytkownik,
                        "Odebrano od kuriera w sortowni");
            }
        }

        zadanie.setStatus("ZAKONCZONE");
        zadanie.setDataKonca(LocalDateTime.now());
        zadanieRepository.save(zadanie);
    }


    public void zgloszenieWydania(Integer przesylkaId, Integer uzytkownikId) {
        Przesylka przesylka = przesylkaRepository.findById(przesylkaId)
                .orElseThrow(() -> new EntityNotFoundException("Brak przesyłki: " + przesylkaId));

        StatusPrzesylki statusGotowa = znajdzStatusPoKodzie("GOTOWA_DO_ODBIORU");

        Uzytkownik uzytkownik = uzytkownikId != null ?
                uzytkownikRepository.findById(uzytkownikId).orElse(null) : null;

        zmienStatusPrzesylki(przesylka, statusGotowa, uzytkownik,
                "Przesyłka gotowa do odbioru w paczkomacie docelowym");
    }


    public void zgloszenieDoPrzewozu(Integer przesylkaId, Integer uzytkownikId) {
        Przesylka przesylka = przesylkaRepository.findById(przesylkaId)
                .orElseThrow(() -> new EntityNotFoundException("Brak przesyłki: " + przesylkaId));

        StatusPrzesylki statusOczekuje = znajdzStatusPoKodzie("OCZEKUJE_NA_KURIERA");

        Uzytkownik uzytkownik = uzytkownikId != null ?
                uzytkownikRepository.findById(uzytkownikId).orElse(null) : null;

        zmienStatusPrzesylki(przesylka, statusOczekuje, uzytkownik,
                "Przesyłka zgłoszona do przewozu");
    }


    private StatusPrzesylki znajdzStatusPoKodzie(String kod) {
        return statusRepository.findAll().stream()
                .filter(s -> s.getKod().equalsIgnoreCase(kod))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Brak statusu: " + kod));
    }

    private void zmienStatusPrzesylki(Przesylka przesylka, StatusPrzesylki nowyStatus,
                                      Uzytkownik uzytkownik, String komentarz) {
        HistoriaStatusu historia = new HistoriaStatusu();
        historia.setPrzesylka(przesylka);
        historia.setStatus(nowyStatus);
        historia.setCzasZmiany(LocalDateTime.now());
        historia.setUzytkownik(uzytkownik);
        historia.setKomentarz(komentarz);
        historiaRepository.save(historia);

        przesylka.setAktualnyStatus(nowyStatus);
        przesylkaRepository.save(przesylka);
    }
}