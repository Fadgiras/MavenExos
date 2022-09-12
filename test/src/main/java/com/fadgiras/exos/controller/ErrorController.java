package com.fadgiras.exos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {
    @GetMapping("/error")
	public String index(){
        return "<h1>Nope</h1>";
    }
}
