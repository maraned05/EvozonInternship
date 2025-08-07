import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class TestLoginFunctionality {
    static Playwright playwright;
    static Browser browser;
    Page page;

    @BeforeAll
    static void setupClass() {
        playwright = Playwright.create();
        browser = playwright.firefox().launch();
    }

    @BeforeEach 
    void setupTest() {
        BrowserContext context = browser.newContext();
        page = context.newPage();
    }

    @Test
    void testWithValidInput() {
        page.navigate("http://qa3magento.dev.evozon.com/customer/account/login/");

        Locator emailField = page.locator("input#email");
        assertThat(emailField).isEmpty();
        emailField.fill("gigi@gmail.com");

        Locator passwordField = page.locator("input#pass");
        assertThat(passwordField).isEmpty();
        passwordField.fill("bunaziua");

        Locator loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        assertThat(loginButton).isEnabled();
        loginButton.click();

        assertThat(page).hasTitle("My Account");
    }

    @AfterAll 
    static void tearDown() {
        browser.close();
        playwright.close();
    }
}
