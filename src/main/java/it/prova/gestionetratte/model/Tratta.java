package it.prova.gestionetratte.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tratta")
public class Tratta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tratta")
    private Long id_tratta;

    @Column(name = "codice", nullable = false)
    private String codice;

    @Column(name = "descrizione", nullable = false)
    private String descrizione;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "oraDecollo", nullable = false)
    private LocalTime oraDecollo;

    @Column(name = "oraAtterraggio", nullable = false)
    private LocalTime oraAtterraggio;

    @Column(name = "stato", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatoTratta stato = StatoTratta.ATTIVA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airbus_id", nullable = false)
    private Airbus airbus;

    public Tratta() {}

    public Tratta(Long id_tratta, String codice, String descrizione, LocalDate data, LocalTime oraDecollo, LocalTime oraAtterraggio, StatoTratta stato) {
        this.id_tratta = id_tratta;
        this.codice = codice;
        this.descrizione = descrizione;
        this.data = data;
        this.oraDecollo = oraDecollo;
        this.oraAtterraggio = oraAtterraggio;
        this.stato = stato;
    }

    public Long getId() {
        return id_tratta;
    }

    public void setId(Long id_tratta) {
        this.id_tratta = id_tratta;
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

    public Airbus getAirbus() {
        return airbus;
    }

    public void setAirbus(Airbus airbus) {
        this.airbus = airbus;
    }
}
