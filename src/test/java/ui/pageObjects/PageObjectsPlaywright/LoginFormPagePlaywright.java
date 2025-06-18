package ui.pageObjects.PageObjectsPlaywright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginFormPagePlaywright {
    private final Locator usernameInputField;
    private final Locator passwordInputField;
    private final Locator submitButton;
    private final Locator resultMessage;

    public LoginFormPagePlaywright(Page page) {
        this.usernameInputField = page.locator("#username");
        this.passwordInputField = page.locator("#password");
        this.submitButton = page.locator("xpath=//button[@type='submit']");
        this.resultMessage = page.locator(".alert");
    }

    public LoginFormPagePlaywright enterUsername(String username) {
        usernameInputField.fill(username);
        return this;
    }

    public LoginFormPagePlaywright enterPassword(String password) {
        passwordInputField.fill(password);
        return this;
    }

    public LoginFormPagePlaywright clickSubmit() {
        submitButton.click();
        return this;
    }

    public String getResultMessage() {
        return resultMessage.innerText();
    }
}
