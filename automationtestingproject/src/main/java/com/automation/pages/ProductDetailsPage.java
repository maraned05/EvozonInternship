package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

// I'm aware that this is not the ideal structure

public class ProductDetailsPage {
    private Page page;
    private Locator colorButton;
    private Locator sizeButton;
    private Locator qtyField;
    private Locator addButton;

    public ProductDetailsPage(Page page) {
        this.page = page;
        this.colorButton = page.getByAltText("Indigo");
        this.sizeButton = page.locator("ul#configurable_swatch_size > li.option-m > a#swatch79");
        this.qtyField = page.locator("input#qty");
        this.addButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to Cart"));
    }

    public void navigate(String pageURL) {
        page.navigate(pageURL);
    }

    public void clickColorButton() {
        this.colorButton.click();
    }

    public void clickSizeButton() {
        this.sizeButton.click();
    }

    public void fillInQuantity(int value) {
        this.qtyField.fill(String.valueOf(value));
    }

    public void clickAddToCartButton() {
        this.addButton.click();
    }
}
