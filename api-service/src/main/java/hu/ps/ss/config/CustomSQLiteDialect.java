package hu.ps.ss.config;
/*
import org.sqlite.hibernate.dialect.SQLiteDialect;

import java.sql.Types;

/**
 * Custom SQLite Dialect for Hibernate 5.4
 * Handles SQLite's type system and BLOB column validation issues - not working properly yet!!!
 * /
public class CustomSQLiteDialect extends SQLiteDialect {

    public CustomSQLiteDialect() {
        // Numeric types
        super();

        registerColumnType(Types.BIT, "integer");
        registerColumnType(Types.TINYINT, "tinyint");
        registerColumnType(Types.SMALLINT, "smallint");
        registerColumnType(Types.INTEGER, "integer");
        registerColumnType(Types.BIGINT, "bigint");
        registerColumnType(Types.FLOAT, "float");
        registerColumnType(Types.DOUBLE, "double");

        // text mappings
        registerColumnType(Types.VARCHAR, "text");
        registerColumnType(Types.LONGVARCHAR, "text");
        registerColumnType(Types.VARCHAR, 255, "varchar($l)");

        // map binary types to longblob to match SQLite JDBC metadata (prevents validation mismatch)
        registerColumnType(Types.VARBINARY, "longblob");
        registerColumnType(Types.LONGVARBINARY, "longblob");
        registerColumnType(Types.BINARY, "blob");
        registerColumnType(Types.BLOB, "longblob");

        registerColumnType(Types.BOOLEAN, "integer");

        registerColumnType(Types.DECIMAL, "decimal($p, $s)");
        registerColumnType(Types.NUMERIC, "numeric($p, $s)");
        registerColumnType(Types.TIMESTAMP, "timestamp");
    }

}
*/
