package api.controllers;

import api.constants.CommonConstants;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PetController {
    RequestSpecification requestSpecification;
    private static final String PET_ENDPOINT = "pet";

    public PetController() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .baseUri(CommonConstants.BASE_URI)
                .filter(new AllureRestAssured());
    }

    @Step("Upload image")
    public HttpResponse uploadImage(String fileName) {
        return new HttpResponse(given()
                .spec(requestSpecification)
                .multiPart("file", new File("src/test/resources/images/" + fileName))
                .contentType("multipart/form-data")
                .when()
                .post(PET_ENDPOINT + "/1/uploadImage")
                .then());
    }
}
