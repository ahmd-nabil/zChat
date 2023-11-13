package nabil.zchat.controllers;

import lombok.RequiredArgsConstructor;
import nabil.zchat.dtos.ChatResponse;
import nabil.zchat.services.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Ahmed Nabil
 */
@Controller
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<ChatResponse> getChat(@PathVariable Long id) {
        return ResponseEntity.ok(this.chatService.getChatById(id));
    }

    @ResponseBody
    @GetMapping
    public List<ChatResponse> getAllUserChats(Authentication authentication) {
        return this.chatService.getAllChatsByUserSubject(authentication.getName());
    }
}
