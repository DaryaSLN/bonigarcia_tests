package ui.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DropdownMenuPage extends BasePage {
    @FindBy(id = "my-dropdown-1")
    private WebElement leftClickDropDownMenu;

    @FindBy(id = "my-dropdown-2")
    private WebElement rightClickDropdownMenu;

    @FindBy(id = "my-dropdown-3")
    private WebElement doubleClickDropdownMenu;

    @FindBy(xpath = "//ul[@class='dropdown-menu show']/li/a[@class='dropdown-item']")
    private List<WebElement> leftClickDropdownMenuOptions;

    @FindBy(xpath = "//ul[@id='context-menu-2']/li")
    private List<WebElement> rightClickDropdownMenuOptions;

    @FindBy(xpath = "//ul[@id='context-menu-3']/li")
    private List<WebElement> doubleClickDropdownMenuOptions;

    public DropdownMenuPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open the left-click dropdown")
    public DropdownMenuPage openLeftClickDropdown() {
        leftClickDropDownMenu.click();
        return this;
    }

    @Step("Open the right-click dropdown")
    public DropdownMenuPage openRightClickDropdown() {
        new Actions(getDriver())
                .contextClick(rightClickDropdownMenu)
                .perform();
        return this;
    }

    @Step("Open the double-click dropdown")
    public DropdownMenuPage openDoubleClickDropdown() {
        new Actions(getDriver())
                .doubleClick(doubleClickDropdownMenu)
                .perform();
        return this;
    }

    @Step("Get the left-click dropdown menu options")
    public List<String> getLeftClickDropdownMenuOptions() {
        return getOptionsList(leftClickDropdownMenuOptions);
    }

    @Step("Get the right-click dropdown menu options")
    public List<String> getRightClickDropdownMenuOptions() {
        return getOptionsList(rightClickDropdownMenuOptions);
    }

    @Step("Get the double-click dropdown menu options")
    public List<String> getDoubleClickDropdownMenuOptions() {
        return getOptionsList(doubleClickDropdownMenuOptions);
    }

    private List<String> getOptionsList(List<WebElement> dropdownMenuOptions) {
        return dropdownMenuOptions
                .stream().map(WebElement::getText)
                .filter(text -> !text.isBlank()).toList();
    }
}
