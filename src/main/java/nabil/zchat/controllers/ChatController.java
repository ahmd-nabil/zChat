package nabil.zchat.controllers;

import lombok.RequiredArgsConstructor;
import nabil.zchat.dtos.ChatMessageRequestDto;
import nabil.zchat.services.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

/**
 * @author Ahmed Nabil
 */
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    @MessageMapping("/sendMessage")
    private void sendMessage(@Payload ChatMessageRequestDto dto) {
        chatService.sendMessage(dto);
    }
}
