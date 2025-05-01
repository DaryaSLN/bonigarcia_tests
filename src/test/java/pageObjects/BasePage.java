package pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {
    private final WebDriver driver;

    @FindBy(className = "display-6")
    private WebElement pageTitle;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebDriver getDriver() {
        return driver;
    }

    @Step("Get current URL")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("Get page title")
    public String getPageTitle() {
        return pageTitle.getText();
    }
}
