package pl.pwr.paczkomaty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;
import pl.pwr.paczkomaty.repository.UzytkownikRepository;

import java.util.Optional;

@Service
@Transactional
public class KurierService {
    
    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    public boolean sprawdzIdKuriera(Integer idKurier) {
        Optional<Uzytkownik> uzytkownik = uzytkownikRepository.findById(idKurier);
        return uzytkownik.isPresent() && "KURIER".equals(uzytkownik.get().getRola());
    }

    public boolean sprawdzUprawnieniaKurieraDoUrzadzenia(Integer idKurier, Integer idUrzadzenia) {
        // Implementacja sprawdzania uprawnień
        return sprawdzIdKuriera(idKurier);
    }

    public void zarejestrujAutoryzacjeKuriera(Integer idKurier, Integer idUrzadzenia) {
        // Implementacja rejestracji autoryzacji
    }

    public Optional<Uzytkownik> znajdzKuriera(Integer id) {
        Optional<Uzytkownik> uzytkownik = uzytkownikRepository.findById(id);
        if (uzytkownik.isPresent() && "KURIER".equals(uzytkownik.get().getRola())) {
            return uzytkownik;
        }
        return Optional.empty();
    }
}

