package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CartItemPage {
    private Page page;
    private Locator qtyField;
    private Locator productImage;
    private Locator productName;
    private Locator unitPrice;
    private Locator colorAttribute;
    private Locator sizeAttribute;

    public CartItemPage(Page page) {
        this.page = page;
        this.qtyField = page.locator("input[title=\"Qty\"]");
        this.productImage = page.locator("tr.odd > td.product-cart-image > a.product-image > img");
        this.productName = page.locator("td.product-cart-info > h2.product-name > a");
        this.unitPrice = page.locator("td.product-cart-price > span.cart-price > span.price");
        this.colorAttribute = page.locator("td.product-cart-info > dl.item-options > dd:first-of-type");
        this.sizeAttribute = page.locator("td.product-cart-info > dl.item-options > dd:last-of-type");
    }

    public void navigate() {
        this.page.navigate("http://qa3magento.dev.evozon.com/checkout/cart/");
    }

    public int getQuantityValue() {
        return Integer.parseInt(this.qtyField.inputValue());
    } 

    public boolean isImageExistent() {
        return this.productImage.count() == 1;
    }

    public String getProductName() {
        return this.productName.innerText();
    }

    public String getUnitPrice() {
        return this.unitPrice.innerText();
    }

    public String getColorAttribute() {
        return this.colorAttribute.innerText();
    }

    public String getSizeAttribute() {
        return this.sizeAttribute.innerText();
    }
}
