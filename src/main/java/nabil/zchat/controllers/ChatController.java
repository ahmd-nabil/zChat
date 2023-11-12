package nabil.zchat.controllers;

import lombok.RequiredArgsConstructor;
import nabil.zchat.domain.Chat;
import nabil.zchat.services.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Ahmed Nabil
 */
@Controller
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChat(@PathVariable Long id) {
        return ResponseEntity.ok(this.chatService.getChatById(id));
    }

    @GetMapping
    public List<Chat> getAllUserChats(Authentication authentication) {
        return this.chatService.getAllChatsByUserSubject(authentication.getName());
    }
}
