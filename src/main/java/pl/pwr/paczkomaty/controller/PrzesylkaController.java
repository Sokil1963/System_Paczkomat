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
import pl.pwr.paczkomaty.model.entity.Przesylka;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;
import pl.pwr.paczkomaty.service.PrzesylkaService;
import pl.pwr.paczkomaty.service.UzytkownikService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/przesylki")
public class PrzesylkaController extends BaseController {

    @Autowired
    private PrzesylkaService przesylkaService;

    @Autowired
    private UzytkownikService uzytkownikService;

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
            model.addAttribute("wszystkieStatusy", przesylkaService.pobierzWszystkieStatusy());
            model.addAttribute("historiaStatusow", przesylkaService.pobierzHistorieStatusow(przesylka.get().getId()));
            return "przesylki/szczegoly";
        }
        return "redirect:/przesylki";
    }

    @GetMapping("/szukaj")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String szukajPrzesylki(@RequestParam(required = false) Integer id, Model model) {
        if (id != null && id > 0) {
            Optional<Przesylka> przesylka = przesylkaService.znajdzPrzesylkePoNumerze(id);
            if (przesylka.isPresent()) {
                return "redirect:/przesylki/" + przesylka.get().getId();
            } else {
                model.addAttribute("error", "Nie znaleziono przesyłki o numerze: " + id);
            }
        }
        return "przesylki/szukaj";
    }

    @PostMapping("/{id}/kod_odbioru") 
     @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String aktualizujKodOdbioru(@PathVariable Integer id,
                                       @RequestParam("kod_odbioru") Integer kodOdbioru, @RequestParam String opis, @RequestParam Integer status) {

        przesylkaService.aktualizujPrzesylke(id, kodOdbioru, opis, status);

        return "redirect:/przesylki/" + id;
    }
    @PostMapping("/{id}/edytuj")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String edytujPrzesylke(@PathVariable Integer id,
                                  @RequestParam(required = false) Integer kodOdbioru,
                                  @RequestParam(required = false) String opis,
                                  @RequestParam(required = false) Integer statusId,
                                  @RequestParam(required = false) String komentarz,
                                  Authentication authentication,
                                  RedirectAttributes redirectAttributes) {
        Integer uzytkownikId = null;
        if (authentication != null) {
            Optional<Uzytkownik> uzytkownik = uzytkownikService.znajdzPoLoginie(authentication.getName());
            uzytkownikId = uzytkownik.map(Uzytkownik::getId).orElse(null);
        }
        przesylkaService.aktualizujPrzesylke(id, kodOdbioru, opis, statusId, uzytkownikId, komentarz);
        redirectAttributes.addFlashAttribute("success", "Przesyłka została zaktualizowana");
        return "redirect:/przesylki/" + id;
    }


    @PostMapping("/{id}/zmien-status")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String zmienStatus(@PathVariable Integer id,
                              @RequestParam Integer statusId,
                              @RequestParam(required = false) String komentarz,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes) {
        try {
            Integer uzytkownikId = null;
            if (authentication != null) {
                Optional<Uzytkownik> uzytkownik = uzytkownikService.znajdzPoLoginie(authentication.getName());
                uzytkownikId = uzytkownik.map(Uzytkownik::getId).orElse(null);
            }
            przesylkaService.zmienStatus(id, statusId, uzytkownikId, komentarz);
            redirectAttributes.addFlashAttribute("success", "Status przesyłki został zmieniony");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Błąd zmiany statusu: " + e.getMessage());
        }
        return "redirect:/przesylki/" + id;
    }
}

