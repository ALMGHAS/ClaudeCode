package com.bookmyseat.movieservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:8081}")
    private String serverPort;

    @Bean
    public OpenAPI movieServiceOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:" + serverPort);
        devServer.setDescription("Development server");

        Server prodServer = new Server();
        prodServer.setUrl("https://api.bookmyseat.com");
        prodServer.setDescription("Production server");

        Contact contact = new Contact();
        contact.setEmail("support@bookmyseat.com");
        contact.setName("BookMySeat Support");

        License license = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Movie Service API")
                .version("1.0.0")
                .contact(contact)
                .description("This API manages movies and showtimes for the BookMySeat application. " +
                        "It provides endpoints for creating, retrieving, updating, and deleting movies, " +
                        "as well as managing showtime schedules for theaters.")
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer));
    }
}