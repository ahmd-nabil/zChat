package nabil.zchat.services;

import nabil.zchat.dtos.ChatMessageRequestDto;
import nabil.zchat.dtos.SimpleChatMessageResponse;

import java.util.List;

/**
 * @author Ahmed Nabil
 */
public interface MessagesService {

    void sendMessage(ChatMessageRequestDto dto);

    List<SimpleChatMessageResponse> getAllUserMessages(Long userId);
    List<SimpleChatMessageResponse> getAllUserMessages(String userSubject);

}
