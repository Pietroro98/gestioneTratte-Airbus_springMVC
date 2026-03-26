package it.prova.gestionetratte.service.tratta;
import it.prova.gestionetratte.model.StatoTratta;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.repository.tratta.TrattaRepository;
import it.prova.gestionetratte.utils.UtilsClass;
import it.prova.gestionetratte.web.exception.NotAllowedException;
import it.prova.gestionetratte.web.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TrattaServiceImpl implements TrattaService
{
    @Autowired
    private UtilsClass utilsClass;

    @Autowired
    private TrattaRepository trattaRepository;

    @Override
    public List<Tratta> listAllElements(boolean eager) {
        if(eager) {
            return trattaRepository.findAllEager();
        }
        return trattaRepository.findAll();
    }

    @Override
    public Tratta caricaSingoloElemento(Long id) {
        return trattaRepository.findById(id).orElse(null);
    }

    @Override
    public Tratta caricaSingoloElementoEager(Long id) {
        return trattaRepository.findSingleTrattaEager(id);
    }

    @Override
    @Transactional
    public Tratta aggiorna(Tratta trattaInstance) {
        utilsClass.validateOrarioTrattaIsAfter(trattaInstance);
        return trattaRepository.save(trattaInstance);
    }

    @Override
    @Transactional
    public Tratta inserisciNuovo(Tratta trattaInstance) {
        utilsClass.validateOrarioTrattaIsAfter(trattaInstance);

        return trattaRepository.save(trattaInstance);
    }

    @Override
    public void rimuovi(Long idToRemove) {
        Tratta tratta = trattaRepository.findById(idToRemove)
                .orElseThrow(() -> new NotFoundException("Tratta not found con id: " + idToRemove));

        if(!StatoTratta.ANNULLATA.equals(tratta.getStato())) {
            trattaRepository.deleteById(idToRemove);
        } else {
            throw new NotAllowedException("Impossibile rimuovere la tratta con id " + idToRemove + " se non e' ANNULLATA");
        }
    }
}
