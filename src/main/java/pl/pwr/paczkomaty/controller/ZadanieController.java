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
import pl.pwr.paczkomaty.model.entity.ZadanieDystrybucyjne;
import pl.pwr.paczkomaty.service.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/zadania")
public class ZadanieController extends BaseController {

    @Autowired
    private ZadanieDystrybucyjneService zadanieService;

    @Autowired
    private UzytkownikService uzytkownikService;

    @Autowired
    private SortowniaService sortowniaService;

    @Autowired
    private PrzesylkaService przesylkaService;

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
            model.addAttribute("kurierzy", uzytkownikService.znajdzKurierow());
            model.addAttribute("sortownie", sortowniaService.pobierzWszystkieSortownie());
            model.addAttribute("przesylki", przesylkaService.znajdzPrzesylkiDoWydania(null, null));
        });
        return "zadania/szczegoly";
    }

    @GetMapping("/nowe")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String formularzNowegoZadania(Model model) {
        model.addAttribute("zadanie", new ZadanieDystrybucyjne());
        model.addAttribute("kurierzy", uzytkownikService.znajdzKurierow());
        model.addAttribute("sortownie", sortowniaService.pobierzWszystkieSortownie());
        return "zadania/formularz";
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String zapiszZadanie(@Valid @ModelAttribute ZadanieDystrybucyjne zadanie, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("zadanie", zadanie);
            model.addAttribute("kurierzy", uzytkownikService.znajdzKurierow());
            model.addAttribute("sortownie", sortowniaService.pobierzWszystkieSortownie());
            return "zadania/formularz";
        }
        zadanieService.utworzZadanie(zadanie);
        return "redirect:/zadania";
    }

    @PostMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String aktualizujStatus(@PathVariable Integer id,@RequestParam String typ, @RequestParam String status) {
        zadanieService.edytujZadanie(id,typ, status);
        return "redirect:/zadania/" + id;
    }

    @PostMapping("/{id}/edytuj")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String edytujZadanie(@PathVariable Integer id,
                                @RequestParam String typ,
                                @RequestParam String opis) {
        zadanieService.edytujZadanie(id, typ, opis);
        return "redirect:/zadania/" + id;
    }


    @PostMapping("/{id}/przydziel-kuriera")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String przydzielKuriera(@PathVariable Integer id,
                                   @RequestParam Integer kurierId,
                                   RedirectAttributes redirectAttributes) {
        try {
            zadanieService.przydzielKuriera(id, kurierId);
            redirectAttributes.addFlashAttribute("success", "Kurier został przydzielony do zadania");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Błąd: " + e.getMessage());
        }
        return "redirect:/zadania/" + id;
    }


    @PostMapping("/{id}/przydziel-sortownie")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String przydzielSortownie(@PathVariable Integer id,
                                     @RequestParam Integer sortowniaId,
                                     RedirectAttributes redirectAttributes) {
        try {
            zadanieService.przydzielSortownie(id, sortowniaId);
            redirectAttributes.addFlashAttribute("success", "Sortownia została przydzielona do zadania");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Błąd: " + e.getMessage());
        }
        return "redirect:/zadania/" + id;
    }


    @PostMapping("/{id}/dodaj-przesylki")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String dodajPrzesylki(@PathVariable Integer id,
                                 @RequestParam List<Integer> przesylkaIds,
                                 RedirectAttributes redirectAttributes) {
        try {
            zadanieService.dodajPrzesylkiDoZadania(id, przesylkaIds);
            redirectAttributes.addFlashAttribute("success", "Przesyłki zostały dodane do zadania");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Błąd: " + e.getMessage());
        }
        return "redirect:/zadania/" + id;
    }


    @PostMapping("/{id}/usun-przesylke/{przesylkaId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String usunPrzesylke(@PathVariable Integer id,
                                @PathVariable Integer przesylkaId,
                                RedirectAttributes redirectAttributes) {
        try {
            zadanieService.usunPrzesylkeZZadania(id, przesylkaId);
            redirectAttributes.addFlashAttribute("success", "Przesyłka została usunięta z zadania");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Błąd: " + e.getMessage());
        }
        return "redirect:/zadania/" + id;
    }


    @PostMapping("/{id}/wydaj-kurierowi")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String wydajKurierowi(@PathVariable Integer id,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes) {
        try {
            Integer uzytkownikId = getUzytkownikId(authentication);
            zadanieService.wydajPrzesylkiKurierowi(id, uzytkownikId);
            redirectAttributes.addFlashAttribute("success", "Przesyłki zostały wydane kurierowi");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Błąd: " + e.getMessage());
        }
        return "redirect:/zadania/" + id;
    }



    @PostMapping("/{id}/odbierz-od-kuriera")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String odbierzOdKuriera(@PathVariable Integer id,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes) {
        try {
            Integer uzytkownikId = getUzytkownikId(authentication);
            zadanieService.odbierzOdKuriera(id, uzytkownikId);
            redirectAttributes.addFlashAttribute("success", "Przesyłki zostały odebrane od kuriera");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Błąd: " + e.getMessage());
        }
        return "redirect:/zadania/" + id;
    }



    @PostMapping("/przesylka/{przesylkaId}/zglos-wydanie")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String zglosWydanie(@PathVariable Integer przesylkaId,
                               Authentication authentication,
                               RedirectAttributes redirectAttributes) {
        try {
            Integer uzytkownikId = getUzytkownikId(authentication);
            zadanieService.zgloszenieWydania(przesylkaId, uzytkownikId);
            redirectAttributes.addFlashAttribute("success", "Przesyłka zgłoszona jako gotowa do odbioru");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Błąd: " + e.getMessage());
        }
        return "redirect:/przesylki/" + przesylkaId;
    }



    @PostMapping("/przesylka/{przesylkaId}/zglos-do-przewozu")
    @PreAuthorize("hasAnyRole('ADMIN', 'KURIER')")
    public String zglosDoPrzewozu(@PathVariable Integer przesylkaId,
                                  Authentication authentication,
                                  RedirectAttributes redirectAttributes) {
        try {
            Integer uzytkownikId = getUzytkownikId(authentication);
            zadanieService.zgloszenieDoPrzewozu(przesylkaId, uzytkownikId);
            redirectAttributes.addFlashAttribute("success", "Przesyłka zgłoszona do przewozu");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Błąd: " + e.getMessage());
        }
        return "redirect:/przesylki/" + przesylkaId;
    }

    private Integer getUzytkownikId(Authentication authentication) {
        if (authentication != null) {
            Optional<Uzytkownik> uzytkownik = uzytkownikService.znajdzPoLoginie(authentication.getName());
            return uzytkownik.map(Uzytkownik::getId).orElse(null);
        }
        return null;
    }
}

