package com.fadgiras.exos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fadgiras.exos.model.Employee;

@Controller
public class GestEmplo {
    @RequestMapping(value = "/gestEmplo", method = RequestMethod.GET)
    public String index(Model model) {
        Employee employee= new Employee();
        model.addAttribute("employee", employee);
    return "gestEmplo.html";
}

}
