package iftm.suetham.mil_vidas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import iftm.suetham.mil_vidas.domain.Login;

@Controller
public class LoginController{

    @GetMapping("/")
    public String telaInicial(Model model){
        return "login/login";
    }

    @PostMapping("/login/entrar")
    public String logar(Login loginDigitado, Model model){
        model.addAttribute("mensaagem", "Bem vindo!");
        return "login/login";
    }
}