package pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Navigate to page")
    public <Page extends BasePage> Page navigateToPage(String linkText, Class<Page> pageClass) {
        getWait2().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.linkText(linkText)))).click();
        try {
            return pageClass.getDeclaredConstructor(WebDriver.class).newInstance(getDriver());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create page instance: " + pageClass.getSimpleName(), e);
        }
    }
}
