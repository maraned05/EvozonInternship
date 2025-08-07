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

public class TestCartFunctionalities {
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
    void testAddSimpleItem() {
        // Madison LX2200

        // Add a simple item to cart
        page.navigate("http://qa3magento.dev.evozon.com/");
        Locator homeSection = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Home & Decor").setExact(true));
        homeSection.hover();
        Locator electronicsSection = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Electronics").setExact(true));
        electronicsSection.click();
        Locator addToCartButton = page.locator(".products-grid > li:nth-child(1) > div.product-info > div.actions > button.btn-cart");
        addToCartButton.click();

        // Check if the user has been redirected to the cart page
        assertThat(page).hasURL("http://qa3magento.dev.evozon.com/checkout/cart/");

        // Check if there is only one item in the cart
        Locator productRow = page.locator("table#shopping-cart-table > tbody > tr.odd");
        assertThat(productRow).hasCount(1);
        Locator unexistentRow = page.locator("table#shopping-cart-table > tbody > tr.even");
        assertThat(unexistentRow).hasCount(0);

        // Check if the quantity field displays the right value
        Locator qtyFieldCart = page.locator("input[title=\"Qty\"]");
        assertThat(qtyFieldCart).hasValue("1");

        // Check if the photo is there
        Locator productImage = page.locator("tr.odd > td.product-cart-image > a.product-image > img");
        assertThat(productImage).isVisible();
        assertThat(productImage).hasAttribute("alt", "Madison LX2200");

        // Check the name of the product
        Locator productName = page.locator("td.product-cart-info > h2.product-name > a");
        assertThat(productName).hasText("Madison LX2200");

        // Check the unit price
        Locator unitPrice = page.locator("td.product-cart-price > span.cart-price > span.price");
        assertThat(unitPrice).hasText("$425.00");
    }

    @Test
    void testAddConfigurableItem() {
        // Tori Tank, Indigo, Size M, Quantity 2, price 60

        // Add a configurable item to cart
        page.navigate("http://qa3magento.dev.evozon.com/");
        Locator womenSection = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Women").setExact(true));
        womenSection.hover();
        Locator newArrivalsSection = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("New Arrivals").setExact(true));
        newArrivalsSection.click();
        Locator viewDetailsButton = page.locator(".products-grid > li:nth-child(1) > div.product-info > div.actions > a.button");
        viewDetailsButton.click();
        Locator colorButton = page.getByAltText("Indigo");
        colorButton.click();
        Locator sizeButton = page.locator("ul#configurable_swatch_size > li.option-m > a#swatch79");
        sizeButton.click();
        Locator qtyField = page.locator("input#qty");
        qtyField.fill("2");
        Locator addButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to Cart"));
        addButton.click();

        // Check if the user has been redirected to the cart page
        assertThat(page).hasURL("http://qa3magento.dev.evozon.com/checkout/cart/");

        // Check if there is only one item in the cart
        Locator productRow = page.locator("table#shopping-cart-table > tbody > tr.odd");
        assertThat(productRow).hasCount(1);
        Locator unexistentRow = page.locator("table#shopping-cart-table > tbody > tr.even");
        assertThat(unexistentRow).hasCount(0);

        // Check if the quantity field displays the right value
        Locator qtyFieldCart = page.locator("input[title=\"Qty\"]");
        assertThat(qtyFieldCart).hasValue("2");

        // Check if the photo is there
        Locator productImage = page.locator("tr.odd > td.product-cart-image > a.product-image > img");
        assertThat(productImage).isVisible();
        assertThat(productImage).hasAttribute("alt", "Tori Tank");

        // Check the name of the product
        Locator productName = page.locator("td.product-cart-info > h2.product-name > a");
        assertThat(productName).hasText("Tori Tank");

        // Check the attributes
        Locator colorAttribute = page.locator("td.product-cart-info > dl.item-options > dd:first-of-type");
        assertThat(colorAttribute).hasText("Indigo");
        Locator sizeAttribute = page.locator("td.product-cart-info > dl.item-options > dd:last-of-type");
        assertThat(sizeAttribute).hasText("M");

        // Check the unit price
        Locator unitPrice = page.locator("td.product-cart-price > span.cart-price > span.price");
        assertThat(unitPrice).hasText("$60.00");
    }

    @Test
    void testRemoveFromCart() {
        // Add two products to cart: Houston Travel Wallet and Broad St. Flapover Briefcase
        page.navigate("http://qa3magento.dev.evozon.com/");
        Locator accessoriesSection = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Accessories").setExact(true));
        accessoriesSection.hover();
        Locator bagsSection = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Bags & Luggage").setExact(true));
        bagsSection.click();
        Locator addToCart1 = page.locator("ul.products-grid > li.item:nth-child(4) > div.product-info > div.actions > button.btn-cart");
        addToCart1.click();
        page.navigate("http://qa3magento.dev.evozon.com/accessories/bags-luggage.html");
        Locator addToCart2 = page.locator("ul.products-grid > li.item:nth-child(3) > div.product-info > div.actions > button.btn-cart");
        addToCart2.click();

        // Remove the first added product
        Locator deleteButton = page.locator("table#shopping-cart-table > tbody > tr.first.odd > td.product-cart-remove > a.btn-remove");
        deleteButton.click();

        // Check if there's only one product left in the cart
        Locator productRow = page.locator("table#shopping-cart-table > tbody > tr");
        assertThat(productRow).hasCount(1);

        // Check if the product that wasn't deleted is still in the cart
        Locator productName = page.locator("table#shopping-cart-table > tbody > tr > td.product-cart-info > h2.product-name > a");
        assertThat(productName).hasText("Broad St. Flapover Briefcase");
    }   

    @AfterAll 
    static void tearDown() {
        browser.close();
        playwright.close();
    }
}
