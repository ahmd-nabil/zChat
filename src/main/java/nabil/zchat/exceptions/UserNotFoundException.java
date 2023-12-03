package nabil.zchat.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Ahmed Nabil
 */

@ResponseStatus(reason = "User Not found!", value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{
}
