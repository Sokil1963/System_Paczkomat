package pl.pwr.paczkomaty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pwr.paczkomaty.model.entity.Paczkomat;
import pl.pwr.paczkomaty.model.entity.AwariaPaczkomatu;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;
import pl.pwr.paczkomaty.service.PaczkomatService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/paczkomaty")
public class PaczkomatController extends BaseController {

    @Autowired
    private PaczkomatService paczkomatService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FIXER')")
    public String listaPaczkomatow(Model model) {
        List<Paczkomat> paczkomaty = paczkomatService.pobierzWszystkiePaczkomaty();
        model.addAttribute("paczkomaty", paczkomaty);
        return "paczkomaty/lista";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FIXER')")
    public String szczegolyPaczkomatu(@PathVariable Integer id, Model model) {
        Optional<Paczkomat> paczkomat = paczkomatService.znajdzPaczkomat(id);
        if (paczkomat.isPresent()) {
            model.addAttribute("paczkomat", paczkomat.get());
            List<AwariaPaczkomatu> awarie = paczkomatService.pobierzAwarie(id);
            model.addAttribute("awarie", awarie);
            return "paczkomaty/szczegoly";
        }
        return "redirect:/paczkomaty";
    }

    @GetMapping("/nowy")
    @PreAuthorize("hasAnyRole('ADMIN', 'FIXER')")
    public String formularzNowegoPaczkomatu(Model model) {
        model.addAttribute("paczkomat", new Paczkomat());
        return "paczkomaty/formularz";
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FIXER')")
    public String zapiszPaczkomat(@ModelAttribute Paczkomat paczkomat) {
        paczkomatService.zapiszPaczkomat(paczkomat);
        return "redirect:/paczkomaty";
    }

    @PostMapping("/{id}/awaria")
    @PreAuthorize("hasAnyRole('ADMIN', 'FIXER')")
    public String zglosAwarie(@PathVariable Integer id,
                             @RequestParam String opis,
                             @RequestParam(required = false) Boolean wymagaBlokady) {
        // Użyj ID zalogowanego użytkownika
        Integer loggedUserId = null;
        Optional<Uzytkownik> userOpt = getCurrentUser();
        if (userOpt.isPresent()) {
            loggedUserId = userOpt.get().getId();
        }
        paczkomatService.zarejestrujAwariePaczkomatu(id, opis, loggedUserId, wymagaBlokady);
        return "redirect:/paczkomaty/" + id;
    }

    @PostMapping("/{id}/blokada")
    @PreAuthorize("hasAnyRole('ADMIN', 'FIXER')")
    public String zablokujPaczkomat(@PathVariable Integer id) {
        paczkomatService.zablokujPaczkomat(id, null);
        return "redirect:/paczkomaty/" + id;
    }

    @PostMapping("/{id}/odblokuj")
    @PreAuthorize("hasAnyRole('ADMIN', 'FIXER')")
    public String odblokujPaczkomat(@PathVariable Integer id) {
        paczkomatService.odblokujPaczkomat(id);
        return "redirect:/paczkomaty/" + id;
    }
}

