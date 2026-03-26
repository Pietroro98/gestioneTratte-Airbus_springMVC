package it.prova.gestionetratte.web.exception;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{

	// ----- 404 ----- //
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFound(NotFoundException ex, WebRequest request) {
		return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	// ----- 422 ----- //
	@ExceptionHandler(NotAllowedException.class)
	public ResponseEntity<Object> handleNotAllowedException(NotAllowedException ex, WebRequest request) {
		return buildErrorResponse(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	// ----- 500 ----- //
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
		return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	// ----- 400 ----- //
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String message = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.joining(", "));
		return buildErrorResponse(message, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return buildErrorResponse("Il body della richiesta non e' valido", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		String message = "Il parametro '" + ex.getName() + "' non e' nel formato corretto";
		return buildErrorResponse(message, HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", message);
		body.put("status", status);

		return new ResponseEntity<>(body, status);
	}

}
