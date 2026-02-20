package hu.ps.ss.config;
/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
@Configuration
public class PasswordEncoderConfig {

	private Map<String, PasswordEncoder> passwordEncoders = null;

	public PasswordEncoderConfig() {
		createPasswordEncoders();
	}
	
	private void createPasswordEncoders() {
		passwordEncoders = new HashMap<>();
		passwordEncoders.put("bcrypt", new BCryptPasswordEncoder());
		passwordEncoders.put("ldap", new LdapShaPasswordEncoder());
		passwordEncoders.put("MD4", new Md4PasswordEncoder());
		passwordEncoders.put("MD5", new MessageDigestPasswordEncoder("MD5"));
		passwordEncoders.put("noop", NoOpPasswordEncoder.getInstance());
		passwordEncoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
		passwordEncoders.put("scrypt", new SCryptPasswordEncoder());
		passwordEncoders.put("SHA-1", new MessageDigestPasswordEncoder("SHA-1"));
		passwordEncoders.put("SHA-256", new MessageDigestPasswordEncoder("SHA-256"));
		passwordEncoders.put("SHA-512", new MessageDigestPasswordEncoder("SHA-512"));
		passwordEncoders.put("sha256", new StandardPasswordEncoder());
	}

	@Bean(name = "passwordEncoders")
	public Map<String, PasswordEncoder> getPasswordEncoders() {
		return passwordEncoders;
	}

	/**
	 * https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/password/DelegatingPasswordEncoder.html
	 * @return
	 * /
	@Bean
	public PasswordEncoder bcryptPasswordEncoder() {
		return new DelegatingPasswordEncoder("bcrypt", passwordEncoders);
	}

	@Bean
	public PasswordEncoder noopPasswordEncoder() {
		return new DelegatingPasswordEncoder("noop", passwordEncoders);
	}
}
*/