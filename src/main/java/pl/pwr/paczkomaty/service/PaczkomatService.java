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
public class PaczkomatService {
    
    @Autowired
    private PaczkomatRepository paczkomatRepository;
    
    @Autowired
    private AwariaPaczkomatuRepository awariaPaczkomatuRepository;
    
    @Autowired
    private BlokadaPaczkomatuRepository blokadaPaczkomatuRepository;
    
    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    public List<Paczkomat> pobierzWszystkiePaczkomaty() {
        return paczkomatRepository.findAll();
    }

    public Optional<Paczkomat> znajdzPaczkomat(Integer id) {
        return paczkomatRepository.findById(id);
    }

    public Optional<Paczkomat> znajdzPaczkomatPoKodzie(String kod) {
        return paczkomatRepository.findByKod(kod);
    }

    public Paczkomat zapiszPaczkomat(Paczkomat paczkomat) {
        return paczkomatRepository.save(paczkomat);
    }

    public void zarejestrujAwariePaczkomatu(Integer idPaczkomatu, String opis, Integer idKurier, Boolean wymagaBlokady) {
        Optional<Paczkomat> paczkomatOpt = paczkomatRepository.findById(idPaczkomatu);
        
        if (paczkomatOpt.isPresent()) {
            AwariaPaczkomatu awaria = new AwariaPaczkomatu();
            awaria.setPaczkomat(paczkomatOpt.get());
            awaria.setOpis(opis);
            awaria.setStatusAwarii("NOWA");
            awaria.setWymagaBlokady(wymagaBlokady != null ? wymagaBlokady : false);
            awaria.setDataZgloszenia(LocalDateTime.now());
            
            if (idKurier != null) {
                uzytkownikRepository.findById(idKurier).ifPresent(awaria::setZglaszajacy);
            }
            
            awariaPaczkomatuRepository.save(awaria);
            
            // Jeśli wymaga blokady, zablokuj paczkomat
            if (awaria.getWymagaBlokady()) {
                zablokujPaczkomat(idPaczkomatu, awaria.getId());
            }
        }
    }

    public void zablokujPaczkomat(Integer idPaczkomatu, Integer awariaId) {
        Optional<Paczkomat> paczkomatOpt = paczkomatRepository.findById(idPaczkomatu);
        
        if (paczkomatOpt.isPresent()) {
            Paczkomat paczkomat = paczkomatOpt.get();
            paczkomat.setStatus("ZABLOKOWANY");
            paczkomatRepository.save(paczkomat);
            
            BlokadaPaczkomatu blokada = new BlokadaPaczkomatu();
            blokada.setPaczkomat(paczkomat);
            blokada.setDataBlokady(LocalDateTime.now());
            
            if (awariaId != null) {
                awariaPaczkomatuRepository.findById(awariaId).ifPresent(blokada::setAwaria);
            }
            
            blokadaPaczkomatuRepository.save(blokada);
        }
    }

    public void odblokujPaczkomat(Integer idPaczkomatu) {
        Optional<Paczkomat> paczkomatOpt = paczkomatRepository.findById(idPaczkomatu);
        
        if (paczkomatOpt.isPresent()) {
            Paczkomat paczkomat = paczkomatOpt.get();
            paczkomat.setStatus("AKTYWNY");
            paczkomatRepository.save(paczkomat);
            
            // Znajdź aktywną blokadę i ją zamknij
            List<BlokadaPaczkomatu> blokady = blokadaPaczkomatuRepository.findByPaczkomatIdAndDataOdblokowaniaIsNull(idPaczkomatu);
            for (BlokadaPaczkomatu blokada : blokady) {
                blokada.setDataOdblokowania(LocalDateTime.now());
                blokadaPaczkomatuRepository.save(blokada);
            }
        }
    }

    public List<AwariaPaczkomatu> pobierzAwarie(Integer paczkomatId) {
        if (paczkomatId != null) {
            return awariaPaczkomatuRepository.findByPaczkomatId(paczkomatId);
        }
        return awariaPaczkomatuRepository.findAll();
    }

}

