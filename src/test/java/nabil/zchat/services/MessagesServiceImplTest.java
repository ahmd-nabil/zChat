package nabil.zchat.services;

import nabil.zchat.domain.Chat;
import nabil.zchat.domain.ChatMessage;
import nabil.zchat.domain.ChatUser;
import nabil.zchat.dtos.ChatMessageRequestDto;
import nabil.zchat.dtos.SimpleChatMessageResponse;
import nabil.zchat.mappers.ChatMessageMapper;
import nabil.zchat.repositories.ChatMessageRepo;
import nabil.zchat.repositories.ChatRepo;
import nabil.zchat.repositories.ChatUserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

/**
 * @author Ahmed Nabil
 */
@ExtendWith(MockitoExtension.class)
class MessagesServiceImplTest {

    @Mock
    ChatUserRepo userRepo;

    @Mock
    ChatRepo chatRepo;

    @Mock
    ChatMessageRepo chatMessageRepo;

    @Mock
    ChatMessageMapper chatMessageMapper;

    @Mock
    SimpMessagingTemplate simpMessagingTemplate;

    @InjectMocks
    MessagesServiceImpl messagesService;
    @Test
    public void test_send_message_successfully() {
        // Arrange
        ChatMessageRequestDto dto = ChatMessageRequestDto.builder()
                .content("Test message")
                .senderSubject("sender")
                .receiverSubject("receiver")
                .build();
        Authentication authentication = Mockito.mock(Authentication.class);
        ChatUser sender = ChatUser.builder()
                .subject("sender")
                .build();
        ChatUser receiver = ChatUser.builder()
                .subject("receiver")
                .isOnline(true)
                .build();
        ChatMessage newMessage = ChatMessage.builder()
                .content(dto.getContent())
                .sender(sender)
                .receiver(receiver)
                .build();
        Chat chat = Chat.builder()
                .chatUsers(Arrays.asList(sender, receiver))
                .build();
        SimpleChatMessageResponse messageResponse = SimpleChatMessageResponse.builder()
                .id(newMessage.getId())
                .content(newMessage.getContent())
                .senderId(newMessage.getSender().getId())
                .receiverId(newMessage.getReceiver().getId())
                .chatId(chat.getId())
                .senderSubject(newMessage.getSender().getSubject())
                .receiverSubject(newMessage.getReceiver().getSubject())
                .createdAt(newMessage.getCreatedAt())
                .build();
        Mockito.when(userRepo.findBySubject(dto.getSenderSubject())).thenReturn(Optional.of(sender));
        Mockito.when(userRepo.findBySubject(dto.getReceiverSubject())).thenReturn(Optional.of(receiver));
        Mockito.when(chatMessageMapper.toSimpleChatMessageResponseDto(any())).thenReturn(messageResponse);

        // Act
        messagesService.sendMessage(dto, authentication);

        // Assert
        Mockito.verify(simpMessagingTemplate, Mockito.times(1)).convertAndSendToUser(sender.getSubject(), "/queue/messages", messageResponse);
        Mockito.verify(simpMessagingTemplate, Mockito.times(1)).convertAndSendToUser(receiver.getSubject(), "/queue/messages", messageResponse);
    }

    @Test
    public void test_dalay_sending_message_when_user_is_offline() {
        // Arrange
        ChatMessageRequestDto dto = ChatMessageRequestDto.builder()
                .content("Test message")
                .senderSubject("sender")
                .receiverSubject("receiver")
                .build();
        Authentication authentication = Mockito.mock(Authentication.class);
        ChatUser sender = ChatUser.builder()
                .subject("sender")
                .build();
        ChatUser receiver = ChatUser.builder()
                .subject("receiver")
                .build();
        Chat chat = Chat.builder()
                .chatUsers(Arrays.asList(sender, receiver))
                .build();
        SimpleChatMessageResponse messageResponse = SimpleChatMessageResponse.builder()
                .id(1L)
                .content(dto.getContent())
                .senderId(sender.getId())
                .receiverId(receiver.getId())
                .chatId(chat.getId())
                .senderSubject(sender.getSubject())
                .receiverSubject(receiver.getSubject())
                .createdAt(LocalDateTime.now())
                .build();
        Mockito.when(userRepo.findBySubject(dto.getSenderSubject())).thenReturn(Optional.of(sender));
        Mockito.when(userRepo.findBySubject(dto.getReceiverSubject())).thenReturn(Optional.of(receiver));
        Mockito.when(chatMessageMapper.toSimpleChatMessageResponseDto(any())).thenReturn(messageResponse);

        // Act
        messagesService.sendMessage(dto, authentication);

        // Assert
        Mockito.verify(simpMessagingTemplate, Mockito.times(1)).convertAndSendToUser(sender.getSubject(), "/queue/messages", messageResponse);
        Mockito.verify(simpMessagingTemplate, Mockito.times(0)).convertAndSendToUser(receiver.getSubject(), "/queue/messages", messageResponse);
    }

    @Test
    public void test_persist_message_in_db() {
        // Arrange
        ChatMessageRequestDto dto = ChatMessageRequestDto.builder()
                .content("Test message")
                .senderSubject("sender")
                .receiverSubject("receiver")
                .chatId(1L)
                .build();
        Authentication authentication = Mockito.mock(Authentication.class);
        ChatUser sender = ChatUser.builder()
                .subject("sender")
                .build();
        ChatUser receiver = ChatUser.builder()
                .subject("receiver")
                .build();
        ChatMessage newMessage = ChatMessage.builder()
                .content(dto.getContent())
                .sender(sender)
                .receiver(receiver)
                .build();
        Chat chat = Chat.builder()
                .chatUsers(Arrays.asList(sender, receiver))
                .build();
        SimpleChatMessageResponse messageResponse = SimpleChatMessageResponse.builder()
                .id(newMessage.getId())
                .content(newMessage.getContent())
                .senderId(newMessage.getSender().getId())
                .receiverId(newMessage.getReceiver().getId())
                .chatId(chat.getId())
                .senderSubject(newMessage.getSender().getSubject())
                .receiverSubject(newMessage.getReceiver().getSubject())
                .createdAt(newMessage.getCreatedAt())
                .build();
        Mockito.when(userRepo.findBySubject(dto.getSenderSubject())).thenReturn(Optional.of(sender));
        Mockito.when(userRepo.findBySubject(dto.getReceiverSubject())).thenReturn(Optional.of(receiver));
        Mockito.when(chatMessageMapper.toSimpleChatMessageResponseDto(any())).thenReturn(messageResponse);
        Mockito.when(chatRepo.findById(any())).thenReturn(Optional.of(chat));
        // Act
        messagesService.sendMessage(dto, authentication);

        // Assert
        Mockito.verify(chatRepo, Mockito.times(1)).findById(any());
    }

    @Test
    public void test_get_all_user_messages_by_user_id() {
        // Arrange
        Long userId = 1L;
        List<ChatMessage> messages = Arrays.asList(
                ChatMessage.builder().id(1L).content("Message 1").build(),
                ChatMessage.builder().id(2L).content("Message 2").build()
        );
        List<SimpleChatMessageResponse> expected = Arrays.asList(
                SimpleChatMessageResponse.builder().id(1L).content("Message 1").build(),
                SimpleChatMessageResponse.builder().id(2L).content("Message 2").build()
        );
        Mockito.when(chatMessageRepo.findAllMessagesByUserId(userId)).thenReturn(messages);
        Mockito.when(chatMessageMapper.toSimpleChatMessageResponseDto(any())).thenAnswer(invocation -> {
            ChatMessage message = invocation.getArgument(0);
            return SimpleChatMessageResponse.builder()
                    .id(message.getId())
                    .content(message.getContent())
                    .build();
        });

        // Act
        List<SimpleChatMessageResponse> result = messagesService.getAllUserMessages(userId);

        // Assert
        assertEquals(expected, result);
    }
}