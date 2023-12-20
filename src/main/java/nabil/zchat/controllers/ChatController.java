package nabil.zchat.controllers;

import lombok.RequiredArgsConstructor;
import nabil.zchat.dtos.ChatResponse;
import nabil.zchat.services.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ahmed Nabil
 */
@Controller
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {
    private final static String API = "http://localhost:8080";

    private final ChatService chatService;

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<ChatResponse> getChat(@PathVariable Long id) {
        if(id == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(this.chatService.getChatById(id));
    }

    @ResponseBody
    @GetMapping
    public List<ChatResponse> getAllUserChats(Authentication authentication) {
        return this.chatService.getAllChatsByUserSubject(authentication);
    }

    @ResponseBody
    @PostMapping()
    public ResponseEntity<Void> addNewChat(@RequestBody ArrayList<String> chatUsersSubjects) {
        ChatResponse response = chatService.addNewChat(chatUsersSubjects);
        return ResponseEntity.created(URI.create(API + response.getId())).build();
    }
}
