package nabil.zchat.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ahmed Nabil
 */
@Getter
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
