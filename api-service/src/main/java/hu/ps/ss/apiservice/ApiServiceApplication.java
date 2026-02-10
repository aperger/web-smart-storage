package hu.ps.ss.apiservice;

import hu.ps.ss.data.entity.CountryEntity;
import hu.ps.ss.infra.database.repository.CountryRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main entry point for the Web Smart Storage application.
 * This is the only bootable module in the hexagonal architecture.
 */
@SpringBootApplication(scanBasePackages = {
    "hu.ps.ss.apiservice",
    "hu.ps.ss.data",
    "hu.ps.ss.infra"
})
@EntityScan(basePackageClasses = {CountryEntity.class})
@EnableJpaRepositories(basePackageClasses = {CountryRepository.class})
public class ApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiServiceApplication.class, args);
    }

}
