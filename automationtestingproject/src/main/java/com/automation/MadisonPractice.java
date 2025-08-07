package com.automation;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

// mvn compile exec:java -D exec.mainClass="com.automation.PlaywrightTest"

public class MadisonPractice {
    public static void main(String[] args) {
        // testHomepage();
        // testAccount();
        // testSearch();
        // testProductsList();
        // testNavigation();
        // testAddProduct();
        testRemoveProduct();
    }

    public static void testHomepage() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.firefox().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
            );
            Page page = browser.newPage();
            // 1. Go to Madison site
            page.navigate("http://qa3magento.dev.evozon.com/");
            // 2. Get title
            System.out.println("Page title: " + page.title());
            // 3. Get current URL
            System.out.println("Page URL: " + page.url());
            // 4. Click on the logo
            Locator madisonLogo = page.locator("div.page-header-container > a.logo");
            madisonLogo.click();
            // 5. Navigate to a different page 
            page.navigate("http://qa3magento.dev.evozon.com/women.html");
            // 6. Navigate back
            page.goBack();
            // 7. Navigate forward
            page.goForward();
            // 8. Refresh the page
            page.reload();
            // 9. Quit the browser
            browser.close();
            return;
        }
    }

    public static void testAccount() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.firefox().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
            );
            Page page = browser.newPage();
            // 1. Go to Madison site
            page.navigate("http://qa3magento.dev.evozon.com/");
            // 2. Click on the account link from the header
            Locator accountLink = page.locator("div.account-cart-wrapper > a.skip-account");
            accountLink.click();
            // 3. Quit browser
            browser.close();
            return;
        }
    }

    public static void testSearch() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.firefox().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
            );
            Page page = browser.newPage();
            // 1. Go to Madison site
            page.navigate("http://qa3magento.dev.evozon.com/");
            // 2. Clear search field
            Locator searchField = page.getByPlaceholder("Search entire store here...");
            searchField.clear();
            // 3. Enter "woman" text to field
            searchField.fill("woman");
            // 4. Submit search
            Locator searchButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search"));
            searchButton.click();
            // 5. Quit browser
            browser.close();
            return;
        }
    }

    public static void testProductsList() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.firefox().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
            );
            Page page = browser.newPage();
            // 1. Go to Madison site
            page.navigate("http://qa3magento.dev.evozon.com/");
            // 2. List the number of new products
            Locator newProduct = page.locator("div.widget-new-products div.widget-products li.item.last");
            System.out.println(newProduct.count());
            // 3. List all products from the new products list
            for (Locator product : newProduct.all()) {
                System.out.println(product.innerText());
            }
            // 4. Quit browser
            browser.close();
            return;
        }
    }

    public static void testNavigation() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.firefox().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
            );
            Page page = browser.newPage();
            // 1. Go to Madison site
            page.navigate("http://qa3magento.dev.evozon.com/");
            // 2. Get the navigation headlines
            Locator navigationHeadline = page.locator("#nav > ol.nav-primary > li.level0");
            for (Locator headline : navigationHeadline.all()) {
                System.out.println(headline.innerText());
            }
            // 3. Go to a specified item "Sale"
            Locator saleHeadline = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sale").setExact(true));
            // 4. Click on it
            saleHeadline.click();
            // 5. Quit browser
            browser.close();
            return;
        }
    }

    public static void testAddProduct() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.firefox().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
            );
            Page page = browser.newPage();
            // 1. Navigate to the homepage
            page.navigate("http://qa3magento.dev.evozon.com/");
            // 2. Select any category
            Locator womenSection = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Women").setExact(true));
            womenSection.hover();
            Locator newArrivalsSection = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("New Arrivals").setExact(true));
            newArrivalsSection.click();
            // 3. Select a product
            Locator viewDetailsButton = page.locator(".products-grid > li:nth-child(1) > div.product-info > div.actions > a.button");
            viewDetailsButton.click();
            // 4. Select any of the required options
            Locator colorButton = page.getByAltText("Indigo");
            colorButton.click();
            Locator sizeButton = page.locator("ul#configurable_swatch_size > li.option-m > a#swatch79");
            sizeButton.click();
            // 5. Write in the "Qty" field a value
            Locator qtyField = page.locator("input#qty");
            qtyField.fill("2");
            // 6. Click the "Add to Cart" button
            Locator addButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to Cart"));
            addButton.click();
            // 7. Quit browser
            browser.close();
            return;
        }
    }

    public static void testRemoveProduct() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.firefox().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
            );
            Page page = browser.newPage();
            // 1. Navigate to the homepage
            page.navigate("http://qa3magento.dev.evozon.com/");
            // 2. Select any category
            Locator accessoriesSection = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Accessories").setExact(true));
            accessoriesSection.hover();
            Locator bagsSection = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Bags & Luggage").setExact(true));
            bagsSection.click();
            // 3. Add two products
            Locator addToCart1 = page.locator("ul.products-grid > li.item:nth-child(4) > div.product-info > div.actions > button.btn-cart");
            addToCart1.click();
            page.navigate("http://qa3magento.dev.evozon.com/accessories/bags-luggage.html");
            Locator addToCart2 = page.locator("ul.products-grid > li.item:nth-child(3) > div.product-info > div.actions > button.btn-cart");
            addToCart2.click();
            // 4. Remove a product from the cart
            Locator deleteButton = page.locator("tr.first.odd > td.product-cart-remove > a.btn-remove");
            deleteButton.click();
            // 5. Quit browser
            browser.close();
            return;
        }
    }
}
