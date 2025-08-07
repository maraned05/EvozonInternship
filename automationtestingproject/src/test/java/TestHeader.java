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

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.regex.Pattern;

public class TestHeader {
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
    void testLogo() {
        page.navigate("http://qa3magento.dev.evozon.com/");
        Locator logoLink = page.locator("div.page-header-container > a.logo");
        assertThat(logoLink).isVisible();
        assertThat(logoLink).hasAttribute("href", "http://qa3magento.dev.evozon.com/");
        Locator logoImage = page.locator("div.page-header-container > a.logo > img").and(page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setIncludeHidden(false)));
        assertThat(logoImage).hasCount(1);
        assertThat(logoImage).hasAttribute("alt", "Madison Island");
        assertThat(logoImage).hasAttribute("src", Pattern.compile("^http://qa3magento.dev.evozon.com/skin/frontend/rwd/default/images/media/.+"));
    }

    @Test
    void testAccountButton() {
        page.navigate("http://qa3magento.dev.evozon.com/");
        Locator accountButton = page.locator("div.account-cart-wrapper > a.skip-account");
        assertThat(accountButton).containsText(Pattern.compile("Account"));
        accountButton.click();
        assertThat(accountButton).containsClass("skip-active");
        Locator accountDetails = page.locator("div#header-account");
        assertThat(accountDetails).isVisible();
    }

    @Test
    void testCartButton() {
        page.navigate("http://qa3magento.dev.evozon.com/");
        Locator cartButton = page.locator("div.account-cart-wrapper > div.header-minicart > a.skip-cart");
        assertThat(cartButton).containsText(Pattern.compile("Cart"));
        cartButton.click();
        assertThat(cartButton).containsClass("skip-active");
        Locator cartDetails = page.locator("div#header-cart");
        assertThat(cartDetails).isVisible();
    }

    @Test
    void testSearchBar() {
        page.navigate("http://qa3magento.dev.evozon.com/");
        Locator searchField = page.getByPlaceholder("Search entire store here...");
        assertThat(searchField).isEmpty();
        assertThat(searchField).isEditable();
        searchField.fill("shirt");
        Locator searchButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search"));
        assertThat(searchButton).isEnabled();
        searchButton.click();
        assertThat(page).hasTitle("Search results for: 'shirt'");
    }

    @Test
    void testNavigationBar() {
        page.navigate("http://qa3magento.dev.evozon.com/");
        Locator navigationHeadline = page.locator("#nav > ol.nav-primary > li.level0 > a");
        for (Locator headline : navigationHeadline.all()) {
            assertThat(headline).isVisible();
            if (headline.getAttribute("class").contains("has-children")) {
                headline.hover();
                Locator details = page.locator("ul.level0").and(page.getByRole(AriaRole.LIST, new Page.GetByRoleOptions().setIncludeHidden(false)));
                assertThat(details).hasCount(1);
            }
            headline.click();
            assertThat(page).hasTitle(headline.textContent());
            page.navigate("http://qa3magento.dev.evozon.com/");
        }
    }

    @AfterAll 
    static void tearDown() {
        browser.close();
        playwright.close();
    }
}
