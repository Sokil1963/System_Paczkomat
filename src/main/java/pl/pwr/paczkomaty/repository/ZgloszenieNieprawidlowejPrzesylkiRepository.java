package pl.pwr.paczkomaty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.paczkomaty.model.entity.ZgloszenieNieprawidlowejPrzesylki;

import java.util.List;

@Repository
public interface ZgloszenieNieprawidlowejPrzesylkiRepository extends JpaRepository<ZgloszenieNieprawidlowejPrzesylki, Integer> {
    List<ZgloszenieNieprawidlowejPrzesylki> findByPrzesylkaId(Integer przesylkaId);
    List<ZgloszenieNieprawidlowejPrzesylki> findByZglaszajacyId(Integer uzytkownikId);
    List<ZgloszenieNieprawidlowejPrzesylki> findByTyp(String typ);
}

