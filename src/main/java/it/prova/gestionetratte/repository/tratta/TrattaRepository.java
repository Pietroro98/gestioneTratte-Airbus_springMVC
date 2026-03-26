package it.prova.gestionetratte.repository.tratta;

import it.prova.gestionetratte.model.Tratta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrattaRepository extends JpaRepository<Tratta, Long> {
    @Query("select t from Tratta t join fetch t.airbus")
    List<Tratta> findAllEager();

    @Query("select t from Tratta t join fetch t.airbus where t.id = ?1")
    Tratta findSingleTrattaEager(Long id);

    @Query("select count(t) from Tratta t where t.airbus.id_airbus = ?1")
    long countByAirbus_Id_airbus(Long airbusId);
}
