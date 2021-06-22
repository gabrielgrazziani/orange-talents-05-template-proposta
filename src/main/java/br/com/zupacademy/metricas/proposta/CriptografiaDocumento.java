package br.com.zupacademy.metricas.proposta;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class CriptografiaDocumento {

	private static final String segredo = "4864894894648949154";
	private static final String salt = "8232E57EC6787E0EF178";
	
	public static String criptografa(String texto) {
		TextEncryptor encryptor = Encryptors.text(segredo, salt);
		return encryptor.encrypt(texto);
	}
	
	public static String descriptografa(String texto) {
		TextEncryptor encryptor = Encryptors.text(segredo, salt);
		return encryptor.decrypt(texto);
	}
}
