package pl.pwr.paczkomaty.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.paczkomaty.model.entity.ZadanieDystrybucyjne;
import pl.pwr.paczkomaty.repository.ZadanieDystrybucyjneRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ZadanieDystrybucyjneService {

    @Autowired
    private ZadanieDystrybucyjneRepository zadanieRepository;

    public List<ZadanieDystrybucyjne> pobierzWszystkieZadania() {
        return zadanieRepository.findAll();
    }

    public Optional<ZadanieDystrybucyjne> znajdzZadanie(Integer id) {
        return zadanieRepository.findById(id);
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
            // Logika czasowa statusów
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
}