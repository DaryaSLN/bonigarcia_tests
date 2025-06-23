package api;

import api.controllers.UserController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static api.testdata.TestData.DEFAULT_USER;
import static api.testdata.TestData.UPDATED_USER;

class UserApiTests {
    UserController userController = new UserController();

    @AfterEach
    void clear() {
        userController.deleteUserByUsername(DEFAULT_USER.getUsername());
        userController.deleteUserByUsername(UPDATED_USER.getUsername());
    }

    @Test
    void createDefaultUserTest() {
        userController.createDefaultUser()
                .statusCodeIs(200);
    }

    @Test
    void createDefaultUserExtendedTest() {
        String expectedUserId = userController.createDefaultUser()
                .statusCodeIs(200)
                .getJsonValue("message");

        userController.getUserByUsername(DEFAULT_USER.getUsername())
                .statusCodeIs(200)
                .jsonValueIs("id", expectedUserId)
                .jsonValueIs("username", DEFAULT_USER.getUsername())
                .jsonValueIs("email", DEFAULT_USER.getEmail());
    }

    @Test
    void updateUserTest() {
        userController.createDefaultUser();

        userController.updateUser(UPDATED_USER, DEFAULT_USER.getUsername())
                .statusCodeIs(200)
                .jsonValueIs("code", "200")
                .jsonValueIs("type", "unknown")
                .getJsonValue("message");

        userController.getUserByUsername(UPDATED_USER.getUsername())
                .statusCodeIs(200)
                .jsonValueIs("username", UPDATED_USER.getUsername())
                .jsonValueIs("firstName", UPDATED_USER.getFirstName())
                .jsonValueIs("lastName", UPDATED_USER.getLastName())
                .jsonValueIs("email", UPDATED_USER.getEmail())
                .jsonValueIs("phone", UPDATED_USER.getPhone());
    }

    @Test
    void getUserByNameTest() {
        userController.createDefaultUser();

        userController.getUserByUsername(DEFAULT_USER.getUsername())
                .statusCodeIs(200)
                .jsonValueIs("username", DEFAULT_USER.getUsername())
                .jsonValueIs("firstName", DEFAULT_USER.getFirstName())
                .jsonValueIs("lastName", DEFAULT_USER.getLastName())
                .jsonValueIs("email", DEFAULT_USER.getEmail())
                .jsonValueIs("phone", DEFAULT_USER.getPhone());
    }

    @Test
    void deleteUserTest() {
        userController.createDefaultUser();

        userController.deleteUserByUsername(DEFAULT_USER.getUsername())
                .statusCodeIs(200)
                .jsonValueIs("code", "200")
                .jsonValueIs("type", "unknown")
                .jsonValueContains("message", DEFAULT_USER.getUsername());
    }
}
