package it.prova.gestionetratte.repository.airbus;

import it.prova.gestionetratte.model.Airbus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AirbusRepository extends JpaRepository <Airbus, Long>
{
    @Query("select distinct a from Airbus a left join fetch a.tratte")
    List<Airbus> findAllEager();

    @Query("select distinct a from Airbus a left join fetch a.tratte where a.id = ?1")
    Airbus findSingleAirbusEager(Long id);
}
