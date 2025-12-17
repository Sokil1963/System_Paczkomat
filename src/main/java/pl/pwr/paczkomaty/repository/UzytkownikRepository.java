package pl.pwr.paczkomaty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;

import java.util.List;
import java.util.Optional;

@Repository
public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Integer> {
    Optional<Uzytkownik> findByLogin(String login);
    List<Uzytkownik> findByRola(String rola);
}

