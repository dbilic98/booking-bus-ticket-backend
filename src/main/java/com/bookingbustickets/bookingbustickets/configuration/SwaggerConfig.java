package com.bookingbustickets.bookingbustickets.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bus management system REST API")
                        .description("This API provides endpoints for booking bus tickets")
                        .version("1.0"));
    }
}

