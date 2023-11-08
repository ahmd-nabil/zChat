package nabil.zchat.dtos;

import lombok.Getter;
import lombok.Setter;
import nabil.zchat.domain.Chat;
import nabil.zchat.domain.ChatUser;

/**
 * @author Ahmed Nabil
 */
@Getter
@Setter
public class ChatMessageRequestDto {
    private Long id;
    private String content;
    private ChatUser sender;
    private ChatUser receiver;
    private Chat chat;
}
