package com.fadgiras.exos.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GestEmplo {
    @RequestMapping(value = "/gestEmplo")
    public String index() {

    return "gestEmplo.html";
}

}
