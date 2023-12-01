package nabil.zchat.services;

import lombok.RequiredArgsConstructor;
import nabil.zchat.domain.Chat;
import nabil.zchat.domain.ChatMessage;
import nabil.zchat.domain.ChatUser;
import nabil.zchat.dtos.ChatMessageRequestDto;
import nabil.zchat.dtos.SimpleChatMessageResponse;
import nabil.zchat.mappers.ChatMessageMapper;
import nabil.zchat.repositories.ChatMessageRepo;
import nabil.zchat.repositories.ChatRepo;
import nabil.zchat.repositories.ChatUserRepo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
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
    private final ChatUserRepo userRepo;

    @Override
    public void sendMessage(ChatMessageRequestDto dto, Authentication authentication) {
        // Map<String, Object> claims = ((JwtAuthenticationToken) authentication).getTokenAttributes();
        // get sender, receiver, chat, and set all of them on chat and persist chat
        ChatUser sender = userRepo.findBySubject(dto.getSenderSubject()).orElseThrow();
        ChatUser receiver = userRepo.findBySubject(dto.getReceiverSubject()).orElseThrow();
        ChatMessage newMessage = ChatMessage.builder()
                                .content(dto.getContent())
                                .sender(sender)
                                .receiver(receiver)
                                .build();
        Chat chat = dto.getChatId() != null ?
                chatRepo.findById(dto.getChatId()).orElseThrow() : Chat.builder().chatUsers(Arrays.asList(sender, receiver)).build();
        chat.addMessage(newMessage);
        SimpleChatMessageResponse messageResponse = chatMessageMapper.toSimpleChatMessageResponseDto(newMessage);
        // 1. send message to the sender queue
        simpMessagingTemplate.convertAndSendToUser(newMessage.getSender().getSubject(),"/queue/messages", messageResponse);
        // 2. send message to the receiver queue
        simpMessagingTemplate.convertAndSendToUser(newMessage.getReceiver().getSubject(),"/queue/messages", messageResponse);
        // 2.1 if receiver is offline keep it in some queue to check before sending
        // 3. todo persist the message in DB
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

}
