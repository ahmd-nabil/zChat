package nabil.zchat.services;

import lombok.RequiredArgsConstructor;
import nabil.zchat.dtos.ChatMessageRequestDto;
import nabil.zchat.entities.ChatMessage;
import nabil.zchat.mappers.ChatMessageMapper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Ahmed Nabil
 */

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageMapper chatMessageMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void sendMessage(ChatMessageRequestDto dto) {
        ChatMessage chatMessage = chatMessageMapper.toChatMessage(dto);
        chatMessage.setSender("DEFAULT");
        // 1. send message to the sender queue
        simpMessagingTemplate.convertAndSend("/topic/"+chatMessage.getSender(), chatMessage);
//        simpMessagingTemplate.convertAndSendToUser(chatMessage.getSender(),"/topic/"+chatMessage.getSender(), chatMessage);
        // 2. send message to the receiver queue
        simpMessagingTemplate.convertAndSend("/topic/"+chatMessage.getReceiver(), chatMessage);
//        simpMessagingTemplate.convertAndSendToUser(chatMessage.getReceiver(),"/topic/"+chatMessage.getReceiver(), chatMessage);
        // 3. persist the message in DB
    }
}
