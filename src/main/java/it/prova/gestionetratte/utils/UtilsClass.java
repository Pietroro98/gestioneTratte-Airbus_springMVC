package it.prova.gestionetratte.utils;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.web.exception.NotAllowedException;
import org.springframework.stereotype.Component;

@Component
public class UtilsClass
{
    public void validateOrarioTrattaIsAfter(Tratta trattaInstance) {
        if (trattaInstance.getOraDecollo() != null && trattaInstance.getOraAtterraggio() != null
                && !trattaInstance.getOraAtterraggio().isAfter(trattaInstance.getOraDecollo())) {
            throw new NotAllowedException("L'ora di atterraggio deve essere successiva all'ora di decollo.");
        }
    }
}
