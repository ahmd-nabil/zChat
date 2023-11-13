package nabil.zchat.services;

import nabil.zchat.dtos.ChatMessageRequestDto;
import nabil.zchat.dtos.SimpleChatMessageResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * @author Ahmed Nabil
 */
public interface MessagesService {

    void sendMessage(ChatMessageRequestDto dto, Authentication authentication);
    List<SimpleChatMessageResponse> getAllUserMessages(Long userId);
    List<SimpleChatMessageResponse> getAllUserMessages(String userSubject);

}
