package pl.pwr.paczkomaty.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pwr.paczkomaty.model.entity.Przesylka;
import pl.pwr.paczkomaty.service.OdbiorcaPrzesylkiService;

import java.util.Optional;


@Controller
@RequestMapping("/odbior")
public class OdbiorcaPrzesylkiController {

    @Autowired
    private OdbiorcaPrzesylkiService odbiorcaService;

    @GetMapping
    public String formularzOdbioru(Model model) {
        return "odbior/formularz";
    }


    @PostMapping("/sprawdz")
    public String sprawdzPrzesylke(@RequestParam Integer kodOdbioru, Model model,
                                    RedirectAttributes redirectAttributes) {
        if (kodOdbioru == null || kodOdbioru <= 0) {
            redirectAttributes.addFlashAttribute("error", "Nieprawidłowy kod odbioru");
            return "redirect:/odbior";
        }

        Optional<Przesylka> przesylka = odbiorcaService.znajdzPoKodzieOdbioru(kodOdbioru);

        if (przesylka.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Nie znaleziono przesyłki o podanym kodzie");
            return "redirect:/odbior";
        }

        Przesylka p = przesylka.get();
        model.addAttribute("przesylka", p);
        model.addAttribute("kodOdbioru", kodOdbioru);
        model.addAttribute("gotowaDoOdbioru", odbiorcaService.czyGotowaDoOdbioru(p));

        return "odbior/sprawdz";
    }


    @PostMapping("/odbierz")
    public String odbierzPrzesylke(@RequestParam Integer kodOdbioru,
                                    RedirectAttributes redirectAttributes) {
        try {
            Przesylka przesylka = odbiorcaService.odbierzPrzesylke(kodOdbioru);
            redirectAttributes.addFlashAttribute("success", "Przesyłka została odebrana!");
            redirectAttributes.addFlashAttribute("przesylka", przesylka);
            return "redirect:/odbior/sukces";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Nie znaleziono przesyłki: " + e.getMessage());
            return "redirect:/odbior";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/odbior";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Błąd podczas odbioru: " + e.getMessage());
            return "redirect:/odbior";
        }
    }

    @GetMapping("/sukces")
    public String sukces(Model model) {
        return "odbior/sukces";
    }


    @GetMapping("/api/status/{kodOdbioru}")
    @ResponseBody
    public String sprawdzStatus(@PathVariable Integer kodOdbioru) {
        return odbiorcaService.pobierzStatusDlaOdbiorcy(kodOdbioru);
    }
}

