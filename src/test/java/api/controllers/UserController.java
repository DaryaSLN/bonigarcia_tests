package api.controllers;

import api.constants.CommonConstants;
import api.models.User;
import api.testdata.TestData;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserController {
    RequestSpecification requestSpecification;
    private static final String USER_ENDPOINT = "user";

    public UserController() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .baseUri(CommonConstants.BASE_URI)
                .filter(new AllureRestAssured());
    }

    @Step("Create default user")
    public HttpResponse createDefaultUser() {
        this.requestSpecification.body(TestData.DEFAULT_USER);
        return new HttpResponse(given(this.requestSpecification).post(USER_ENDPOINT).then());
    }

    @Step("Create user")
    public HttpResponse createUser(User user) {
        this.requestSpecification.body(user);
        return new HttpResponse(given(this.requestSpecification)
                .post(USER_ENDPOINT)
                .then());
    }

    @Step("Update user")
    public HttpResponse updateUser(User user, String userName) {
        this.requestSpecification.body(user);
        return new HttpResponse(given(this.requestSpecification)
                .put(USER_ENDPOINT + "/" + userName)
                .then());
    }

    @Step("Get user by username")
    public HttpResponse getUserByUsername(String username) {
        return new HttpResponse(given(this.requestSpecification)
                .get(USER_ENDPOINT + "/" + username)
                .then());
    }

    @Step("Delete user by username")
    public HttpResponse deleteUserByUsername(String username) {
        return new HttpResponse(given(this.requestSpecification)
                .delete(USER_ENDPOINT + "/" + username)
                .then());
    }
}
