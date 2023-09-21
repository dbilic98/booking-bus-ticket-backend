package com.bookingbustickets.bookingbustickets.contorller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN_ROLE')")
    public String test(){
        return "Hello";
    }
}
