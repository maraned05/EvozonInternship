import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.automation.pages.LoginPage;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class TestLoginFunctionality {
    static Playwright playwright;
    static Browser browser;
    Page page;
    LoginPage loginPage;

    @BeforeAll
    static void setupClass() {
        playwright = Playwright.create();
        browser = playwright.firefox().launch();
    }

    @BeforeEach 
    void setupTest() {
        BrowserContext context = browser.newContext();
        page = context.newPage();
        this.loginPage = new LoginPage(page);
    }

    @Test
    void testWithValidCredentials() {
        this.loginPage.navigate();
        this.loginPage.login("gigi@gmail.com", "bunaziua");
        assertThat(page).hasTitle("My Account");
    }

    @Test
    void testOnlyEmail() {
        this.loginPage.navigate();
        this.loginPage.login("gigi@gmail.com", "");
        assertThat(page).hasTitle("Customer Login");
    }

    @Test
    void testOnlyPassword() {
        this.loginPage.navigate();
        this.loginPage.login("", "bunaziua");
        assertThat(page).hasTitle("Customer Login");
        assertTrue(this.loginPage.isAdviceMessageVisible());
    }

    @Test
    void testInvalidEmailAndPassword() {
        this.loginPage.navigate();
        this.loginPage.login("dasdsad", "fdsfsd");
        assertThat(page).hasTitle("Customer Login");
    }


    @AfterAll 
    static void tearDown() {
        browser.close();
        playwright.close();
    }
}
