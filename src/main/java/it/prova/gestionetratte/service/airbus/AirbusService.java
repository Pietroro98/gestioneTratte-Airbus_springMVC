package it.prova.gestionetratte.service.airbus;

import it.prova.gestionetratte.model.Airbus;

import java.util.List;

public interface AirbusService {
    List<Airbus> listAllElements(boolean eager);

    Airbus caricaSingoloElemento(Long id);

    Airbus caricaSingoloElementoEager(Long id);

    Airbus aggiorna(Airbus airbusInstance);

    Airbus inserisciNuovo(Airbus airbusInstance);

    void rimuovi(Long idToRemove);

    List<Airbus> listAllConSovrapposizioni();
}
