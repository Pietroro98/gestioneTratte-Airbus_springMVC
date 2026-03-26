package it.prova.gestionetratte.dto;

import java.util.ArrayList;
import java.util.List;

public class ConcludiTratteResponseDTO {

    private List<TrattaDTO> tratte = new ArrayList<>();
    private String message;

    public ConcludiTratteResponseDTO() {
    }

    public ConcludiTratteResponseDTO(List<TrattaDTO> tratte, String message) {
        this.tratte = tratte;
        this.message = message;
    }

    public List<TrattaDTO> getTratte() {
        return tratte;
    }

    public void setTratte(List<TrattaDTO> tratte) {
        this.tratte = tratte;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
