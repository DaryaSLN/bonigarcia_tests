package tests;

import constants.Constants;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Point;
import pageObjects.DragAndDropPage;
import pageObjects.HomePage;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DragAndDropTests extends BaseTest {
    @Test
    void dragAndDropTest() {
        DragAndDropPage dragAndDropPage = new HomePage(getDriver())
                .navigateToPage(Constants.DRAG_AND_DROP_PAGE_PATH, DragAndDropPage.class);

        Point initialElementLocation = dragAndDropPage.getDraggableLocation();

        dragAndDropPage.dragAndDropElement();

        Point finalElementLocation = dragAndDropPage.getDraggableLocation();
        Point targetElementLocation = dragAndDropPage.getTargetLocation();

        assertAll(
                () -> assertEquals(finalElementLocation, targetElementLocation),
                () -> assertNotEquals(initialElementLocation, finalElementLocation)
        );
    }
}
