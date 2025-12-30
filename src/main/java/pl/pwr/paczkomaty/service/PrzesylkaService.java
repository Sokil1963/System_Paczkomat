package pl.pwr.paczkomaty.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.paczkomaty.model.entity.Przesylka;
import pl.pwr.paczkomaty.model.entity.StatusPrzesylki;
import pl.pwr.paczkomaty.repository.PrzesylkaRepository;
import pl.pwr.paczkomaty.repository.StatusPrzesylkiRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PrzesylkaService {

    @Autowired
    private PrzesylkaRepository przesylkaRepository;

    @Autowired
    private StatusPrzesylkiRepository statusPrzesylkiRepository;

    public List<Przesylka> znajdzPrzesylkiDoWydania(Integer kurierId, Integer sortowniaId) {
        return przesylkaRepository.findAll();
    }

    public Optional<Przesylka> znajdzPrzesylke(Integer id) {
        return przesylkaRepository.findById(id);
    }

    public Optional<Przesylka> znajdzPrzesylkePoNumerze(Integer id) {
        return przesylkaRepository.findById(id);
    }

    public Przesylka zapiszPrzesylke(Przesylka przesylka) {
        if (przesylka.getAktualnyStatus() == null) {
            statusPrzesylkiRepository.findAll().stream().findFirst()
                    .ifPresent(przesylka::setAktualnyStatus);
        }
        return przesylkaRepository.save(przesylka);
    }

    public void aktualizujPrzesylke(Integer id, Integer kodOdbioru, String opis, Integer statusId) {
        Przesylka przesylka = przesylkaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brak przesyłki: " + id));

        if (kodOdbioru != null) przesylka.setKodOdbioru(kodOdbioru);
        if (opis != null) przesylka.setOpis(opis);
        if (statusId != null) {
            StatusPrzesylki status = statusPrzesylkiRepository.findById(statusId)
                    .orElseThrow(() -> new EntityNotFoundException("Brak statusu"));
            przesylka.setAktualnyStatus(status);
        }
    }

    public List<StatusPrzesylki> pobierzWszystkieStatusy() {
        return statusPrzesylkiRepository.findAll();
    }
}