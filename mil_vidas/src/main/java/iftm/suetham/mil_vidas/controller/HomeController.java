package iftm.suetham.mil_vidas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String index(Model model){
        model.addAttribute("title", "Bem vindo ao Mil Vidas");
        return "fragments/layout";
    }
}