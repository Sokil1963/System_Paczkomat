package pl.pwr.paczkomaty.e2e;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.options.WaitUntilState;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;


public abstract class BaseE2ETest {

    protected static final String BASE_URL = "http://localhost:8080";
    protected static final String ADMIN_LOGIN = "admin";
    protected static final String ADMIN_PASSWORD = "1111";
    protected static final int DEFAULT_TIMEOUT = 7000;
    protected static final String STORAGE_STATE_PATH = "storageState.json";

    protected static Playwright playwright;
    protected static Browser browser;

    protected BrowserContext context;
    protected Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.firefox().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(350)
        );
    }

    @AfterAll
    static void closeBrowser() {
        if (playwright != null) {
            playwright.close();
        }
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
        if (context != null) {
            context.close();
        }
    }


    protected void loginAsAdmin() {
        page.navigate(BASE_URL + "/login",
                new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
        );

        waitForElement(page, "#login");

        page.fill("#login", ADMIN_LOGIN);
        page.fill("#haslo", ADMIN_PASSWORD);
        page.click("button[type='submit']");

        waitForVisible(page, "text=Witaj w systemie zarządzania siecią paczkomatów");

        context.storageState(new BrowserContext.StorageStateOptions()
                .setPath(Paths.get(STORAGE_STATE_PATH)));

    }


    protected BrowserContext createAuthenticatedContext() {
        return browser.newContext(new Browser.NewContextOptions()
                .setStorageStatePath(Paths.get(STORAGE_STATE_PATH))
                .setIgnoreHTTPSErrors(true)
        );
    }


    protected void waitForElement(Page page, String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(DEFAULT_TIMEOUT));
    }


    protected void waitForVisible(Page page, String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(DEFAULT_TIMEOUT));
    }


    protected void assertVisible(Page page, String selector, String message) {
        assertTrue(page.isVisible(selector), message);
    }


    protected void navigateTo(Page page, String path) {
        page.navigate(BASE_URL + path,
                new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
        );
    }
}

