package pl.pwr.paczkomaty.e2e;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class E2ETestsSortownia extends BaseE2ETest {


    @Test
    @DisplayName("list of parcels should be visible to admin")
    void testAdminLoginSuccess() {
        loginAsAdmin();

        waitForVisible(page, "#username");
        assertTrue(page.textContent("#username").contains(ADMIN_LOGIN),
                "Name of logged in admin as 'admin' should be visible on dashboard");
    }


    @Test
    @DisplayName("session should persist across tabs")
    void testSessionPersistsAcrossTabs() {
        loginAsAdmin();

        Page page2 = context.newPage();
        navigateTo(page2, "/");

        waitForVisible(page2, "text=Witaj w systemie zarządzania siecią paczkomatów");

        assertVisible(page2, "text=System Zarządzania Siecią Paczkomatów",
                "dashboard should be visible in new tab without re-login");

        page2.close();
    }


    @Test
    @DisplayName("Saved storage state should allow reuse of session")
    void testStorageStateReuse() {
        loginAsAdmin();

        try (BrowserContext authenticatedContext = createAuthenticatedContext()) {
            Page newPage = authenticatedContext.newPage();
            navigateTo(newPage, "/sortownie");

            waitForVisible(newPage, "text=Lista Sortowni");

            assertVisible(newPage, "text=Lista Sortowni",
                    "should be able to access sortownie page with saved storage state");
        }
    }

    @Test
    @DisplayName("Creation of new sortownia should work correctly")
    void testCreateNewSortownia() {
        loginAsAdmin();

        try (BrowserContext authContext = createAuthenticatedContext()) {
            Page sortowniaPage = authContext.newPage();
            navigateTo(sortowniaPage, "/sortownie");

            waitForVisible(sortowniaPage, "text=Lista Sortowni");

            Locator createButton = sortowniaPage.getByRole(AriaRole.LINK,
                    new Page.GetByRoleOptions().setName("Dodaj nową sortownię"));
            createButton.click();

            waitForVisible(sortowniaPage, "#nazwa");
            sortowniaPage.fill("#nazwa", "Testowa Sortownia");
            sortowniaPage.fill("#adres", "Testowy Adres 123");
            sortowniaPage.click("button[type='submit']");

            waitForVisible(sortowniaPage, "text=Testowa Sortownia");

            assertVisible(sortowniaPage, "text=Testowa Sortownia",
                    "Creation of new sortownia should be visible in the list");
        }
    }
}
