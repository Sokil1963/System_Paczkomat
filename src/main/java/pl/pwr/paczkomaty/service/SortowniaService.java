package pl.pwr.paczkomaty.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.paczkomaty.model.entity.Sortownia;
import pl.pwr.paczkomaty.repository.SortowniaRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SortowniaService {
    
    @Autowired
    private SortowniaRepository sortowniaRepository;

    public List<Sortownia> pobierzWszystkieSortownie() {
        return sortowniaRepository.findAll();
    }

    public Optional<Sortownia> znajdzSortownie(Integer id) {
        return sortowniaRepository.findById(id);
    }

    public Sortownia zapiszSortownie(Sortownia sortownia) {
        return sortowniaRepository.save(sortownia);
    }

    public void usunSortownie(Integer id) {
        sortowniaRepository.deleteById(id);
    }

    public void aktualizujSortownie(Integer id, String nazwa, String adres) {
        Sortownia s = sortowniaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sortownia nie istnieje"));
        s.setNazwa(nazwa);
        s.setAdres(adres);
    }
}

