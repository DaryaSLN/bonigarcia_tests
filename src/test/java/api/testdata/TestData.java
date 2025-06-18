package api.testdata;

import api.models.User;

public class TestData {
    public static final User DEFAULT_USER = User.builder()
            .id(123654)
            .username("Name123654")
            .firstName("FName")
            .lastName("LName")
            .email("Email")
            .phone("123")
            .userStatus(0)
            .build();

    public static final User INVALID_USER = User.builder().build();

    public static final User UPDATED_USER = User.builder()
            .id(123654)
            .username("Name123654")
            .firstName("New FName")
            .lastName("New LName")
            .email("New Email")
            .phone("New 123")
            .userStatus(0)
            .build();
}
