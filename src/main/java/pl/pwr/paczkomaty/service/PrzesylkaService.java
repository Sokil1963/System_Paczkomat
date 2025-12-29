package pl.pwr.paczkomaty.service;

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
        // Implementacja logiki znajdowania przesyłek do wydania
        return przesylkaRepository.findAll();
    }



    public void aktualizujStatusPrzesylki(Integer idPrzesylki, String kodStatusu, String opis, Integer uzytkownikId) {
        Optional<Przesylka> przesylkaOpt = przesylkaRepository.findById(idPrzesylki);
        Optional<StatusPrzesylki> statusOpt = statusPrzesylkiRepository.findByKod(kodStatusu);
        
        if (przesylkaOpt.isPresent() && statusOpt.isPresent()) {
            Przesylka przesylka = przesylkaOpt.get();
            StatusPrzesylki nowyStatus = statusOpt.get();
            
            // Zapisz historię
            HistoriaStatusu historia = new HistoriaStatusu();
            historia.setPrzesylka(przesylka);
            historia.setStatus(nowyStatus);
            historia.setCzasZmiany(LocalDateTime.now());
            historia.setKomentarz(opis);
            
            if (uzytkownikId != null) {
                uzytkownikRepository.findById(uzytkownikId).ifPresent(historia::setUzytkownik);
            }
            
            historiaStatusuRepository.save(historia);
            
            // Aktualizuj status
            przesylka.setAktualnyStatus(nowyStatus);
            przesylkaRepository.save(przesylka);
        }
    }



    public Optional<Przesylka> znajdzPrzesylke(Integer id) {
        return przesylkaRepository.findById(id);
    }

    public Optional<Przesylka> znajdzPrzesylkePoNumerze(Long numer) {
        return przesylkaRepository.findByNumer(numer);
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

