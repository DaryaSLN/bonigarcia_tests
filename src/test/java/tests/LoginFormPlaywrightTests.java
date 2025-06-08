package tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pageObjects.PageObjectsPlaywright.HomePagePlaywright;
import patterns.builder.LoginData;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginFormPlaywrightTests extends BasePlaywrightTest {
    LoginData loginData;

    @Test
    void validCredentialsTest() {
        final String expectedMessage = "Login successful";

        loginData = LoginData.builder()
                .username(testConfig.getUsername())
                .password(testConfig.getPassword())
                .build();

        String actualMessage = new HomePagePlaywright(page)
                .navigateToLoginFormPage()
                .enterUsername(loginData.getUsername())
                .enterPassword(loginData.getPassword())
                .clickSubmit()
                .getResultMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    static Stream<Arguments> invalidCredentials() {
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of("        ", "        "),
                Arguments.of("username", "password")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidCredentials")
    void invalidCredentialsTest(String username, String password) {
        final String expectedMessage = "Invalid credentials";

        loginData = LoginData.builder()
                .username(username)
                .password(password)
                .build();

        String actualMessage = new HomePagePlaywright(page)
                .navigateToLoginFormPage()
                .enterUsername(username)
                .enterPassword(password)
                .clickSubmit()
                .getResultMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
