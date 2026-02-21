package hu.ps.ss.apiservice.config;


import java.sql.Types;
import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.Dialect;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.descriptor.sql.internal.DdlTypeImpl;
import org.hibernate.type.descriptor.sql.spi.DdlTypeRegistry;

/**
 * Custom SQLite Dialect for Hibernate 5.4 Handles SQLite's type system and BLOB column validation
 * issues - not working properly yet!!!
 */
public class CustomSQLiteDialect extends Dialect {

  @Override
  protected void registerColumnTypes(TypeContributions typeContributions,
      ServiceRegistry serviceRegistry) {
    super.registerColumnTypes(typeContributions, serviceRegistry);
    DdlTypeRegistry ddlTypeRegistry = typeContributions.getTypeConfiguration().getDdlTypeRegistry();
    ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.BIT, "integer", this));
    ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.TINYINT, "tinyint", this));
    ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.SMALLINT, "smallint", this));
    ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.INTEGER, "integer", this));
    ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.BIGINT, "bigint", this));
    ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.FLOAT, "float", this));
    ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.DOUBLE, "double", this));

    ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.VARCHAR, "varchar($l)", this));
    ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.LONGVARCHAR, "text", this));

    // map binary types to longblob to match SQLite JDBC metadata (prevents validation mismatch)
    ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.VARBINARY, "longblob", this));
    ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.LONGVARBINARY, "longblob", this));
    ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.BINARY, "blob", this));
    ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.BLOB, "longblob", this));

    ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.BOOLEAN, "integer", this));

    ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.DECIMAL, "decimal($p, $s)", this));
    ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.NUMERIC, "numeric($p, $s)", this));

    ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.TIMESTAMP, "timestamp", this));
  }

}

