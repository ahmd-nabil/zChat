package nabil.zchat.controllers;

import lombok.RequiredArgsConstructor;
import nabil.zchat.domain.ChatUser;
import nabil.zchat.exceptions.UserNotFoundException;
import nabil.zchat.repositories.ChatUserRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ahmed Nabil
 */
@RestController
@RequestMapping()
@RequiredArgsConstructor
public class UserController {

    private final ChatUserRepo chatUserRepo;

    @GetMapping("/auth")
    private ResponseEntity<Authentication> getAuthentication(Authentication authentication) {
        return ResponseEntity.ofNullable(authentication);
    }

    @GetMapping("/private")
    private ResponseEntity<Authentication> getPrivate(Authentication authentication) {
        System.out.println(authentication);
        return ResponseEntity.ofNullable(authentication);
    }

    @GetMapping("/users")
    public ResponseEntity<ChatUser> findUserByEmail(@RequestParam(value = "email") String email) {
        ChatUser chatUser = this.chatUserRepo.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return ResponseEntity.ok().body(chatUser);
    }
}
