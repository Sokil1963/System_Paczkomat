package pl.pwr.paczkomaty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.paczkomaty.model.entity.Sortownia;

@Repository
public interface SortowniaRepository extends JpaRepository<Sortownia, Integer> {
}

