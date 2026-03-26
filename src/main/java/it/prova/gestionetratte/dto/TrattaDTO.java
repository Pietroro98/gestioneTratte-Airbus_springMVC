package it.prova.gestionetratte.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import it.prova.gestionetratte.model.StatoTratta;
import it.prova.gestionetratte.model.Tratta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrattaDTO {

    private Long id;

    @NotBlank(message = "Il campo Codice deve essere valorizzato")
    private String codice;

    @NotBlank(message = "Il campo Descrizione deve essere valorizzato")
    private String descrizione;

    @NotNull(message = "Il campo Data deve essere valorizzato")
    private LocalDate data;

    @NotNull(message = "Il campo Ora Decollo deve essere valorizzato")
    private LocalTime oraDecollo;

    @NotNull(message = "Il campo Ora Atterraggio deve essere valorizzato")
    private LocalTime oraAtterraggio;

    private StatoTratta stato;

    @JsonIgnoreProperties(value = { "tratte" })
    @NotNull(message = "Il campo Airbus deve essere valorizzato")
    private AirbusDTO airbus;

    public TrattaDTO() {
    }

    public TrattaDTO(Long id, String codice, String descrizione, LocalDate data, LocalTime oraDecollo,
                     LocalTime oraAtterraggio, StatoTratta stato) {
        this.id = id;
        this.codice = codice;
        this.descrizione = descrizione;
        this.data = data;
        this.oraDecollo = oraDecollo;
        this.oraAtterraggio = oraAtterraggio;
        this.stato = stato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getOraDecollo() {
        return oraDecollo;
    }

    public void setOraDecollo(LocalTime oraDecollo) {
        this.oraDecollo = oraDecollo;
    }

    public LocalTime getOraAtterraggio() {
        return oraAtterraggio;
    }

    public void setOraAtterraggio(LocalTime oraAtterraggio) {
        this.oraAtterraggio = oraAtterraggio;
    }

    public StatoTratta getStato() {
        return stato;
    }

    public void setStato(StatoTratta stato) {
        this.stato = stato;
    }

    public AirbusDTO getAirbus() {
        return airbus;
    }

    public void setAirbus(AirbusDTO airbus) {
        this.airbus = airbus;
    }

    public Tratta buildTrattaModel() {
        Tratta result = new Tratta(this.id, this.codice, this.descrizione, this.data, this.oraDecollo,
                this.oraAtterraggio, this.stato);
        if (this.airbus != null) {
            result.setAirbus(this.airbus.buildAirbusModel());
        }
        return result;
    }

    public static TrattaDTO buildTrattaDTOFromModel(Tratta model, boolean includeAirbus) {
        TrattaDTO result = new TrattaDTO(model.getId(), model.getCodice(), model.getDescrizione(), model.getData(),
                model.getOraDecollo(), model.getOraAtterraggio(), model.getStato());
        if (includeAirbus) {
            result.setAirbus(AirbusDTO.buildAirbusDTOFromModel(model.getAirbus(), false));
        }
        return result;
    }

    public static List<TrattaDTO> createTrattaDTOListFromModelList(List<Tratta> modelList, boolean includeAirbus) {
        return modelList.stream().map(element -> buildTrattaDTOFromModel(element, includeAirbus)).collect(Collectors.toList());
    }

    public static Set<TrattaDTO> createTrattaDTOSetFromModelSet(Set<Tratta> modelSet, boolean includeAirbus) {
        return modelSet.stream().map(element -> buildTrattaDTOFromModel(element, includeAirbus)).collect(Collectors.toSet());
    }
}
