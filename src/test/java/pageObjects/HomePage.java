package pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Navigate to page")
    public <Page extends BasePage> Page navigateToPage(String linkText, Class<Page> pageClass) {
        js.executeScript("arguments[0].click();", getDriver().findElement(By.linkText(linkText)));

        try {
            return pageClass.getDeclaredConstructor(WebDriver.class).newInstance(getDriver());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create page instance: " + pageClass.getSimpleName(), e);
        }
    }
}
