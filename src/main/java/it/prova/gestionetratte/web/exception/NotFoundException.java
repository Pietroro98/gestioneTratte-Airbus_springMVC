package it.prova.gestionetratte.web.exception;

public class NotFoundException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public NotFoundException(long id) {
		super("Il record con id " + id + " non è stato trovato!");
	}

	public NotFoundException(String msg) {
		super(msg);
	}
}