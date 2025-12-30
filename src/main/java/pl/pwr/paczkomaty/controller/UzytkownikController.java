package pl.pwr.paczkomaty.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String szczegolyUzytkownika(@PathVariable Integer id, Model model, Authentication auth) {
        Optional<Uzytkownik> uzytkownikOpt = uzytkownikService.znajdzUzytkownika(id);

        if (uzytkownikOpt.isPresent()) {
            Uzytkownik u = uzytkownikOpt.get();
            boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            // Sprawdzenie: Czy to admin LUB czy użytkownik edytuje samego siebie
            if (isAdmin || u.getLogin().equals(auth.getName())) {
                model.addAttribute("uzytkownik", u);
                return "uzytkownicy/szczegoly";
            }
        }
        return "redirect:/403"; // lub redirect:/uzytkownicy
    }
    @PostMapping("/{id}/edytuj")
    public String edytujUzytkownika(@PathVariable Integer id,
                                    @RequestParam String login,
                                    @RequestParam(required = false) String haslo,
                                    Authentication auth) {
        Optional<Uzytkownik> uzytkownikOpt = uzytkownikService.znajdzUzytkownika(id);
        if (uzytkownikOpt.isPresent()) {
            Uzytkownik u = uzytkownikOpt.get();
            boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            if (isAdmin || u.getLogin().equals(auth.getName())) {
                uzytkownikService.aktualizujUzytkownika(id, login, haslo);
                return "redirect:/uzytkownicy/" + id;
            }
        }
        return "redirect:/403";
    }

    @Valid
    @GetMapping("/nowy")
    @PreAuthorize("hasRole('ADMIN')")
    public String formularzNowegoUzytkownika(Model model) {
        model.addAttribute("uzytkownik", new Uzytkownik());
        return "uzytkownicy/formularz";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String zapiszUzytkownika(@Valid @ModelAttribute Uzytkownik uzytkownik, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("uzytkownik", uzytkownik);
            return "uzytkownicy/formularz";
        }
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

