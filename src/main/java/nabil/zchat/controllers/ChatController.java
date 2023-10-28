package nabil.zchat.controllers;

import lombok.RequiredArgsConstructor;
import nabil.zchat.dtos.ChatMessageRequestDto;
import nabil.zchat.entities.ChatMessage;
import nabil.zchat.mappers.ChatMessageMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author Ahmed Nabil
 */
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageMapper chatMessageMapper;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    ChatMessage sendMessage(@Payload ChatMessageRequestDto chatMessageRequestDto) {
        ChatMessage chatMessage = chatMessageMapper.toChatMessage(chatMessageRequestDto);
        chatMessage.setSender("nabil");
        return chatMessage;
    }
}
