package nabil.zchat.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nabil.zchat.domain.ChatMessage;
import nabil.zchat.domain.ChatUser;

import java.util.List;

/**
 * @author Ahmed Nabil
 */
@Getter
@Setter
@Builder
public class ChatResponse {
    private Long id;
    private List<ChatUser> chatUsers;
    private List<SimpleChatMessageResponse> chatMessages;
    private ChatMessage lastMessage;
}
