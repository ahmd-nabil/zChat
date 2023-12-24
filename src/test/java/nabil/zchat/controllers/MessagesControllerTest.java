package nabil.zchat.controllers;

import nabil.zchat.dtos.ChatMessageRequestDto;
import nabil.zchat.dtos.SimpleChatMessageResponse;
import nabil.zchat.exceptions.MessageNotFoundException;
import nabil.zchat.exceptions.UserNotFoundException;
import nabil.zchat.services.MessagesService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Ahmed Nabil
 */
@WebMvcTest(MessagesController.class)
class MessagesControllerTest {
    @Autowired
    MessagesController messagesController;

    @MockBean
    MessagesService messagesService;

    @MockBean
    SimpMessagingTemplate simpMessagingTemplate;
    @Test
    public void test_send_private_message_calls_sendMessage_method() {
        // Arrange
        ChatMessageRequestDto dto = ChatMessageRequestDto.builder().build();
        Authentication authentication = Mockito.mock(Authentication.class);

        // Act
        messagesController.sendPrivateMessage(dto, authentication);

        // Assert
        Mockito.verify(messagesService, Mockito.times(1)).sendMessage(dto, authentication);
    }

    @Test
    public void test_send_public_message_sends_message_to_topic_messages() {
        // Arrange
        ChatMessageRequestDto dto = ChatMessageRequestDto.builder().build();

        // Act
        messagesController.sendPublicMessage(dto);

        // Assert
        Mockito.verify(simpMessagingTemplate, Mockito.times(1)).convertAndSend("/topic/messages", dto);
    }

    @Test
    public void test_get_all_messages_by_user_with_valid_user_id_returns_list_of_SimpleChatMessageResponse() {
        // Arrange
        Long userId = 1L;
        List<SimpleChatMessageResponse> expectedResponse = new ArrayList<>();
        Mockito.when(messagesService.getAllUserMessages(userId)).thenReturn(expectedResponse);

        // Act
        List<SimpleChatMessageResponse> response = messagesController.getAllMessagesByUser(userId, null);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    public void test_get_all_messages_by_user_with_valid_user_subject_returns_list_of_SimpleChatMessageResponse() {
        // Arrange
        String userSubject = "user1";
        List<SimpleChatMessageResponse> expectedResponse = new ArrayList<>();
        Mockito.when(messagesService.getAllUserMessages(userSubject)).thenReturn(expectedResponse);

        // Act
        List<SimpleChatMessageResponse> response = messagesController.getAllMessagesByUser(null, userSubject);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    public void test_get_all_messages_by_user_with_no_parameters_returns_empty_list() {
        // Arrange
        List<SimpleChatMessageResponse> expectedResponse = new ArrayList<>();
        Mockito.when(messagesService.getAllUserMessages((Long) null)).thenReturn(expectedResponse);

        // Act
        List<SimpleChatMessageResponse> response = messagesController.getAllMessagesByUser(null, null);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    public void test_send_private_message_with_null_payload_throws_exception() {
        // Arrange
        ChatMessageRequestDto dto = null;
        Authentication authentication = Mockito.mock(Authentication.class);

        // Act and Assert
        assertThrows(MessageNotFoundException.class, () -> messagesController.sendPrivateMessage(dto, authentication));
    }

    @Test
    public void test_send_private_message_with_null_authentication_throws_exception() {
        // Arrange
        ChatMessageRequestDto dto = ChatMessageRequestDto.builder().build();
        Authentication authentication = null;

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> messagesController.sendPrivateMessage(dto, authentication));
    }

    @Test
    public void test_send_public_message_with_null_payload_throws_exception() {
        // Arrange
        ChatMessageRequestDto dto = null;

        // Act and Assert
        assertThrows(MessageNotFoundException.class, () -> messagesController.sendPublicMessage(dto));
    }

    @Test
    public void test_get_all_messages_by_user_with_negative_user_id_returns_empty_list() {
        // Arrange
        Long userId = -1L;
        List<SimpleChatMessageResponse> expectedResponse = new ArrayList<>();
        Mockito.when(messagesService.getAllUserMessages(userId)).thenReturn(expectedResponse);

        // Act
        List<SimpleChatMessageResponse> response = messagesController.getAllMessagesByUser(userId, null);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    public void test_get_all_messages_by_user_with_non_existent_user_subject_returns_empty_list() {
        // Arrange
        String userSubject = "nonexistent";
        List<SimpleChatMessageResponse> expectedResponse = new ArrayList<>();
        Mockito.when(messagesService.getAllUserMessages(userSubject)).thenReturn(expectedResponse);

        // Act
        List<SimpleChatMessageResponse> response = messagesController.getAllMessagesByUser(null, userSubject);

        // Assert
        assertEquals(expectedResponse, response);
    }
}