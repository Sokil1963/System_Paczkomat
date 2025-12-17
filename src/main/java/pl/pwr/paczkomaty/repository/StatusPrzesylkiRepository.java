package pl.pwr.paczkomaty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.paczkomaty.model.entity.StatusPrzesylki;

import java.util.Optional;

@Repository
public interface StatusPrzesylkiRepository extends JpaRepository<StatusPrzesylki, Integer> {
    Optional<StatusPrzesylki> findByKod(String kod);
}

