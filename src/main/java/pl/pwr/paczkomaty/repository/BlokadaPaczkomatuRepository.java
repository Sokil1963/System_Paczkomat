package pl.pwr.paczkomaty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.paczkomaty.model.entity.BlokadaPaczkomatu;

import java.util.List;

@Repository
public interface BlokadaPaczkomatuRepository extends JpaRepository<BlokadaPaczkomatu, Integer> {
    List<BlokadaPaczkomatu> findByPaczkomatId(Integer paczkomatId);
    List<BlokadaPaczkomatu> findByPaczkomatIdAndDataOdblokowaniaIsNull(Integer paczkomatId);
}

