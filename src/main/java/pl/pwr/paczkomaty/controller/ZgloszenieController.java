package pl.pwr.paczkomaty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;
import pl.pwr.paczkomaty.model.entity.ZgloszenieNieprawidlowejPrzesylki;
import pl.pwr.paczkomaty.service.PrzesylkaService;
import pl.pwr.paczkomaty.service.UzytkownikService;
import pl.pwr.paczkomaty.service.ZgloszenieNieprawidlowejPrzesylkiService;

import java.util.Optional;


@Controller
@RequestMapping("/zgloszenia")
public class ZgloszenieController extends BaseController {

    @Autowired
    private ZgloszenieNieprawidlowejPrzesylkiService zgloszenieService;

    @Autowired
    private PrzesylkaService przesylkaService;

    @Autowired
    private UzytkownikService uzytkownikService;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String listaZgloszen(Model model) {
        model.addAttribute("zgloszenia", zgloszenieService.pobierzWszystkieZgloszenia());
        return "zgloszenia/lista";
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String szczegolyZgloszenia(@PathVariable Integer id, Model model) {
        zgloszenieService.znajdzZgloszenie(id).ifPresent(zgloszenie -> {
            model.addAttribute("zgloszenie", zgloszenie);
        });
        return "zgloszenia/szczegoly";
    }

    @GetMapping("/nowe")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String formularzZgloszenia(@RequestParam(required = false) Integer przesylkaId, Model model) {
        model.addAttribute("typy", zgloszenieService.pobierzDostepneTypy());

        if (przesylkaId != null) {
            przesylkaService.znajdzPrzesylke(przesylkaId).ifPresent(przesylka ->
                model.addAttribute("przesylka", przesylka));
        }

        model.addAttribute("przesylkaId", przesylkaId);
        model.addAttribute("przesylki", przesylkaService.znajdzPrzesylkiDoWydania(null, null));

        return "zgloszenia/formularz";
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String utworzZgloszenie(@RequestParam Integer przesylkaId,
                                   @RequestParam String typ,
                                   @RequestParam(required = false) String opis,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes) {
        try {
            Integer uzytkownikId = null;
            if (authentication != null) {
                Optional<Uzytkownik> uzytkownik = uzytkownikService.znajdzPoLoginie(authentication.getName());
                uzytkownikId = uzytkownik.map(Uzytkownik::getId).orElse(null);
            }

            ZgloszenieNieprawidlowejPrzesylki zgloszenie =
                    zgloszenieService.utworzZgloszenie(przesylkaId, uzytkownikId, typ, opis);

            redirectAttributes.addFlashAttribute("success",
                    "Zgłoszenie #" + zgloszenie.getId() + " zostało utworzone. Status przesyłki został zaktualizowany.");
            return "redirect:/zgloszenia/" + zgloszenie.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Błąd tworzenia zgłoszenia: " + e.getMessage());
            return "redirect:/zgloszenia/nowe?przesylkaId=" + przesylkaId;
        }
    }


    @PostMapping("/{id}/usun")
    @PreAuthorize("hasRole('ADMIN')")
    public String usunZgloszenie(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            zgloszenieService.usunZgloszenie(id);
            redirectAttributes.addFlashAttribute("success", "Zgłoszenie zostało usunięte");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Błąd usuwania zgłoszenia: " + e.getMessage());
        }
        return "redirect:/zgloszenia";
    }


    @GetMapping("/typ/{typ}")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String zgloszeniaPoTypie(@PathVariable String typ, Model model) {
        model.addAttribute("zgloszenia", zgloszenieService.znajdzZgloszeniaPoTypie(typ));
        model.addAttribute("filtrowanyTyp", typ);
        return "zgloszenia/lista";
    }
}

