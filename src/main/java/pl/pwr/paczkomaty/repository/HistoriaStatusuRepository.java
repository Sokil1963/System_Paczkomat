package pl.pwr.paczkomaty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwr.paczkomaty.model.entity.HistoriaStatusu;

import java.util.List;

@Repository
public interface HistoriaStatusuRepository extends JpaRepository<HistoriaStatusu, Integer> {
    List<HistoriaStatusu> findByPrzesylkaId(Integer przesylkaId);

    List<HistoriaStatusu> findByPrzesylkaIdOrderByCzasZmianyDesc(Integer przesylkaId);

    List<HistoriaStatusu> findByPrzesylkaIdOrderByCzasZmianyAsc(Integer przesylkaId);
}

