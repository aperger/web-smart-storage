package hu.ps.ss.config;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.descriptor.sql.internal.*;
import org.hibernate.type.descriptor.sql.spi.DdlTypeRegistry;

import java.sql.Types;

public class CustomPostgreSQLDialect extends PostgreSQLDialect {

	@Override
	protected void registerColumnTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
		super.registerColumnTypes(typeContributions, serviceRegistry);
		DdlTypeRegistry ddlTypeRegistry = typeContributions.getTypeConfiguration().getDdlTypeRegistry();
		ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.BLOB, "bytea", this));
		ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.BINARY, "bytea", this));
		ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.VARBINARY, "bytea", this));
		ddlTypeRegistry.addDescriptor(new DdlTypeImpl(Types.LONGVARBINARY, "bytea", this));
	}

}
