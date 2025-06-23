package api.controllers;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class HttpResponse {
    private final ValidatableResponse response;

    public HttpResponse(ValidatableResponse response) {
        this.response = response;
    }

    @Step("Check status code")
    public HttpResponse statusCodeIs(int statusCode) {
        this.response.statusCode(statusCode);
        return this;
    }

    @Step("Check json value by path '{path}' and expected value '{expectedValue}'")
    public HttpResponse jsonValueIs(String path, String expectedValue) {
        String actualValue = this.response.extract().jsonPath().getString(path);
        assertThat(actualValue).as("Actual value '%s' is not equals to expected '%s' for the path '%s' and response: \n%s",
                actualValue, expectedValue, path,
                this.response.extract().response().andReturn().asPrettyString()).isEqualTo(expectedValue);
        return this;
    }

    @Step("Get json value by path: {path}")
    public String getJsonValue(String path) {
        String value = this.response.extract().jsonPath().getString(path);
        assertThat(value).isNotNull();
        return value;
    }

    @Step("Check json value by path '{path}' and expected value '{expectedValue}'")
    public HttpResponse jsonValueContains(String path, String expectedValue) {
        String actualValue = this.response.extract().jsonPath().getString(path);
        assertThat(actualValue).as("Actual value '%s' does not equals to expected '%s' for the path '%s' and response: \n%s",
                actualValue, expectedValue, path,
                this.response.extract().response().andReturn().asPrettyString()).contains(expectedValue);
        return this;
    }
}
