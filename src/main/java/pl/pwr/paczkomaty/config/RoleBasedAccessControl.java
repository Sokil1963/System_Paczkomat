package pl.pwr.paczkomaty.config;

import org.springframework.stereotype.Component;
import pl.pwr.paczkomaty.model.entity.Uzytkownik;

/**
 * Klasa do kontroli dostępu opartej na rolach
 */
@Component
public class RoleBasedAccessControl {

    // Symulacja aktualnie zalogowanego użytkownika (w prawdziwej aplikacji użyj Spring Security)
    private static ThreadLocal<Uzytkownik> currentUser = new ThreadLocal<>();

    public static void setCurrentUser(Uzytkownik uzytkownik) {
        currentUser.set(uzytkownik);
    }

    public static Uzytkownik getCurrentUser() {
        return currentUser.get();
    }

    public static void clearCurrentUser() {
        currentUser.remove();
    }

    /**
     * Sprawdza czy użytkownik ma rolę ADMIN
     */
    public static boolean isAdmin(Uzytkownik uzytkownik) {
        return uzytkownik != null && "ADMIN".equals(uzytkownik.getRola());
    }

    /**
     * Sprawdza czy użytkownik ma rolę KURIER
     */
    public static boolean isKurier(Uzytkownik uzytkownik) {
        return uzytkownik != null && "KURIER".equals(uzytkownik.getRola());
    }

    /**
     * Sprawdza czy użytkownik ma rolę FIXER
     */
    public static boolean isFixer(Uzytkownik uzytkownik) {
        return uzytkownik != null && "FIXER".equals(uzytkownik.getRola());
    }

    /**
     * Sprawdza czy użytkownik może zarządzać paczkomatami
     * ADMIN i FIXER mogą zarządzać paczkomatami
     */
    public static boolean canManagePaczkomaty(Uzytkownik uzytkownik) {
        return isAdmin(uzytkownik) || isFixer(uzytkownik);
    }

    /**
     * Sprawdza czy użytkownik może zarządzać przesyłkami
     * ADMIN i KURIER mogą zarządzać przesyłkami
     */
    public static boolean canManagePrzesylki(Uzytkownik uzytkownik) {
        return isAdmin(uzytkownik) || isKurier(uzytkownik);
    }

    /**
     * Sprawdza czy użytkownik może zarządzać użytkownikami
     * Tylko ADMIN może zarządzać użytkownikami
     */
    public static boolean canManageUzytkownicy(Uzytkownik uzytkownik) {
        return isAdmin(uzytkownik);
    }

    /**
     * Sprawdza czy użytkownik może zarządzać sortowniami
     * Tylko ADMIN może zarządzać sortowniami
     */
    public static boolean canManageSortownie(Uzytkownik uzytkownik) {
        return isAdmin(uzytkownik);
    }

    /**
     * Sprawdza czy użytkownik może zarządzać zadaniami
     * ADMIN i KURIER mogą zarządzać zadaniami
     */
    public static boolean canManageZadania(Uzytkownik uzytkownik) {
        return isAdmin(uzytkownik) || isKurier(uzytkownik);
    }

    /**
     * Sprawdza czy użytkownik może zmieniać status paczkomatu
     * Tylko ADMIN i FIXER mogą zmieniać status paczkomatu
     */
    public static boolean canChangePaczkomatStatus(Uzytkownik uzytkownik) {
        return isAdmin(uzytkownik) || isFixer(uzytkownik);
    }

    /**
     * Sprawdza czy użytkownik może zgłaszać awarie
     * Wszyscy użytkownicy mogą zgłaszać awarie
     */
    public static boolean canReportAwaria(Uzytkownik uzytkownik) {
        return uzytkownik != null;
    }
}

