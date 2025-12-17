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
    
    @Autowired
    private SortowniaRepository sortowniaRepository;
    
    @Autowired
    private UzytkownikRepository uzytkownikRepository;

    public List<ZadanieDystrybucyjne> pobierzWszystkieZadania() {
        return zadanieRepository.findAll();
    }

    public List<ZadanieDystrybucyjne> pobierzZadaniaKuriera(Integer kurierId) {
        return zadanieRepository.findByKurierId(kurierId);
    }

    public List<ZadanieDystrybucyjne> pobierzZadaniaPoStatusie(String status) {
        return zadanieRepository.findByStatus(status);
    }

    public ZadanieDystrybucyjne utworzZadanie(ZadanieDystrybucyjne zadanie) {
        // ustaw domyślny статус jeśli null
        if (zadanie.getStatus() == null) {
            zadanie.setStatus("ZAPLANOWANE");
        }
        // Если пользователь хочет видеть время сразу при создании, установить dataStartu при создании, если пустая
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

            // если перевод в W_TRAKCIE и дата старта пустая - установить
            if ("W_TRAKCIE".equals(status) && zadanie.getDataStartu() == null) {
                zadanie.setDataStartu(LocalDateTime.now());
            }

            // если перевод в ZAKONCZONE - установить дату конца, если пустая
            if ("ZAKONCZONE".equals(status) && zadanie.getDataKonca() == null) {
                // если дата старта не установлена - установить её тоже (на случай пропуска)
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
