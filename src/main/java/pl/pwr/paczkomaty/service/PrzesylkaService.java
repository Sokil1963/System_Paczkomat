package pl.pwr.paczkomaty.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.paczkomaty.model.entity.HistoriaStatusu;
import pl.pwr.paczkomaty.model.entity.Przesylka;
import pl.pwr.paczkomaty.model.entity.StatusPrzesylki;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;
import pl.pwr.paczkomaty.repository.HistoriaStatusuRepository;
import pl.pwr.paczkomaty.repository.PrzesylkaRepository;
import pl.pwr.paczkomaty.repository.StatusPrzesylkiRepository;
import pl.pwr.paczkomaty.repository.UzytkownikRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PrzesylkaService {

    @Autowired
    private PrzesylkaRepository przesylkaRepository;

    @Autowired
    private StatusPrzesylkiRepository statusPrzesylkiRepository;

    @Autowired
    private HistoriaStatusuRepository historiaStatusuRepository;

    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    public List<Przesylka> znajdzPrzesylkiDoWydania(Integer kurierId, Integer sortowniaId) {
        return przesylkaRepository.findAll();
    }

    public Optional<Przesylka> znajdzPrzesylke(Integer id) {
        return przesylkaRepository.findById(id);
    }

    public Optional<Przesylka> znajdzPrzesylkePoNumerze(Integer id) {
        return przesylkaRepository.findById(id);
    }


    public Optional<Przesylka> znajdzPrzesylkePoKodzieOdbioru(Integer kodOdbioru) {
        return przesylkaRepository.findByKodOdbioru(kodOdbioru);
    }

    public Przesylka zapiszPrzesylke(Przesylka przesylka) {
        if (przesylka.getAktualnyStatus() == null) {
            statusPrzesylkiRepository.findAll().stream().findFirst()
                    .ifPresent(przesylka::setAktualnyStatus);
        }
        return przesylkaRepository.save(przesylka);
    }

    public void aktualizujPrzesylke(Integer id, Integer kodOdbioru, String opis, Integer statusId) {
        aktualizujPrzesylke(id, kodOdbioru, opis, statusId, null, null);
    }


    public void aktualizujPrzesylke(Integer id, Integer kodOdbioru, String opis, Integer statusId,
                                    Integer uzytkownikId, String komentarz) {
        Przesylka przesylka = przesylkaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brak przesyłki: " + id));

        if (kodOdbioru != null) przesylka.setKodOdbioru(kodOdbioru);
        if (opis != null) przesylka.setOpis(opis);
        if (statusId != null) {
            StatusPrzesylki nowyStatus = statusPrzesylkiRepository.findById(statusId)
                    .orElseThrow(() -> new EntityNotFoundException("Brak statusu"));

            if (przesylka.getAktualnyStatus() == null ||
                !przesylka.getAktualnyStatus().getId().equals(statusId)) {

                zapiszHistorieStatusu(przesylka, nowyStatus, uzytkownikId, komentarz);
            }
            przesylka.setAktualnyStatus(nowyStatus);
        }
    }


    public void zmienStatus(Integer przesylkaId, Integer nowyStatusId, Integer uzytkownikId, String komentarz) {
        Przesylka przesylka = przesylkaRepository.findById(przesylkaId)
                .orElseThrow(() -> new EntityNotFoundException("Brak przesyłki: " + przesylkaId));

        StatusPrzesylki nowyStatus = statusPrzesylkiRepository.findById(nowyStatusId)
                .orElseThrow(() -> new EntityNotFoundException("Brak statusu: " + nowyStatusId));

        zapiszHistorieStatusu(przesylka, nowyStatus, uzytkownikId, komentarz);

        przesylka.setAktualnyStatus(nowyStatus);
        przesylkaRepository.save(przesylka);
    }


    public void zmienStatusPoKodzie(Integer przesylkaId, String kodStatusu, Integer uzytkownikId, String komentarz) {
        StatusPrzesylki status = statusPrzesylkiRepository.findAll().stream()
                .filter(s -> s.getKod().equalsIgnoreCase(kodStatusu))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Brak statusu o kodzie: " + kodStatusu));

        zmienStatus(przesylkaId, status.getId(), uzytkownikId, komentarz);
    }

    private void zapiszHistorieStatusu(Przesylka przesylka, StatusPrzesylki nowyStatus,
                                       Integer uzytkownikId, String komentarz) {
        HistoriaStatusu historia = new HistoriaStatusu();
        historia.setPrzesylka(przesylka);
        historia.setStatus(nowyStatus);
        historia.setCzasZmiany(LocalDateTime.now());

        if (uzytkownikId != null) {
            uzytkownikRepository.findById(uzytkownikId).ifPresent(historia::setUzytkownik);
        }

        historia.setKomentarz(komentarz);
        historiaStatusuRepository.save(historia);
    }


    public List<HistoriaStatusu> pobierzHistorieStatusow(Integer przesylkaId) {
        return historiaStatusuRepository.findByPrzesylkaIdOrderByCzasZmianyDesc(przesylkaId);
    }

    public List<StatusPrzesylki> pobierzWszystkieStatusy() {
        return statusPrzesylkiRepository.findAll();
    }
}