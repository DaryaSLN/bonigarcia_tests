package tests;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pageObjects.PageObjectsSelenide.HomePageSelenide;
import steps.AllureSteps;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;

class LoadingImagesSelenideTests extends BaseSelenideTest {
    AllureSteps allureSteps = new AllureSteps();

    static Stream<String> testData() {
        return Stream.of("compass", "calendar", "award", "landscape");
    }

    @ParameterizedTest
    @MethodSource("testData")
    void loadingImagesTest(String imageName) throws IOException {
        SelenideElement pageImage = new HomePageSelenide()
                .navigateToLoadingImagesPage()
                .getImageByImageName(imageName);

        allureSteps.captureScreenshotSelenide();
        allureSteps.captureScreenshotSelenideSpoiler();

        assertThat(pageImage.getDomAttribute("src")).containsIgnoringCase(imageName);
    }
}
