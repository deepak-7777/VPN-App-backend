package com.securevpn.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger / OpenAPI 3 configuration.
 * Access Swagger UI at: http://localhost:8080/swagger-ui/index.html
 *
 * TODO: Disable Swagger UI in production or restrict it to internal IPs only.
 *       Add this to application-prod.properties:
 *       springdoc.swagger-ui.enabled=false
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SecureVPN Backend API")
                        .version("1.0.0")
                        .description(
                            "Privacy-first backend for the SecureVPN Android app. " +
                            "Guest mode only. No user traffic data is collected or stored."
                        )
                        .contact(new Contact()
                                .name("SecureVPN Support")
                                .email("support@your-domain.com")
                                .url("https://your-domain.com"))
                        .license(new License()
                                .name("Proprietary")
                                .url("https://your-domain.com/terms")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Dev"),
                        // TODO: Add your production server URL here
                        new Server().url("https://your-domain.com").description("Production")
                ));
    }
}
