package pl.pwr.paczkomaty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pwr.paczkomaty.model.entity.ZadanieDystrybucyjne;
import pl.pwr.paczkomaty.repository.ZadanieDystrybucyjneRepository;
import pl.pwr.paczkomaty.repository.SortowniaRepository;
import pl.pwr.paczkomaty.repository.UzytkownikRepository;

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



    public ZadanieDystrybucyjne utworzZadanie(ZadanieDystrybucyjne zadanie) {
        if (zadanie.getStatus() == null) {
            zadanie.setStatus("ZAPLANOWANE");
        }
        if (zadanie.getDataStartu() == null) {
            zadanie.setDataStartu(LocalDateTime.now());
        }
        return zadanieRepository.save(zadanie);
    }

    public Optional<ZadanieDystrybucyjne> znajdzZadanie(Integer id) {
        return zadanieRepository.findById(id);
    }

    public void aktualizujStatusZadania(Integer id, String status) {
        Optional<ZadanieDystrybucyjne> zadanieOpt = zadanieRepository.findById(id);
        if (zadanieOpt.isPresent()) {
            ZadanieDystrybucyjne zadanie = zadanieOpt.get();

            if ("W_TRAKCIE".equals(status) && zadanie.getDataStartu() == null) {
                zadanie.setDataStartu(LocalDateTime.now());
            }

            if ("ZAKONCZONE".equals(status) && zadanie.getDataKonca() == null) {
                if (zadanie.getDataStartu() == null) {
                    zadanie.setDataStartu(LocalDateTime.now());
                }
                zadanie.setDataKonca(LocalDateTime.now());
            }

            zadanie.setStatus(status);
            zadanieRepository.save(zadanie);
        }
    }
}
