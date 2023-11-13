package nabil.zchat.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ahmed Nabil
 */
@Getter
@Setter
@Builder
public class ChatMessageRequestDto {
    private Long id;
    private String content;
    private Long senderId;
    private Long receiverId;
    private Long chatId;
    private String senderSubject;
    private String receiverSubject;
}
