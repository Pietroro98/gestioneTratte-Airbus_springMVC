package it.prova.gestionetratte.service.airbus;

import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.repository.airbus.AirbusRepository;
import it.prova.gestionetratte.repository.tratta.TrattaRepository;
import it.prova.gestionetratte.web.exception.AirbusHasAssociatedTratteException;
import it.prova.gestionetratte.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AirbusServiceImpl implements AirbusService {

    @Autowired
    private AirbusRepository airbusRepository;

    @Autowired
    private TrattaRepository trattaRepository;


    @Override
    public List<Airbus> listAllElements(boolean eager) {
        if (eager) {
            return airbusRepository.findAllEager();
        }
        return airbusRepository.findAll();
    }

    @Override
    public Airbus caricaSingoloElemento(Long id) {
        return airbusRepository.findById(id).orElse(null);
    }

    @Override
    public Airbus caricaSingoloElementoEager(Long id) {
        return airbusRepository.findSingleAirbusEager(id);
    }

    @Override
    @Transactional
    public Airbus aggiorna(Airbus airbusInstance) {
        validateCodiceUnivocoInAggiornamento(airbusInstance);
        return airbusRepository.save(airbusInstance);
    }

    @Override
    @Transactional
    public Airbus inserisciNuovo(Airbus airbusInstance) {
        validateCodiceUnivocoInInserimento(airbusInstance);
        return airbusRepository.save(airbusInstance);
    }

    @Override
    public void rimuovi(Long idToRemove) {
        airbusRepository.findById(idToRemove)
                .orElseThrow(() -> new NotFoundException("Airbus con id: " + idToRemove + " non trovato. Impossibile rimuoverlo."));

        if (trattaRepository.countByAirbus_Id_airbus(idToRemove) > 0) {
            throw new AirbusHasAssociatedTratteException(
                    "Impossibile rimuovere l'airbus con id: " + idToRemove + " perchè ha tratte associate");
        }

        airbusRepository.deleteById(idToRemove);

    }

    @Override
    public List<Airbus> listAllConSovrapposizioni() {
        return airbusRepository.findAllConSovrapposizioni();
    }

    private void validateCodiceUnivocoInInserimento(Airbus airbusInstance) {
        if (airbusInstance != null && hasText(airbusInstance.getCodice())
                && airbusRepository.existsByCodice(airbusInstance.getCodice())) {
            throw new it.prova.gestionetratte.web.exception.BadRequestException(
                    "Esiste gia' un Airbus con codice '" + airbusInstance.getCodice() + "'");
        }
    }

    private void validateCodiceUnivocoInAggiornamento(Airbus airbusInstance) {
        if (airbusInstance != null && airbusInstance.getId_airbus() != null && hasText(airbusInstance.getCodice())
                && airbusRepository.existsByCodiceAndIdNot(airbusInstance.getCodice(), airbusInstance.getId_airbus())) {
            throw new it.prova.gestionetratte.web.exception.BadRequestException(
                    "Esiste gia' un Airbus con codice '" + airbusInstance.getCodice() + "'");
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
