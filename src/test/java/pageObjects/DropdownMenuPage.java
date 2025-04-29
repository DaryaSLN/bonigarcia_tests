package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class DropdownMenuPage extends BasePage {
    WebElement leftClickDropDownMenu = getDriver().findElement(By.id("my-dropdown-1"));
    WebElement rightClickDropdownMenu = getDriver().findElement(By.id("my-dropdown-2"));
    WebElement doubleClickDropdownMenu = getDriver().findElement(By.id("my-dropdown-3"));
    By leftClickDropdownMenuOptions = By.xpath("//ul[@class='dropdown-menu show']/li/a[@class='dropdown-item']");
    List<WebElement> rightClickDropdownMenuOptions = getDriver().findElements(By.xpath("//ul[@id='context-menu-2']/li"));
    List<WebElement> doubleClickDropdownMenuOptions = getDriver().findElements(By.xpath("//ul[@id='context-menu-3']/li"));

    public DropdownMenuPage(WebDriver driver) {
        super(driver);
    }

    public DropdownMenuPage openLeftClickDropdown() {
        leftClickDropDownMenu.click();
        return this;
    }

    public DropdownMenuPage openRightClickDropdown() {
        new Actions(getDriver())
                .contextClick(rightClickDropdownMenu)
                .perform();
        return this;
    }

    public DropdownMenuPage openDoubleClickDropdown() {
        new Actions(getDriver())
                .doubleClick(doubleClickDropdownMenu)
                .perform();
        return this;
    }

    public List<String> getLeftClickDropdownMenuOptions() {
        List<WebElement> options = getDriver().findElements(leftClickDropdownMenuOptions);
        return getOptionsList(options);
    }

    public List<String> getRightClickDropdownMenuOptions() {
        return getOptionsList(rightClickDropdownMenuOptions);
    }

    public List<String> getDoubleClickDropdownMenuOptions() {
        return getOptionsList(doubleClickDropdownMenuOptions);
    }

    private List<String> getOptionsList(List<WebElement> dropdownMenuOptions) {
        return dropdownMenuOptions
                .stream().map(WebElement::getText)
                .filter(text -> !text.isBlank()).toList();
    }
}
