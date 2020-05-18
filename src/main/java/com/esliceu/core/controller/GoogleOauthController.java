package com.esliceu.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoogleOauthController {

    @GetMapping("/")
    public String helloWorld() {

        return "helloWorld";
    }

    @GetMapping
    public String restricted() {

        return "helloWorld";
    }
}
