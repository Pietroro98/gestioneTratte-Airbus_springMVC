package it.prova.gestionetratte.service.tratta;
import it.prova.gestionetratte.model.Tratta;
import java.util.List;

public interface TrattaService
{
    List<Tratta> listAllElements(boolean eager);

    Tratta caricaSingoloElemento(Long id);

    Tratta caricaSingoloElementoEager(Long id);

    Tratta aggiorna(Tratta trattaInstance);

    Tratta inserisciNuovo(Tratta trattaInstance);

    void rimuovi(Long idToRemove);
}
