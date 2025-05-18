package tests;

import constants.Constants;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;

class LoadingImagesTests extends BaseTest {
    static Stream<String> testData() {
        return Stream.of("compass", "calendar", "award", "landscape");
    }

    @ParameterizedTest
    @MethodSource("testData")
    void loadingImagesTest(String imageName) {
        getDriver().findElement(By.xpath(Constants.LOADING_IMAGES_PAGE_PATH)).click();

        WebElement pageImage = wait10.until(ExpectedConditions.presenceOfElementLocated(By.id(imageName)));

        assertThat(pageImage.getDomAttribute("src")).containsIgnoringCase(imageName);
    }
}
