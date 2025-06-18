package api.models;

import lombok.Data;

@Data
public class DeleteUserResponse {
    private int code;
    private String type;
    private String message;
}
