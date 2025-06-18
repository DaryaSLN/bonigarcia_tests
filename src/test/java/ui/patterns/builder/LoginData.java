package ui.patterns.builder;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class LoginData {
    private String username;
    private String password;
}
