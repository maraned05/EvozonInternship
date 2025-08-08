package com.automation.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.Locator;

public class LoginPage {
    private Page page;
    private Locator emailField; 
    private Locator passwordField;
    private Locator loginButton;

    public LoginPage(Page page) {
        this.page = page;
        this.emailField = page.locator("input#email");
        this.passwordField = page.locator("input#pass");
        this.loginButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
    }

    public void navigate() {
        page.navigate("http://qa3magento.dev.evozon.com/customer/account/login/");
    }

    public void login(String email, String password) {
        this.emailField.fill(email);
        this.passwordField.fill(password);
        this.loginButton.click();
    }

    public boolean isAdviceMessageVisible() {
        Locator errorMessage = page.locator("div#advice-required-entry-email");
        return errorMessage.count() == 1;
    }
}
