package nabil.zchat.controllers;

import lombok.RequiredArgsConstructor;
import nabil.zchat.dtos.ChatMessageRequestDto;
import nabil.zchat.repositories.ChatUserRepo;
import nabil.zchat.services.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

/**
 * @author Ahmed Nabil
 */
@Controller("/")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatUserRepo userRepo;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/messages/private")
    private void sendPrivateMessage(@Payload ChatMessageRequestDto dto, Authentication authentication) {
//        ChatUser sender = userRepo.findBySubject(authentication.getName()).orElseThrow();
//        dto.setSender(sender);
        chatService.sendMessage(dto);
    }

    @MessageMapping("/messages")
    @SendTo("/messages")
    private void sendPublicMessage(@Payload ChatMessageRequestDto dto) {
        simpMessagingTemplate.convertAndSend("/topic/messages", dto);
    }
}
