package it.prova.gestionetratte.repository.airbus;

import it.prova.gestionetratte.model.Airbus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AirbusRepository extends JpaRepository <Airbus, Long>
{
    boolean existsByCodice(String codice);

    @Query("select count(a) > 0 from Airbus a where a.codice = ?1 and a.id_airbus <> ?2")
    boolean existsByCodiceAndIdNot(String codice, Long id);

    @Query("select distinct a from Airbus a left join fetch a.tratte")
    List<Airbus> findAllEager();

    @Query("select distinct a from Airbus a left join fetch a.tratte where a.id_airbus = ?1")
    Airbus findSingleAirbusEager(Long id);

    @Query(value = """
    select
        a.id_airbus as id_airbus,
        a.codice as codice,
        a.descrizione as descrizione,
        a.dataInizioServizio as dataInizioServizio,
        a.numeroPasseggeri as numeroPasseggeri
    from airbus a
    where exists (
        select 1
        from tratta t1
        join tratta t2 on t1.airbus_id = t2.airbus_id
        where t1.airbus_id = a.id_airbus
          and t1.id < t2.id
          and t1.data = t2.data
          and t1.oraDecollo < t2.oraAtterraggio
          and t1.oraAtterraggio > t2.oraDecollo
    )
    """, nativeQuery = true)
    List<Airbus> findAllConSovrapposizioni();
}
