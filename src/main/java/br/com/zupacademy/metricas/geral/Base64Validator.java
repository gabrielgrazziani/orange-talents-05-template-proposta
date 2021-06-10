package br.com.zupacademy.metricas.geral;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Base64Validator implements ConstraintValidator<Base64, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(Objects.isNull(value)) return true;
		
		try {
			java.util.Base64.getDecoder().decode(value);			
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
		
	}

}
