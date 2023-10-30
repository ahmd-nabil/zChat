package nabil.zchat.controllers;

import lombok.RequiredArgsConstructor;
import nabil.zchat.dtos.ChatMessageRequestDto;
import nabil.zchat.services.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * @author Ahmed Nabil
 */
@Controller("/")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/messages/private")
    private void sendPrivateMessage(@Payload ChatMessageRequestDto dto, @AuthenticationPrincipal Principal principal) {
        dto.setSender(principal.getName());
        chatService.sendMessage(dto);
    }

    @MessageMapping("/messages")
    @SendTo("/messages")
    private void sendPublicMessage(@Payload ChatMessageRequestDto dto) {
        simpMessagingTemplate.convertAndSend("/topic/messages", dto);
    }
}
