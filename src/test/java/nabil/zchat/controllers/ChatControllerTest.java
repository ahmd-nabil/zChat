package nabil.zchat.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nabil.zchat.domain.ChatMessage;
import nabil.zchat.dtos.ChatResponse;
import nabil.zchat.services.ChatService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static nabil.zchat.TestUtils.API;
import static nabil.zchat.TestUtils.EXPECTED_CHAT_RESPONSE_ARRAY_JSON;

/**
 * @author Ahmed Nabil
 */

@WebMvcTest(ChatController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ChatControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ChatService chatService;

    @Autowired
    ObjectMapper objectMapper;

    private final List<ChatResponse> chatResponseArray = List.of(
            ChatResponse.builder()
                    .id(1L)
                    .chatMessages(new ArrayList<>())
                    .chatUsers(new ArrayList<>())
                    .lastMessage(ChatMessage.builder().content("last message").build()).build(),
            ChatResponse.builder()
                    .id(2L)
                    .chatMessages(new ArrayList<>())
                    .chatUsers(new ArrayList<>())
                    .lastMessage(ChatMessage.builder().content("last message").build()).build()
            );

    @Test
    void getChatSuccessTest() throws Exception {
        BDDMockito.given(chatService.getChatById(BDDMockito.any())).willReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get(API+"/chats/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAllUserChatsSuccessTest() throws Exception {
        BDDMockito.given(chatService.getAllChatsByUserSubject(BDDMockito.any())).willReturn(chatResponseArray);
        mockMvc.perform(MockMvcRequestBuilders.get(API+"/chats"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(EXPECTED_CHAT_RESPONSE_ARRAY_JSON, false))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addNewChatSuccessTest() throws Exception {
        BDDMockito.given(chatService.addNewChat(BDDMockito.anyList())).willReturn(ChatResponse.builder().build());
        mockMvc.perform(MockMvcRequestBuilders
                        .post(API + "/chats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ArrayList<>())))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"));
    }
}
