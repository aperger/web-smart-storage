package hu.ps.ss.apiservice.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.io.Serial;
import java.util.Arrays;
import java.util.HashSet;

public class QuotedNamingStrategyMySql extends PhysicalNamingStrategyStandardImpl {

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

		boolean contains = RESERVED_WORDS.stream().anyMatch(word -> word.equalsIgnoreCase(name.getText()));

		if (contains) {
			return Identifier.quote(name);
		} else {
			return name;
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
