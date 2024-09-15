package com.bookingbustickets.bookingbustickets;

import com.bookingbustickets.bookingbustickets.configuration.KeycloakConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(KeycloakConfigurationProperties.class)
public class BookingBusTicketsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingBusTicketsApplication.class, args);
    }

}
