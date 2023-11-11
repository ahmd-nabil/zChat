package nabil.zchat.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ahmed Nabil
 */
@RestController
public class UserController {

    @GetMapping("/auth")
    private ResponseEntity<Authentication> getAuthentication(Authentication authentication) {
        return ResponseEntity.ofNullable(authentication);
    }

    @GetMapping("/private")
    private ResponseEntity<Authentication> getPrivate(Authentication authentication) {
        System.out.println(authentication);
        return ResponseEntity.ofNullable(authentication);
    }
}
