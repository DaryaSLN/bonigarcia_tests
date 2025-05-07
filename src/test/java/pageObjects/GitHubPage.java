package pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GitHubPage {
    private final WebDriver driver;

    @FindBy(linkText = "bonigarcia")
    private WebElement gitHubNameLink;

    public GitHubPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebDriver getDriver() {
        return driver;
    }

    @Step("Get GitHub name")
    public String getGitHubName() {
        return gitHubNameLink.getText();
    }
}
