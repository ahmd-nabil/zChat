package nabil.zchat.services;

import lombok.RequiredArgsConstructor;
import nabil.zchat.domain.Chat;
import nabil.zchat.domain.ChatMessage;
import nabil.zchat.dtos.ChatMessageRequestDto;
import nabil.zchat.dtos.SimpleChatMessageResponse;
import nabil.zchat.mappers.ChatMessageMapper;
import nabil.zchat.repositories.ChatMessageRepo;
import nabil.zchat.repositories.ChatRepo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author Ahmed Nabil
 */

@Service
@RequiredArgsConstructor
@Transactional
public class MessagesServiceImpl implements MessagesService {

    private final ChatMessageMapper chatMessageMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatRepo chatRepo;
    private final ChatMessageRepo chatMessageRepo;

    @Override
    public void sendMessage(ChatMessageRequestDto dto) {
        ChatMessage chatMessage = chatMessageMapper.toChatMessage(dto);
        // 1. send message to the sender queue
//        simpMessagingTemplate.convertAndSendToUser(chatMessage.getSender().getSubject(),"/queue/messages", chatMessage);
        // 2. send message to the receiver queue
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getReceiver().getSubject(),"/queue/messages", chatMessage);
        // 2.1 if receiver is offline keep it in some queue to check before sending
        // 3. todo persist the message in DB
        persistMessageInDb(chatMessage);
    }

    private void persistMessageInDb(ChatMessage chatMessage) {
        if(chatMessage.getChat() == null) {
            chatMessage.setChat(Chat.builder().chatUsers(Arrays.asList(chatMessage.getSender(), chatMessage.getReceiver())).build());
        }
        chatMessage.getChat().addMessage(chatMessage);
        chatRepo.save(chatMessage.getChat());
    }

    @Override
    public List<SimpleChatMessageResponse> getAllUserMessages(Long userId) {
        return this.chatMessageRepo.findAllMessagesByUserId(userId)
                .stream()
                .map(chatMessageMapper::toSimpleChatMessageResponseDto)
                .toList();
    }

    @Override
    public List<SimpleChatMessageResponse> getAllUserMessages(String userSubject) {
        return this.chatMessageRepo.findAllMessagesByUserSubject(userSubject)
                .stream()
                .map(chatMessageMapper::toSimpleChatMessageResponseDto)
                .toList();
    }

    //        simpMessagingTemplate.convertAndSend("/topic/"+chatMessage.getSender(), chatMessage);
//        simpMessagingTemplate.convertAndSend("/topic/"+chatMessage.getReceiver(), chatMessage);
}
