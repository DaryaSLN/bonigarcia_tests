package ui.tests;

import com.codeborne.selenide.Configuration;
import ui.config.TestPropertiesConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

abstract class BaseSelenideTest {
    TestPropertiesConfig testConfig = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    @BeforeEach
    void setup() {
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");

        if (remoteUrl != null) {
            Configuration.remote = remoteUrl;

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.setCapability("goog:loggingPrefs", Map.of("browser", "ALL"));

            Configuration.browserCapabilities = options;
        }

        open(testConfig.getBaseUrl());
        getWebDriver().manage().window().maximize();
    }
}
