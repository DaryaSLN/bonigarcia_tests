package api.models;

import lombok.Data;

@Data
public class CreateUserResponse {
    private int code;
    private String type;
    private String message;
}
