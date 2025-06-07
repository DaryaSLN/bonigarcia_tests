package tests;

import config.TestPropertiesConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

abstract class BaseSelenideTest {
    TestPropertiesConfig testConfig = ConfigFactory.create(TestPropertiesConfig.class, System.getProperties());

    @BeforeEach
    void setup() {
        open(testConfig.getBaseUrl());
        getWebDriver().manage().window().maximize();
    }
}
