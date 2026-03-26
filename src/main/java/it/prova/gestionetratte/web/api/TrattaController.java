package it.prova.gestionetratte.web.api;
import it.prova.gestionetratte.dto.MessageDTO;
import it.prova.gestionetratte.dto.TrattaDTO;
import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.service.airbus.AirbusService;
import it.prova.gestionetratte.service.tratta.TrattaService;
import it.prova.gestionetratte.web.exception.BadRequestException;
import it.prova.gestionetratte.web.exception.NotAllowedException;
import it.prova.gestionetratte.web.exception.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tratta")
public class TrattaController {

    @Autowired
    private TrattaService trattaService;

    @Autowired
    private AirbusService airbusService;

    @GetMapping
    public List<TrattaDTO> getAll() {
        return TrattaDTO.createTrattaDTOListFromModelList(trattaService.listAllElements(true), true);
    }

    @GetMapping("/{id}")
    public TrattaDTO findById(@PathVariable(required = true) Long id) {
        Tratta tratta = trattaService.caricaSingoloElementoEager(id);
        if (tratta == null) {
            throw new NotFoundException("Tratta not found con id: " + id);
        }
        return TrattaDTO.buildTrattaDTOFromModel(tratta, true);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrattaDTO createNew(@Valid @RequestBody TrattaDTO trattaInput) {
        Tratta trattaToInsert = buildTrattaForInsert(trattaInput);
        return TrattaDTO.buildTrattaDTOFromModel(trattaService.inserisciNuovo(trattaToInsert), true);
    }

    @PostMapping("/multiple")
    @ResponseStatus(HttpStatus.CREATED)
    public List<TrattaDTO> createNewMultiple(@Valid @RequestBody List<@Valid TrattaDTO> tratteInput) {
        if (tratteInput == null || tratteInput.isEmpty()) {
            throw new BadRequestException("La lista delle tratte da inserire non puo' essere vuota");
        }

        List<Tratta> tratteToInsert = tratteInput.stream()
                .map(this::buildTrattaForInsert)
                .toList();

        return TrattaDTO.createTrattaDTOListFromModelList(trattaService.inserisciNuovi(tratteToInsert), true);
    }

    @PutMapping("/{id}")
    public TrattaDTO update(@Valid @RequestBody TrattaDTO trattaInput, @PathVariable(required = true) Long id) {
        Tratta tratta = trattaService.caricaSingoloElemento(id);
        if (tratta == null) {
            throw new NotAllowedException("Tratta non trovata con id: " + id);
        }
        trattaInput.setId(id);
        return TrattaDTO.buildTrattaDTOFromModel(trattaService.aggiorna(trattaInput.buildTrattaModel()), true);
    }

    @DeleteMapping("/{id}")
    public MessageDTO delete(@PathVariable(required = true) Long id) {
        trattaService.rimuovi(id);
        return new MessageDTO("Tratta con id " + id + " eliminata correttamente");
    }

    private Tratta buildTrattaForInsert(TrattaDTO trattaInput) {
        if (trattaInput.getId() != null) {
            throw new NotAllowedException("Non e' ammesso fornire un id per la creazione");
        }
        if (trattaInput.getAirbus() == null || trattaInput.getAirbus().getId_airbus() == null) {
            throw new BadRequestException("E' obbligatorio fornire l'id_airbus nel body della tratta");
        }

        Tratta tratta = trattaInput.buildTrattaModel();
        tratta.setAirbus(loadExistingAirbus(trattaInput.getAirbus().getId_airbus()));
        return tratta;
    }

    private Airbus loadExistingAirbus(Long idAirbus) {
        Airbus airbus = airbusService.caricaSingoloElemento(idAirbus);
        if (airbus == null) {
            throw new NotFoundException("Airbus not found con id: " + idAirbus);
        }
        return airbus;
    }

    @GetMapping("/concludiTratte")
    public List<TrattaDTO> concludiTratteOnDemand(){
        return TrattaDTO.createTrattaDTOListFromModelList(trattaService.concludiTratte(),true);
    }
}
