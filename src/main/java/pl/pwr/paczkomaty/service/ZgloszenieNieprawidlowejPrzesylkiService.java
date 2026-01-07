package pl.pwr.paczkomaty.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.paczkomaty.model.entity.*;
import pl.pwr.paczkomaty.repository.ZgloszenieNieprawidlowejPrzesylkiRepository;
import pl.pwr.paczkomaty.repository.PrzesylkaRepository;
import pl.pwr.paczkomaty.repository.UzytkownikRepository;
import pl.pwr.paczkomaty.repository.StatusPrzesylkiRepository;
import pl.pwr.paczkomaty.repository.HistoriaStatusuRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ZgloszenieNieprawidlowejPrzesylkiService {

    public static final String TYP_USZKODZONA = "USZKODZONA";
    public static final String TYP_ZAGUBIONA = "ZAGUBIONA";
    public static final String TYP_INNA = "INNA";

    @Autowired
    private ZgloszenieNieprawidlowejPrzesylkiRepository zgloszenieRepository;

    @Autowired
    private PrzesylkaRepository przesylkaRepository;

    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    @Autowired
    private StatusPrzesylkiRepository statusRepository;

    @Autowired
    private HistoriaStatusuRepository historiaRepository;

    public List<ZgloszenieNieprawidlowejPrzesylki> pobierzWszystkieZgloszenia() {
        return zgloszenieRepository.findAll();
    }

    public Optional<ZgloszenieNieprawidlowejPrzesylki> znajdzZgloszenie(Integer id) {
        return zgloszenieRepository.findById(id);
    }

    public List<ZgloszenieNieprawidlowejPrzesylki> znajdzZgloszeniaDlaPrzesylki(Integer przesylkaId) {
        return zgloszenieRepository.findByPrzesylkaId(przesylkaId);
    }

    public List<ZgloszenieNieprawidlowejPrzesylki> znajdzZgloszeniaUzytkownika(Integer uzytkownikId) {
        return zgloszenieRepository.findByZglaszajacyId(uzytkownikId);
    }

    public List<ZgloszenieNieprawidlowejPrzesylki> znajdzZgloszeniaPoTypie(String typ) {
        return zgloszenieRepository.findByTyp(typ);
    }


    public ZgloszenieNieprawidlowejPrzesylki utworzZgloszenie(Integer przesylkaId, Integer zglaszajacyId,
                                                              String typ, String opis) {
        Przesylka przesylka = przesylkaRepository.findById(przesylkaId)
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono przesyłki o ID: " + przesylkaId));

        Uzytkownik zglaszajacy = null;
        if (zglaszajacyId != null) {
            zglaszajacy = uzytkownikRepository.findById(zglaszajacyId)
                    .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono użytkownika o ID: " + zglaszajacyId));
        }

        if (!isValidTyp(typ)) {
            throw new IllegalArgumentException("Nieprawidłowy typ zgłoszenia: " + typ);
        }

        ZgloszenieNieprawidlowejPrzesylki zgloszenie = new ZgloszenieNieprawidlowejPrzesylki();
        zgloszenie.setPrzesylka(przesylka);
        zgloszenie.setZglaszajacy(zglaszajacy);
        zgloszenie.setTyp(typ);
        zgloszenie.setOpis(opis);
        zgloszenie.setDataZgloszenia(LocalDateTime.now());

        ZgloszenieNieprawidlowejPrzesylki saved = zgloszenieRepository.save(zgloszenie);

        aktualizujStatusPrzesylkiPoZgloszeniu(przesylka, typ, zglaszajacy, opis);

        return saved;
    }


    private void aktualizujStatusPrzesylkiPoZgloszeniu(Przesylka przesylka, String typ,
                                                        Uzytkownik uzytkownik, String komentarz) {
        String nowyStatusKod;
        switch (typ) {
            case TYP_USZKODZONA:
                nowyStatusKod = "USZKODZONA";
                break;
            case TYP_ZAGUBIONA:
                nowyStatusKod = "ZAGUBIONA";
                break;
            default:
                nowyStatusKod = "PROBLEM";
                break;
        }

        Optional<StatusPrzesylki> nowyStatus = statusRepository.findAll().stream()
                .filter(s -> s.getKod().equalsIgnoreCase(nowyStatusKod))
                .findFirst();

        if (nowyStatus.isPresent()) {
            HistoriaStatusu historia = new HistoriaStatusu();
            historia.setPrzesylka(przesylka);
            historia.setStatus(nowyStatus.get());
            historia.setCzasZmiany(LocalDateTime.now());
            historia.setUzytkownik(uzytkownik);
            historia.setKomentarz("Zgłoszenie nieprawidłowości: " + typ + ". " + (komentarz != null ? komentarz : ""));
            historiaRepository.save(historia);

            przesylka.setAktualnyStatus(nowyStatus.get());
            przesylkaRepository.save(przesylka);
        }
    }

    public void usunZgloszenie(Integer id) {
        zgloszenieRepository.deleteById(id);
    }

    public List<String> pobierzDostepneTypy() {
        return List.of(TYP_USZKODZONA, TYP_ZAGUBIONA, TYP_INNA);
    }

    private boolean isValidTyp(String typ) {
        return TYP_USZKODZONA.equals(typ) || TYP_ZAGUBIONA.equals(typ) || TYP_INNA.equals(typ);
    }
}

