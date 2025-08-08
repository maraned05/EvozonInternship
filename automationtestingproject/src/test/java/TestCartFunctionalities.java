import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.automation.pages.CartItemPage;
import com.automation.pages.ProductDetailsPage;
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
    ProductDetailsPage productPage;
    CartItemPage cartProduct;

    @BeforeAll
    static void setupClass() {
        playwright = Playwright.create();
        browser = playwright.firefox().launch();
    }

    @BeforeEach 
    void setupTest() {
        BrowserContext context = browser.newContext();
        page = context.newPage();
        this.productPage = new ProductDetailsPage(page);
        this.cartProduct = new CartItemPage(page);
    }

    @Test
    void testAddSimpleItem() {
        // Madison LX2200

        // Add a simple item to cart
        this.productPage.navigate("http://qa3magento.dev.evozon.com/home-decor/electronics/madison-lx2200.html");
        this.productPage.clickAddToCartButton();

        // Check if the user has been redirected to the cart page
        assertThat(page).hasURL("http://qa3magento.dev.evozon.com/checkout/cart/");

        // Check if the quantity field displays the right value
        assertEquals(this.cartProduct.getQuantityValue(), 1);

        // Check if the photo is there
        assertTrue(this.cartProduct.isImageExistent());

        // Check the name of the product
        assertEquals(this.cartProduct.getProductName(), "MADISON LX2200");

        // Check the unit price
        assertEquals(this.cartProduct.getUnitPrice(), "$425.00");
    }

    @Test
    void testAddConfigurableItem() {
        // Tori Tank, Indigo, Size M, Quantity 2, price 60

        // Add a configurable item to cart
        this.productPage.navigate("http://qa3magento.dev.evozon.com/women/new-arrivals/tori-tank-466.html");
        this.productPage.clickColorButton();
        this.productPage.clickSizeButton();
        this.productPage.fillInQuantity(2);
        this.productPage.clickAddToCartButton();

        // Check if the user has been redirected to the cart page
        assertThat(page).hasURL("http://qa3magento.dev.evozon.com/checkout/cart/");

        // Check if the quantity field displays the right value
        assertEquals(this.cartProduct.getQuantityValue(), 2);

        // Check if the photo is there
        assertTrue(this.cartProduct.isImageExistent());

        // Check the name of the product
        assertEquals(this.cartProduct.getProductName(), "TORI TANK");

        // Check the attributes
        assertEquals(this.cartProduct.getColorAttribute(), "Indigo");
        assertEquals(this.cartProduct.getSizeAttribute(), "M");

        // Check the unit price
        assertEquals(this.cartProduct.getUnitPrice(), "$60.00");
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
