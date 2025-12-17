package pl.pwr.paczkomaty.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pwr.paczkomaty.model.entity.Przesylka;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;
import pl.pwr.paczkomaty.service.PrzesylkaService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/przesylki")
public class PrzesylkaController extends BaseController {

    @Autowired
    private PrzesylkaService przesylkaService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String listaPrzesylek(Model model) {
        List<Przesylka> przesylki = przesylkaService.znajdzPrzesylkiDoWydania(null, null);
        model.addAttribute("przesylki", przesylki);
        return "przesylki/lista";
    }

    @GetMapping("/nowa")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String formularzNowejPrzesylki(Model model) {
        model.addAttribute("przesylka", new Przesylka());
        return "przesylki/formularz";
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String zapiszPrzesylke(@Valid @ModelAttribute Przesylka przesylka, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("przesylka", przesylka);
            return "przesylki/formularz";
        }
        przesylkaService.zapiszPrzesylke(przesylka);
        return "redirect:/przesylki";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String szczegolyPrzesylki(@PathVariable Integer id, Model model) {
        Optional<Przesylka> przesylka = przesylkaService.znajdzPrzesylke(id);
        if (przesylka.isPresent()) {
            model.addAttribute("przesylka", przesylka.get());
            return "przesylki/szczegoly";
        }
        return "redirect:/przesylki";
    }

    @GetMapping("/szukaj")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String szukajPrzesylki(@RequestParam(required = false) Long numer, Model model) {
        if (numer != null && numer > 0) {
            Optional<Przesylka> przesylka = przesylkaService.znajdzPrzesylkePoNumerze(numer);
            if (przesylka.isPresent()) {
                return "redirect:/przesylki/" + przesylka.get().getId();
            } else {
                model.addAttribute("error", "Nie znaleziono przesyłki o numerze: " + numer);
            }
        }
        return "przesylki/szukaj";
    }

    @PostMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String aktualizujStatus(@PathVariable Integer id,
                                   @RequestParam String kodStatusu,
                                   @RequestParam(required = false) String opis) {
        Integer loggedUserId = null;
        Optional<Uzytkownik> userOpt = getCurrentUser();
        if (userOpt.isPresent()) {
            loggedUserId = userOpt.get().getId();
        }
        przesylkaService.aktualizujStatusPrzesylki(id, kodStatusu, opis, loggedUserId);
        return "redirect:/przesylki/" + id;
    }
}

