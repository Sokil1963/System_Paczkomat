package pl.pwr.paczkomaty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.paczkomaty.model.entity.ZadanieDystrybucyjne;

import java.util.List;

@Repository
public interface ZadanieDystrybucyjneRepository extends JpaRepository<ZadanieDystrybucyjne, Integer> {
    List<ZadanieDystrybucyjne> findByKurierId(Integer kurierId);
    List<ZadanieDystrybucyjne> findByStatus(String status);
}

