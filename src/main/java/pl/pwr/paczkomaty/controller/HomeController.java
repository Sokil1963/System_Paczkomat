package pl.pwr.paczkomaty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;

import java.util.Optional;

@Controller
public class HomeController extends BaseController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

}

