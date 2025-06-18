package api.models;

import lombok.Data;

@Data
public class UpdateUserResponse {
    private int code;
    private String type;
    private String message;
}
