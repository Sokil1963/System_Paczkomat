package pl.pwr.paczkomaty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.paczkomaty.model.entity.Paczkomat;

import java.util.Optional;

@Repository
public interface PaczkomatRepository extends JpaRepository<Paczkomat, Integer> {
    Optional<Paczkomat> findByKod(String kod);
}

