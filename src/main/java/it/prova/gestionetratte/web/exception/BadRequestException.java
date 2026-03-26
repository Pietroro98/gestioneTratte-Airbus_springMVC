package it.prova.gestionetratte.web.exception;

public class BadRequestException extends RuntimeException
{
    public BadRequestException() {
        super("Problema lato server! Giuro che risolverò quanto prima! Ma magari...");
    }

    public BadRequestException(String message) {
        super(message);
    }
}
