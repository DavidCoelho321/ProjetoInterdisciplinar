package com.scaapi.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IdexResource {

    @GetMapping()
    public String get() {
        return "SCA API";
    }
}