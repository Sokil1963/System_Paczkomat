package pl.pwr.paczkomaty.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.paczkomaty.model.entity.HistoriaStatusu;
import pl.pwr.paczkomaty.model.entity.Przesylka;
import pl.pwr.paczkomaty.model.entity.StatusPrzesylki;
import pl.pwr.paczkomaty.repository.HistoriaStatusuRepository;
import pl.pwr.paczkomaty.repository.PrzesylkaRepository;
import pl.pwr.paczkomaty.repository.StatusPrzesylkiRepository;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Transactional
public class OdbiorcaPrzesylkiService {

    private static final String STATUS_GOTOWA_DO_ODBIORU = "GOTOWA_DO_ODBIORU";
    private static final String STATUS_ODEBRANA = "ODEBRANA";

    @Autowired
    private PrzesylkaRepository przesylkaRepository;

    @Autowired
    private StatusPrzesylkiRepository statusRepository;

    @Autowired
    private HistoriaStatusuRepository historiaRepository;


    public Optional<Przesylka> sprawdzPrzesylke(Integer kodOdbioru) {
        return przesylkaRepository.findByKodOdbioru(kodOdbioru)
                .filter(p -> p.getAktualnyStatus() != null
                        && STATUS_GOTOWA_DO_ODBIORU.equalsIgnoreCase(p.getAktualnyStatus().getKod()));
    }


    public Optional<Przesylka> znajdzPoKodzieOdbioru(Integer kodOdbioru) {
        return przesylkaRepository.findByKodOdbioru(kodOdbioru);
    }


    public boolean czyGotowaDoOdbioru(Przesylka przesylka) {
        return przesylka.getAktualnyStatus() != null
                && STATUS_GOTOWA_DO_ODBIORU.equalsIgnoreCase(przesylka.getAktualnyStatus().getKod());
    }


    public Przesylka odbierzPrzesylke(Integer kodOdbioru) {
        Przesylka przesylka = przesylkaRepository.findByKodOdbioru(kodOdbioru)
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono przesyłki o kodzie: " + kodOdbioru));

        if (przesylka.getAktualnyStatus() == null ||
            !STATUS_GOTOWA_DO_ODBIORU.equalsIgnoreCase(przesylka.getAktualnyStatus().getKod())) {
            throw new IllegalStateException("Przesyłka nie jest gotowa do odbioru. Aktualny status: " +
                    (przesylka.getAktualnyStatus() != null ? przesylka.getAktualnyStatus().getKod() : "brak"));
        }

        StatusPrzesylki statusOdebrana = statusRepository.findAll().stream()
                .filter(s -> STATUS_ODEBRANA.equalsIgnoreCase(s.getKod()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Brak statusu ODEBRANA w systemie"));

        HistoriaStatusu historia = new HistoriaStatusu();
        historia.setPrzesylka(przesylka);
        historia.setStatus(statusOdebrana);
        historia.setCzasZmiany(LocalDateTime.now());
        historia.setKomentarz("Przesyłka odebrana z paczkomatu przez odbiorcę");
        historiaRepository.save(historia);

        przesylka.setAktualnyStatus(statusOdebrana);
        return przesylkaRepository.save(przesylka);
    }


    public String pobierzStatusDlaOdbiorcy(Integer kodOdbioru) {
        Optional<Przesylka> przesylka = przesylkaRepository.findByKodOdbioru(kodOdbioru);

        if (przesylka.isEmpty()) {
            return "NIE_ZNALEZIONO";
        }

        Przesylka p = przesylka.get();
        if (p.getAktualnyStatus() == null) {
            return "BRAK_STATUSU";
        }

        return p.getAktualnyStatus().getKod();
    }
}

