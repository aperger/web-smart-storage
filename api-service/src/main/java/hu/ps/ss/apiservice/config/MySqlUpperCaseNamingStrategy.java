package hu.ps.ss.apiservice.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.io.Serial;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

public class MySqlUpperCaseNamingStrategy extends PhysicalNamingStrategyStandardImpl {

	@Serial
    private static final long serialVersionUID = 3404084681051088098L;

	/**
	 * The reserved words of SQL database
	 * 
	 * The HashSet is ordered, so binary search will be used!
	 */
	protected static final HashSet<String> RESERVED_WORDS = new HashSet<>(
			Arrays.asList(new String[] { "user", "password", "role", "source", "resource", "organization", "rank", "row_number", "timestamp" }));

	/**
	 * Add quotes to the reserved words
	 * 
	 * @param name
	 * @return
	 */
	private Identifier addQuotes2ReservedWords(Identifier name) {
		if (name == null) {
			return null;
		}
		
		Identifier identifier = new Identifier(name.getText().toUpperCase(Locale.ENGLISH), false);

		boolean contains = RESERVED_WORDS.stream().anyMatch(word -> word.equalsIgnoreCase(identifier.getText()));

		if (contains) {
			return Identifier.quote(identifier);
		} else {
			return identifier;
		}
	}

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		return addQuotes2ReservedWords(name);
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
		return addQuotes2ReservedWords(name);
	}
}
