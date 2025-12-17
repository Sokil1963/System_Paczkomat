package pl.pwr.paczkomaty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;
import pl.pwr.paczkomaty.service.UzytkownikService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/uzytkownicy")
public class UzytkownikController extends BaseController {

    @Autowired
    private UzytkownikService uzytkownikService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String listaUzytkownikow(Model model) {
        List<Uzytkownik> uzytkownicy = uzytkownikService.pobierzWszystkichUzytkownikow();
        model.addAttribute("uzytkownicy", uzytkownicy);
        return "uzytkownicy/lista";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String szczegolyUzytkownika(@PathVariable Integer id, Model model) {
        Optional<Uzytkownik> uzytkownik = uzytkownikService.znajdzUzytkownika(id);
        if (uzytkownik.isPresent()) {
            model.addAttribute("uzytkownik", uzytkownik.get());
            return "uzytkownicy/szczegoly";
        }
        return "redirect:/uzytkownicy";
    }

    @GetMapping("/nowy")
    @PreAuthorize("hasRole('ADMIN')")
    public String formularzNowegoUzytkownika(Model model) {
        model.addAttribute("uzytkownik", new Uzytkownik());
        return "uzytkownicy/formularz";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String zapiszUzytkownika(@ModelAttribute Uzytkownik uzytkownik) {
        uzytkownikService.zapiszUzytkownika(uzytkownik);
        return "redirect:/uzytkownicy";
    }

    @PostMapping("/{id}/usun")
    @PreAuthorize("hasRole('ADMIN')")
    public String usunUzytkownika(@PathVariable Integer id) {
        uzytkownikService.usunUzytkownika(id);
        return "redirect:/uzytkownicy";
    }
}

