package nabil.zchat.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ahmed Nabil
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChatMessage {
    private Long id;

    @NotNull
    @NotBlank
    private String content;

    @NotNull
    @NotBlank
    private String sender;  // todo change type to user

    @NotNull
    @NotBlank
    private String receiver; // todo change type to user
}
