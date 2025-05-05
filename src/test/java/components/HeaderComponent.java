package components;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageObjects.GitHubPage;

public class HeaderComponent {
    private WebDriver driver;

    @FindBy(className = "display-4")
    private WebElement title;

    @FindBy(tagName = "h5")
    private WebElement description;

    @FindBy(xpath = "//img")
    private WebElement logoImage;

    @FindBy(xpath = "//a[@href='https://github.com/bonigarcia/selenium-webdriver-java']")
    private WebElement logoLink;

    public HeaderComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getHeaderTitleText() {
        return title.getText();
    }

    public String getHeaderDescriptionText() {
        return description.getText();
    }

    public int getLogoWidth() {
        return logoImage.getSize().getWidth();
    }

    public int getLogoHeight() {
        return logoImage.getSize().getHeight();
    }

    public GitHubPage clickLogoLink() {
        logoLink.click();
        return new GitHubPage(driver);
    }
}
