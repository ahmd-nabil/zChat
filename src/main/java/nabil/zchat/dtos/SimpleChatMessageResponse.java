package nabil.zchat.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ahmed Nabil
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleChatMessageResponse {
    private Long id;
    private String content;
    private Long senderId;
    private Long receiverId;
    private Long chatId;
    private String senderSubject;
    private String receiverSubject;
}
