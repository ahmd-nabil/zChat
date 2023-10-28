package nabil.zchat.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ahmed Nabil
 */
@Getter
@Setter
@AllArgsConstructor
public class ChatMessageRequestDto {
    private String content;
    private String receiver;
}
