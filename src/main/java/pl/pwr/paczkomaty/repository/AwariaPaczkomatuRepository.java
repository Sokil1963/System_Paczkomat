package pl.pwr.paczkomaty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.paczkomaty.model.entity.AwariaPaczkomatu;

import java.util.List;

@Repository
public interface AwariaPaczkomatuRepository extends JpaRepository<AwariaPaczkomatu, Integer> {
    List<AwariaPaczkomatu> findByPaczkomatId(Integer paczkomatId);
    List<AwariaPaczkomatu> findByStatusAwarii(String statusAwarii);
}

