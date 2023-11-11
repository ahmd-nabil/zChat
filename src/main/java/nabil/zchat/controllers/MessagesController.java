package nabil.zchat.controllers;

import lombok.RequiredArgsConstructor;
import nabil.zchat.dtos.ChatMessageRequestDto;
import nabil.zchat.dtos.SimpleChatMessageResponse;
import nabil.zchat.services.MessagesService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Ahmed Nabil
 */
@Controller("/")
@RequiredArgsConstructor
public class MessagesController {

    private final MessagesService messagesService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/messages/private")
    private void sendPrivateMessage(@Payload ChatMessageRequestDto dto) {
        messagesService.sendMessage(dto);
    }

    @MessageMapping("/messages")
    @SendTo("/messages")
    private void sendPublicMessage(@Payload ChatMessageRequestDto dto) {
        simpMessagingTemplate.convertAndSend("/topic/messages", dto);
    }

    @GetMapping("/messages")
    @ResponseBody
    public List<SimpleChatMessageResponse> getAllMessagesByUser(@RequestParam(required = false) Long userId, @RequestParam(required = false) String subject) {
        return subject != null ? this.messagesService.getAllUserMessages(subject) : this.messagesService.getAllUserMessages(userId);
    }
}
