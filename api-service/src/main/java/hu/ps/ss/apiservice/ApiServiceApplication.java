package hu.ps.ss.apiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Web Smart Storage application.
 * This is the only bootable module in the hexagonal architecture.
 */
@SpringBootApplication(scanBasePackages = {
    "hu.ps.ss.apiservice",
    "hu.ps.ss.data",
    "hu.ps.ss.infra"
})
public class ApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiServiceApplication.class, args);
    }

}
