package pl.pwr.paczkomaty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pwr.paczkomaty.config.RoleBasedAccessControl;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;
import pl.pwr.paczkomaty.repository.UzytkownikRepository;

import java.util.Optional;

/**
 * Bazowy kontroler z metodami pomocniczymi do kontroli dostępu
 * Używa Spring Security do zarządzania autentykacją
 */
public abstract class BaseController {

    @Autowired
    protected UzytkownikRepository uzytkownikRepository;

    /**
     * Pobiera aktualnego zalogowanego użytkownika z Spring Security
     */
    protected Optional<Uzytkownik> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        String username = authentication.getName();
        return uzytkownikRepository.findByLogin(username);
    }

    /**
     * Sprawdza czy użytkownik jest zalogowany
     */
    protected boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    /**
     * Sprawdza czy użytkownik ma dostęp i dodaje informacje o błędzie do modelu
     */
    protected boolean checkAccess(Model model, boolean hasAccess, String errorMessage) {
        if (!hasAccess) {
            model.addAttribute("error", errorMessage);
            return false;
        }
        return true;
    }

    /**
     * Sprawdza czy użytkownik może zarządzać paczkomatami
     */
    protected boolean canManagePaczkomaty(Uzytkownik uzytkownik) {
        return RoleBasedAccessControl.canManagePaczkomaty(uzytkownik);
    }

    /**
     * Sprawdza czy użytkownik może zarządzać przesyłkami
     */
    protected boolean canManagePrzesylki(Uzytkownik uzytkownik) {
        return RoleBasedAccessControl.canManagePrzesylki(uzytkownik);
    }

    /**
     * Sprawdza czy użytkownik może zarządzać użytkownikami
     */
    protected boolean canManageUzytkownicy(Uzytkownik uzytkownik) {
        return RoleBasedAccessControl.canManageUzytkownicy(uzytkownik);
    }

    /**
     * Sprawdza czy użytkownik może zarządzać sortowniami
     */
    protected boolean canManageSortownie(Uzytkownik uzytkownik) {
        return RoleBasedAccessControl.canManageSortownie(uzytkownik);
    }

    /**
     * Sprawdza czy użytkownik może zmieniać status paczkomatu
     */
    protected boolean canChangePaczkomatStatus(Uzytkownik uzytkownik) {
        return RoleBasedAccessControl.canChangePaczkomatStatus(uzytkownik);
    }
}

