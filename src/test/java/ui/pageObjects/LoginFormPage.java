package ui.pageObjects;

import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginFormPage extends BasePage {
    @FindBy(id = "username")
    private WebElement usernameInputField;

    @FindBy(id = "password")
    private WebElement passwordInputField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(className = "alert")
    private WebElement resultMessage;

    @FindBy(id = "success")
    private WebElement successResultMessage;

    public LoginFormPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter username '{username}'")
    public LoginFormPage enterUsername(String username) {
        usernameInputField.sendKeys(username);
        return this;
    }

    @Step("Enter password")
    public LoginFormPage enterPassword(@Param(name = "password", mode = Parameter.Mode.MASKED) String password) {
        passwordInputField.sendKeys(password);
        return this;
    }

    @Step("Click submit button")
    public LoginFormPage clickSubmit() {
        submitButton.click();
        return this;
    }

    @Step("Get the result message")
    public String getResultMessage() {
        return getWait2().until(ExpectedConditions.visibilityOf(resultMessage)).getText();
    }

    @Step("Get success result message")
    public String getSuccessResultMessage() {
        return getWait2().until(ExpectedConditions.visibilityOf(successResultMessage)).getText();
    }
}
