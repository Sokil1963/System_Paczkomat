package pl.pwr.paczkomaty.e2e;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class e2ETestsPrzesylka extends BaseE2ETest {

    @Test
    @DisplayName("list of parcels should be visible to admin")
    void testPrzesylkiListAccessible() {
        loginAsAdmin();

        try (BrowserContext authContext = createAuthenticatedContext()) {
            Page przesylkiPage = authContext.newPage();
            navigateTo(przesylkiPage, "/przesylki");

            waitForVisible(przesylkiPage, "text=Lista Przesyłek");

            assertVisible(przesylkiPage, "text=Lista Przesyłek",
                    "should be seen 'Lista Przesyłek'");
        }
    }


    @Test
    @DisplayName("Creation of new parcel should work correctly")
    void testCreateNewPrzesylka() {
        loginAsAdmin();


        String uniqueDescription = "Testowa przesylka " + Instant.now().toEpochMilli();
        String gabaryt = "B";
        String waga = "3";
        String kodOdbioru = "6661";

        try (BrowserContext authContext = createAuthenticatedContext()) {
            Page przesylkiPage = authContext.newPage();
            navigateTo(przesylkiPage, "/przesylki");

            waitForVisible(przesylkiPage, "text=Lista Przesyłek");


            Locator createButton = przesylkiPage.getByRole(AriaRole.LINK,
                    new Page.GetByRoleOptions().setName("Dodaj nową przesyłkę"));
            createButton.click();


            waitForVisible(przesylkiPage, "#opis");

            przesylkiPage.fill("#opis", uniqueDescription);
            przesylkiPage.selectOption("#gabaryt", gabaryt);
            przesylkiPage.fill("#waga", waga);
            przesylkiPage.fill("#kodOdbioru", kodOdbioru);
            przesylkiPage.click("button[type='submit']");


            waitForVisible(przesylkiPage, "text=" + uniqueDescription);

            assertVisible(przesylkiPage, "text=" + uniqueDescription,
                    "Creation of new parcel should be visible in the list" );
        }
    }


    @Test
    @DisplayName("From for creating parcel should have all fields")
    void testCreatePrzesylkaFormHasAllFields() {
        loginAsAdmin();

        try (BrowserContext authContext = createAuthenticatedContext()) {
            Page formPage = authContext.newPage();
            navigateTo(formPage, "/przesylki");

            waitForVisible(formPage, "text=Lista Przesyłek");


            Locator createButton = formPage.getByRole(AriaRole.LINK,
                    new Page.GetByRoleOptions().setName("Dodaj nową przesyłkę"));
            createButton.click();


            waitForVisible(formPage, "#opis");

            assertTrue(formPage.isVisible("#opis"), " 'opis' should be visible");
            assertTrue(formPage.isVisible("#gabaryt"), "'gabaryt' should be visible");
            assertTrue(formPage.isVisible("#waga"), " 'waga' should be visible");
            assertTrue(formPage.isVisible("#kodOdbioru"), " 'kodOdbioru' should be visible");
            assertTrue(formPage.isVisible("button[type='submit']"), "button submit should be visible" );
        }
    }


    @Test
    @DisplayName("Option for taking different gabaryt should work correctly")
    void testDifferentGabarytOptions() {
        loginAsAdmin();

        try (BrowserContext authContext = createAuthenticatedContext()) {
            Page formPage = authContext.newPage();
            navigateTo(formPage, "/przesylki");

            waitForVisible(formPage, "text=Lista Przesyłek");

            Locator createButton = formPage.getByRole(AriaRole.LINK,
                    new Page.GetByRoleOptions().setName("Dodaj nową przesyłkę"));
            createButton.click();

            waitForVisible(formPage, "#gabaryt");


            Locator gabarytSelect = formPage.locator("#gabaryt");


            gabarytSelect.selectOption("A");
            assertEquals("A", gabarytSelect.inputValue(), "should have been chosen size A");

            gabarytSelect.selectOption("B");
            assertEquals("B", gabarytSelect.inputValue(), "should have been chosen size B");

            gabarytSelect.selectOption("C");
            assertEquals("C", gabarytSelect.inputValue(), "should have been chosen size C");
        }
    }
}
