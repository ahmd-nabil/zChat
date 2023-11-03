package nabil.zchat.services;

import lombok.RequiredArgsConstructor;
import nabil.zchat.domain.ChatMessage;
import nabil.zchat.dtos.ChatMessageRequestDto;
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
        // 1. send message to the sender queue
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getSender(),"/queue/messages", chatMessage);
        // 2. send message to the receiver queue
        // 2.1 if receiver is offline keep it in some queue to check before sending
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getReceiver(),"/queue/messages", chatMessage);
        // 3. todo persist the message in DB
    }



//        simpMessagingTemplate.convertAndSend("/topic/"+chatMessage.getSender(), chatMessage);
//        simpMessagingTemplate.convertAndSend("/topic/"+chatMessage.getReceiver(), chatMessage);
}
