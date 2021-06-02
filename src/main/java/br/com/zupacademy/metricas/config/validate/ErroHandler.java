package br.com.zupacademy.metricas.config.validate;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroHandler {
	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public List<ErroDto> handler(MethodArgumentNotValidException exception) {
		return exception.getBindingResult().getFieldErrors()
			.stream()
			.map(f ->{
				String message = messageSource.getMessage(f, LocaleContextHolder.getLocale());
				return new ErroDto(f.getField(),message);
			})
			.collect(Collectors.toList());
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<?> handler(ApiException exception) {
		ErroDto erro = new ErroDto(exception.getCampo(), exception.getMensagem());
		
		return ResponseEntity.status(exception.getStatus()).body(List.of(erro));
	}
	
}
