package ui.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    private final WebDriver driver;
    private WebDriverWait wait2;
    JavascriptExecutor js;

    @FindBy(className = "display-6")
    private WebElement pageTitle;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait2() {
        if (wait2 == null) {
            wait2 = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
        }
        return wait2;
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
