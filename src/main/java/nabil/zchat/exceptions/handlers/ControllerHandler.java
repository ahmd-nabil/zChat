package nabil.zchat.exceptions.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nabil.zchat.exceptions.ChatNotFoundException;
import nabil.zchat.exceptions.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * @author Ahmed Nabil
 */
@RestControllerAdvice
public class ControllerHandler extends ResponseEntityExceptionHandler {
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(HttpServletRequest request, UserNotFoundException ex) {
        var headers = new HttpHeaders();
        headers.put("content-type", List.of("application/json"));
        return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<?> handleChatNotFoundException(HttpServletRequest req, HttpServletResponse res, ChatNotFoundException ex) {
        var headers = new HttpHeaders();
        headers.put("content-type", List.of("application/json"));
        return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.NOT_FOUND);
    }
}
