package pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginFormPage extends BasePage {
    @FindBy(id = "username")
    private WebElement usernameInputField;

    @FindBy(id = "password")
    private WebElement passwordInputField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(className = "alert")
    private WebElement resultMessage;

    public LoginFormPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter username")
    public LoginFormPage enterUsername(String username) {
        usernameInputField.sendKeys(username);
        return this;
    }

    @Step("Enter password")
    public LoginFormPage enterPassword(String password) {
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
        return resultMessage.getText();
    }
}
