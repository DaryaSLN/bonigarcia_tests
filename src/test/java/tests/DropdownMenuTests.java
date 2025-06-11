package tests;

import constants.Constants;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;
import pageObjects.DropdownMenuPage;
import pageObjects.HomePage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("POM")
class DropdownMenuTests extends BaseTest {
    final List<String> expectedDropdownMenuOptions = List.of("Action", "Another action", "Something else here", "Separated link");

    @Test
    void leftClickTest() {
        List<String> actualDropdownMenuOptions = new HomePage(getDriver())
                .navigateToPage(Constants.DROPDOWN_MENU_PAGE_LINKTEXT, DropdownMenuPage.class)
                .openLeftClickDropdown()
                .getLeftClickDropdownMenuOptions();

        assertEquals(expectedDropdownMenuOptions, actualDropdownMenuOptions);
    }

    @Test
    void rightClickTest() {
        List<String> actualDropdownMenuOptions = new HomePage(getDriver())
                .navigateToPage(Constants.DROPDOWN_MENU_PAGE_LINKTEXT, DropdownMenuPage.class)
                .openRightClickDropdown()
                .getRightClickDropdownMenuOptions();

        assertEquals(expectedDropdownMenuOptions, actualDropdownMenuOptions);
    }

    @Test
    void doubleClickTest() {
        List<String> actualDropdownMenuOptions = new HomePage(getDriver())
                .navigateToPage(Constants.DROPDOWN_MENU_PAGE_LINKTEXT, DropdownMenuPage.class)
                .openDoubleClickDropdown()
                .getDoubleClickDropdownMenuOptions();

        assertEquals(expectedDropdownMenuOptions, actualDropdownMenuOptions);
    }
}
