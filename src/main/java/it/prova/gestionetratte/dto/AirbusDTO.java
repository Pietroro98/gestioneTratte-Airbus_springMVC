package it.prova.gestionetratte.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import it.prova.gestionetratte.model.Airbus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirbusDTO {

    private Long id_airbus;

    @NotBlank(message = "{airbus.codice.notblank}")
    private String codice;

    @NotBlank(message = "{airbus.descrizione.notblank}")
    private String descrizione;

    @NotNull(message = "{airbus.dataInizioServizio.notnull}")
    private LocalDate dataInizioServizio;

    @NotNull(message = "{airbus.numeroPasseggeri.notnull}")
    @Min(value = 1, message = "{airbus.numeroPasseggeri.min}")
    @Max(value = 868, message = "{airbus.numeroPasseggeri.max}")
    private Integer numeroPasseggeri;

    private Boolean conSovrapposizioni;

    @JsonIgnoreProperties(value = { "airbus" })
    private Set<TrattaDTO> tratte;

    public AirbusDTO() {
    }

    public AirbusDTO(Long id_airbus, String codice, String descrizione, LocalDate dataInizioServizio, Integer numeroPasseggeri) {
        this.id_airbus = id_airbus;
        this.codice = codice;
        this.descrizione = descrizione;
        this.dataInizioServizio = dataInizioServizio;
        this.numeroPasseggeri = numeroPasseggeri;
    }

    public AirbusDTO(Long id, String codice, String descrizione, LocalDate dataInizioServizio, Integer numeroPasseggeri,Boolean conSovrapposizioni) {
        this(id, codice, descrizione, dataInizioServizio, numeroPasseggeri);
        this.conSovrapposizioni = conSovrapposizioni;
    }

    public Long getId_airbus() {
        return id_airbus;
    }

    public void setId_airbus(Long id) {
        this.id_airbus = id;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDate getDataInizioServizio() {
        return dataInizioServizio;
    }

    public void setDataInizioServizio(LocalDate dataInizioServizio) {
        this.dataInizioServizio = dataInizioServizio;
    }

    public Integer getNumeroPasseggeri() {
        return numeroPasseggeri;
    }

    public void setNumeroPasseggeri(Integer numeroPasseggeri) {
        this.numeroPasseggeri = numeroPasseggeri;
    }

    public Boolean getConSovrapposizioni() {
        return conSovrapposizioni;
    }

    public void setConSovrapposizioni(Boolean conSovrapposizioni) {
        this.conSovrapposizioni = conSovrapposizioni;
    }

    public Set<TrattaDTO> getTratte() {
        return tratte;
    }

    public void setTratte(Set<TrattaDTO> tratte) {
        this.tratte = tratte;
    }

    public Airbus buildAirbusModel() {
        return new Airbus(this.id_airbus, this.descrizione, this.codice, this.dataInizioServizio, this.numeroPasseggeri);
    }

    public static AirbusDTO buildAirbusDTOFromModel(Airbus model, boolean includeTratte) {
        AirbusDTO result = new AirbusDTO(model.getId_airbus(), model.getCodice(), model.getDescrizione(),
                model.getDataInizioServizio(), model.getNumeroPasseggeri());

        return result ;
    }

    public static AirbusDTO buildAirbusDTOConSovrapposizioni(Airbus model) {
        return new AirbusDTO(model.getId_airbus(), model.getCodice(), model.getDescrizione(), model.getDataInizioServizio(),
                model.getNumeroPasseggeri(), true);
    }

    public static List<AirbusDTO> buildAirbusDTOListFromModelList(Set<Airbus> modelList, boolean includeTratte) {
        return modelList.stream().map(element -> buildAirbusDTOFromModel(element, includeTratte)).collect(Collectors.toList());
    }
}
