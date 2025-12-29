package pl.pwr.paczkomaty.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pwr.paczkomaty.model.entity.ZadanieDystrybucyjne;
import pl.pwr.paczkomaty.service.ZadanieDystrybucyjneService;

import java.util.List;

@Controller
@RequestMapping("/zadania")
public class ZadanieController extends BaseController {

    @Autowired
    private ZadanieDystrybucyjneService zadanieService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String listaZadan(Model model) {
        List<ZadanieDystrybucyjne> zadania = zadanieService.pobierzWszystkieZadania();
        model.addAttribute("zadania", zadania);
        return "zadania/lista";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String szczegolyZadania(@PathVariable Integer id, Model model) {
        zadanieService.znajdzZadanie(id).ifPresent(zadanie -> {
            model.addAttribute("zadanie", zadanie);
        });
        return "zadania/szczegoly";
    }

    @GetMapping("/nowe")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String formularzNowegoZadania(Model model) {
        model.addAttribute("zadanie", new ZadanieDystrybucyjne());
        return "zadania/formularz";
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String zapiszZadanie(@Valid @ModelAttribute ZadanieDystrybucyjne zadanie, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("zadanie", zadanie);
            return "zadania/formularz";
        }
        zadanieService.utworzZadanie(zadanie);
        return "redirect:/zadania";
    }

    @PostMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String aktualizujStatus(@PathVariable Integer id, @RequestParam String status) {
        zadanieService.aktualizujStatusZadania(id, status);
        return "redirect:/zadania/" + id;
    }
}

