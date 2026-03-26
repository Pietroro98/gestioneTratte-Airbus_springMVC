package it.prova.gestionetratte.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "airbus")
public class Airbus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_airbus")
    private Long id_airbus;

    @Column(name = "codice", nullable = false)
    private String codice;

    @Column(name = "descrizione", nullable = false)
    private String descrizione;

    @Column(name = "dataInizioServizio", nullable = false)
    private LocalDate dataInizioServizio;

    @Column(name = "numeroPasseggeri", nullable = false)
    private Integer numeroPasseggeri;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "airbus")
    private Set<Tratta> tratte = new HashSet<>(0);

    public Airbus() {
    }


    public Airbus(Long id_airbus, String codice, String descrizione, LocalDate dataInizioServizio, Integer numeroPasseggeri) {
        this.id_airbus = id_airbus;
        this.codice = codice;
        this.descrizione = descrizione;
        this.dataInizioServizio = dataInizioServizio;
        this.numeroPasseggeri = numeroPasseggeri;
    }

    public Airbus(String codice, String descrizione, LocalDate dataInizioServizio, Integer numeroPasseggeri) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.dataInizioServizio = dataInizioServizio;
        this.numeroPasseggeri = numeroPasseggeri;
    }

    public Long getId_airbus() {
        return id_airbus;
    }

    public void setId_airbus(Long id_airbus) {
        this.id_airbus = id_airbus;
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

    public Set<Tratta> getTratte() {
        return tratte;
    }

    public void setTratte(Set<Tratta> tratte) {
        this.tratte = tratte;
    }
}
