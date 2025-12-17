package pl.pwr.paczkomaty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pwr.paczkomaty.model.entity.Sortownia;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;
import pl.pwr.paczkomaty.service.SortowniaService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sortownie")
public class SortowniaController extends BaseController {

    @Autowired
    private SortowniaService sortowniaService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String listaSortowni(Model model) {
        List<Sortownia> sortownie = sortowniaService.pobierzWszystkieSortownie();
        model.addAttribute("sortownie", sortownie);
        return "sortownie/lista";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String szczegolySortowni(@PathVariable Integer id, Model model) {
        Optional<Sortownia> sortownia = sortowniaService.znajdzSortownie(id);
        if (sortownia.isPresent()) {
            model.addAttribute("sortownia", sortownia.get());
            return "sortownie/szczegoly";
        }
        return "redirect:/sortownie";
    }

    @GetMapping("/nowa")
    @PreAuthorize("hasRole('ADMIN')")
    public String formularzNowejSortowni(Model model) {
        model.addAttribute("sortownia", new Sortownia());
        return "sortownie/formularz";
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String zapiszSortownie(@ModelAttribute Sortownia sortownia) {
        sortowniaService.zapiszSortownie(sortownia);
        return "redirect:/sortownie";
    }

    @PostMapping("/{id}/usun")
    @PreAuthorize("hasRole('ADMIN')")
    public String usunSortownie(@PathVariable Integer id) {
        sortowniaService.usunSortownie(id);
        return "redirect:/sortownie";
    }
}

