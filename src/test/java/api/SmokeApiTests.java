package api;

import api.controllers.UserController;
import api.models.CreateUserResponse;
import api.models.DeleteUserResponse;
import api.models.UpdateUserResponse;
import api.models.User;
import io.restassured.response.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import static api.testdata.TestData.DEFAULT_USER;
import static api.testdata.TestData.INVALID_USER;
import static api.testdata.TestData.UPDATED_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SmokeApiTests {
    UserController userController = new UserController();

    @Test
    @Order(1)
    void createDefaultUserTest() {
        Response response = userController.createDefaultUser();
        CreateUserResponse createUserResponse = response.as(CreateUserResponse.class);

        assertEquals(200, response.getStatusCode());
        assertEquals(200, createUserResponse.getCode());
        assertEquals("unknown", createUserResponse.getType());
        assertFalse(createUserResponse.getMessage().isEmpty());
    }

    @Test
    @Order(2)
    void createInvalidUserTest() {
        Response response = userController.createUser(INVALID_USER);
        CreateUserResponse createUserResponse = response.as(CreateUserResponse.class);

        assertEquals(200, response.getStatusCode());
        assertEquals(200, createUserResponse.getCode());
        assertEquals("unknown", createUserResponse.getType());
        assertEquals("0", createUserResponse.getMessage());
    }

    @Test
    @Order(3)
    void updateUserTest() {
        Response response = userController.updateUser(UPDATED_USER, DEFAULT_USER.getFirstName());
        UpdateUserResponse updateUserResponse = response.as(UpdateUserResponse.class);

        assertEquals(200, response.getStatusCode());
        assertEquals(200, updateUserResponse.getCode());
        assertEquals("unknown", updateUserResponse.getType());
        assertFalse(updateUserResponse.getMessage().isEmpty());
    }

    @Test
    @Order(4)
    void getUserByNameTest() {
        Response response = userController.getUserByUsername(DEFAULT_USER.getUsername());
        User user = response.as(User.class);

        assertEquals(200, response.getStatusCode());
        assertEquals(UPDATED_USER, user);
    }

    @Test
    @Order(5)
    void deleteUserTest() {
        String userToDelete = DEFAULT_USER.getUsername();

        Response response = userController.deleteUserByUsername(userToDelete);
        DeleteUserResponse deleteUserResponse = response.as(DeleteUserResponse.class);

        assertEquals(200, response.getStatusCode());
        assertEquals(200, deleteUserResponse.getCode());
        assertEquals("unknown", deleteUserResponse.getType());
        assertEquals(userToDelete, deleteUserResponse.getMessage());
    }
}
