package api.controllers;

import api.constants.CommonConstants;
import api.models.User;
import api.testdata.TestData;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserController {
    RequestSpecification requestSpecification;
    private static final String USER_ENDPOINT = "user";

    public UserController() {
        this.requestSpecification = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .baseUri(CommonConstants.BASE_URI)
                .filter(new AllureRestAssured());
    }

    @Step("Create default user")
    public Response createDefaultUser() {
        return given(this.requestSpecification)
                .body(TestData.DEFAULT_USER)
                .when()
                .post(USER_ENDPOINT)
                .andReturn();
    }

    @Step("Create user")
    public Response createUser(User user) {
        return given(this.requestSpecification)
                .body(user)
                .when()
                .post(USER_ENDPOINT)
                .andReturn();
    }

    @Step("Update user")
    public Response updateUser(User user, String userName) {
        return given(this.requestSpecification)
                .body(user)
                .when()
                .put(USER_ENDPOINT + "/" + userName)
                .andReturn();
    }

    @Step("Get user by username")
    public Response getUserByUsername(String username) {
        return given(this.requestSpecification)
                .when()
                .get(USER_ENDPOINT + "/" + username)
                .andReturn();
    }

    @Step("Delete user by username")
    public Response deleteUserByUsername(String username) {
        return given(this.requestSpecification)
                .when()
                .delete(USER_ENDPOINT + "/" + username)
                .andReturn();
    }
}
