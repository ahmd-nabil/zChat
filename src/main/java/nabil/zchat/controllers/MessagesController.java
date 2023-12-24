package nabil.zchat.controllers;

import lombok.RequiredArgsConstructor;
import nabil.zchat.dtos.ChatMessageRequestDto;
import nabil.zchat.dtos.SimpleChatMessageResponse;
import nabil.zchat.exceptions.MessageNotFoundException;
import nabil.zchat.exceptions.UserNotFoundException;
import nabil.zchat.services.MessagesService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Ahmed Nabil
 */
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MessagesController {

    private final MessagesService messagesService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/messages/private")
    public void sendPrivateMessage(@Payload ChatMessageRequestDto dto, Authentication authentication) {
        if(dto == null) throw new MessageNotFoundException();
        if(authentication == null) throw new UserNotFoundException();
        messagesService.sendMessage(dto, authentication);
    }

    @MessageMapping("/messages")
    @SendTo("/messages")
    public void sendPublicMessage(@Payload ChatMessageRequestDto dto) {
        if(dto == null) throw new MessageNotFoundException();
        simpMessagingTemplate.convertAndSend("/topic/messages", dto);
    }

    @GetMapping("/messages")
    @ResponseBody
    public List<SimpleChatMessageResponse> getAllMessagesByUser(
                        @RequestParam(value = "userId", required = false) Long userId,
                        @RequestParam(value = "subject", required = false) String subject) {
        return subject != null ? this.messagesService.getAllUserMessages(subject) : this.messagesService.getAllUserMessages(userId);
    }
}
