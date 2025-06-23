package api;

import api.controllers.PetController;
import api.utils.PdfUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Java6Assertions.assertThat;


class FileTests {
    PetController petController = new PetController();
    final String uploadFileName = "2016516223709_2.jpg";

    @Test
    void uploadFileTest() {
        petController.uploadImage(uploadFileName)
                .statusCodeIs(200)
                .jsonValueIs("code", "200")
                .jsonValueIs("type", "unknown")
                .jsonValueContains("message", "File uploaded to ./" + uploadFileName);
    }

    @Test
    void testDownloadHttpClient() {
        String endpoint = "https://alfabank.servicecdn.ru/site-upload/67/dd/356/zayavlenie-IZK.pdf";
        String downloadFileName = "downloaded.pdf";

        Response response =
                given().
                        when().
                        get(endpoint).
                        then().
                        contentType("application/pdf").
                        statusCode(200).
                        extract().response();
        PdfUtils.savePdf(response, downloadFileName);
        File downloadedFile = new File(downloadFileName);
        assertThat(downloadedFile).exists();
    }
}
