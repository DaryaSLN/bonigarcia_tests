package api.testdata;

import api.models.User;

public class TestData {
    public static final User DEFAULT_USER = User.builder()
            .id(123654)
            .username("Name12")
            .firstName("FName")
            .lastName("LName")
            .email("Email")
            .phone("123")
            .userStatus(0)
            .build();

    public static final User UPDATED_USER = User.builder()
            .id(123654)
            .username("New Name12")
            .firstName("New FName")
            .lastName("New LName")
            .email("New Email")
            .phone("New 123")
            .userStatus(0)
            .build();
}
