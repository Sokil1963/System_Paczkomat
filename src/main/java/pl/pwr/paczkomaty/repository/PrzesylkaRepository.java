package pl.pwr.paczkomaty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.pwr.paczkomaty.model.entity.Przesylka;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrzesylkaRepository extends JpaRepository<Przesylka, Integer> {
    Optional<Przesylka> findById(Integer id);

    Optional<Przesylka> findByKodOdbioru(Integer kodOdbioru);

    @Query("SELECT p FROM Przesylka p WHERE p.aktualnyStatus.kod = :statusKod")
    List<Przesylka> findByStatusKod(@Param("statusKod") String statusKod);

    @Query("SELECT p FROM Przesylka p WHERE p.paczkomatDocelowy.id = :paczkomatId AND p.aktualnyStatus.kod = 'GOTOWA_DO_ODBIORU'")
    List<Przesylka> findGotoweDoOdbioruWPaczkomacie(@Param("paczkomatId") Integer paczkomatId);
}

