package pl.pwr.paczkomaty.e2e;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.options.WaitUntilState;
import org.junit.jupiter.api.*;
import java.nio.file.Paths;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class E2ETestsSortownia {
    static Playwright playwright;
    static Browser browser;

    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.firefox().launch(
                new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(100)
        );
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1280, 720)
                .setIgnoreHTTPSErrors(true)
        );
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    void TestAdvancedBrowsermanagment() {
        page.navigate("http://localhost:8080/login",
                new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
        );

        page.waitForSelector("#login", new Page.WaitForSelectorOptions()
        .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(7000));


        page.fill("#login", "admin");
        page.fill("#haslo", "1111");
        page.click("button[type='submit']");

        //koniec logowania
        page.waitForSelector(
                "text=Witaj w systemie zarządzania siecią paczkomatów",
                new Page.WaitForSelectorOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(7000)
        );

        context.storageState(new BrowserContext.StorageStateOptions()
                .setPath(Paths.get("storageState.json")));


      page.waitForSelector("#username", new Page.WaitForSelectorOptions()
              .setState(WaitForSelectorState.VISIBLE)
              .setTimeout(7000));

        assertTrue(page.textContent("#username").contains("admin"),
                "Login value should contain 'admin'");


        Page page2 = context.newPage();
        page2.navigate("http://localhost:8080/",
                new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
        );

        page2.waitForSelector("text=Witaj w systemie zarządzania siecią paczkomatów", new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(7000));


        assertTrue(page2.isVisible("text=System Zarządzania Siecią Paczkomatów"),
                "Dashboard should contain 'System Zarządzania Siecią Paczkomatów'");


        BrowserContext context2 = browser.newContext(new Browser.NewContextOptions()
                .setStorageStatePath(Paths.get("storageState.json"))
                .setIgnoreHTTPSErrors(true)
        );

        Page page3 = context2.newPage();


        page3.navigate("http://localhost:8080/sortownie",
                new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
        );



        page3.waitForSelector("text=Lista Sortowni", new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(7000));

        assertTrue(page3.isVisible("text=Lista Sortowni"),
                "Should display 'Lista Sortowni' message");


        Locator createSortownieButton = page3.getByRole(AriaRole.LINK,
                new Page.GetByRoleOptions().setName("Dodaj nową sortownię"));
        createSortownieButton.click();

        page3.waitForSelector("#nazwa", new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(7000));

        page3.fill("#nazwa", "Testowa Sortownia");
        page3.fill("#adres", "Testowy Adres 123");
        page3.click("button[type='submit']");


        assertTrue(page3.isVisible("text=Testowa Sortownia"),
                "Should display newly created sortownia Testowa Sortownia");




context.close();
context2.close();




    }
}
