package pl.pwr.paczkomaty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Kontroler logowania
 * Aутентификация obsługiwana jest przez Spring Security
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login";
    }


    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
}


