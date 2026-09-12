package hu.ps.ss.apiservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of OpenAPI
 */
@Configuration
public class OpenApiConfiguration {


  /**
   * Bean of Configuration
   *
   * @param properties build properties
   * @return the configuration instance
   */
  @Bean
  public OpenAPI openApi(@Autowired(required = false) BuildProperties properties) {
    final var version = Objects.nonNull(properties) ? properties.getVersion() : "0.0.0";
    return new OpenAPI().info(new Info()
            .title("Web SmartStorage API")
            .description("API endpoints of SmartStorage application")
            .version(version))
        .schemaRequirement("bearerAuth", new io.swagger.v3.oas.models.security.SecurityScheme()
            .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT"))
        ;
  }
}
