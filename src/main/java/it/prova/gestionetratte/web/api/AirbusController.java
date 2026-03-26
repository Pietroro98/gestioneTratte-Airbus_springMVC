package it.prova.gestionetratte.web.api;
import it.prova.gestionetratte.dto.AirbusDTO;
import it.prova.gestionetratte.dto.MessageDTO;
import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.service.airbus.AirbusService;
import it.prova.gestionetratte.web.exception.BadRequestException;
import it.prova.gestionetratte.web.exception.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/airbus")
public class AirbusController {

    @Autowired
    private AirbusService airbusService;

    @GetMapping
    public List<AirbusDTO> getAll() {
        return AirbusDTO.createAirbusDTOListFromModelList(airbusService.listAllElements(true), true);
    }

    @GetMapping("/{id}")
    public AirbusDTO findById(@PathVariable(required = true) Long id) {
        Airbus airbus = airbusService.caricaSingoloElementoEager(id);
        if (airbus == null) {
            throw new NotFoundException("Airbus non trovato con id: " + id);
        }
        return AirbusDTO.buildAirbusDTOFromModel(airbus, true);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AirbusDTO createNew(@Valid @RequestBody AirbusDTO airbusInput) {
        if (airbusInput.getId_airbus() != null) {
            throw new BadRequestException("Non e' ammesso fornire un id nel body per la creazione");
        }
        return AirbusDTO.buildAirbusDTOFromModel(airbusService.inserisciNuovo(airbusInput.buildAirbusModel()), false);
    }

    @PutMapping("/{id}")
    public AirbusDTO update(@Valid @RequestBody AirbusDTO airbusInput, @PathVariable(required = true) Long id) {
        if (airbusInput.getId_airbus() != null) {
            throw new BadRequestException("Non e' ammesso fornire id_airbus nel body per l'aggiornamento");
        }

        Airbus airbus = airbusService.caricaSingoloElemento(id);
        if (airbus == null) {
            throw new NotFoundException("Airbus not found con id: " + id);
        }
        airbusInput.setId_airbus(id);
        return AirbusDTO.buildAirbusDTOFromModel(airbusService.aggiorna(airbusInput.buildAirbusModel()), false);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public MessageDTO delete(@PathVariable(required = true) Long id) {
        airbusService.rimuovi(id);
        return new MessageDTO("Airbus con id " + id + " eliminato correttamente");
    }


    /**
     * Invocando questo endpoint il sistema carica tutte le tratte create che presentino eventuali sovrapposizioni di orario,
     * magari a causa di inserimento erroneo sulla base dati.
     * Restituisce una lista con tutti gli airbus e aggiunge in busta
     * un boolean --> ‘conSovrapposizioni’: true.
     * @return
     */
    @GetMapping("/listaAirbusConSovrapposizioni")
    public List<AirbusDTO> getListaAirbusConSovrapposizioni() {
        return AirbusDTO.createAirbusDTOConSovrapposizioniListFromModelList(airbusService.listAllConSovrapposizioni());
    }
}
