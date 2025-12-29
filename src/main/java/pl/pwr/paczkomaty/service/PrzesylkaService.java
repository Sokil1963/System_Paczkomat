package pl.pwr.paczkomaty.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.paczkomaty.model.entity.*;
import pl.pwr.paczkomaty.repository.*;

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
    private PaczkomatRepository paczkomatRepository;
    
    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    public List<Przesylka> znajdzPrzesylkiDoWydania(Integer kurierId, Integer sortowniaId) {
        return przesylkaRepository.findAll();
    }



    @Transactional
    public void aktualizujKodOdbioru(Integer id, Integer nowyKod) {
        Przesylka przesylka = przesylkaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono przesyłki o ID: " + id));

        przesylka.setKodOdbioru(nowyKod);

         przesylkaRepository.save(przesylka);

    }



    public Optional<Przesylka> znajdzPrzesylke(Integer id) {
        return przesylkaRepository.findById(id);
    }

    public Optional<Przesylka> znajdzPrzesylkePoNumerze(Integer id) {
        return przesylkaRepository.findById(id);
    }

    public Przesylka zapiszPrzesylke(Przesylka przesylka) {
        if (przesylka.getAktualnyStatus() == null) {
            List<StatusPrzesylki> all = statusPrzesylkiRepository.findAll();
            if (!all.isEmpty()) {
                przesylka.setAktualnyStatus(all.get(0));
            }
        }
        return przesylkaRepository.save(przesylka);
    }
}

