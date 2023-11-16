package nabil.zchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Ahmed Nabil
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private ChatUser sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private ChatUser receiver;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    @JsonIgnore
    private Chat chat;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", content='" + content +
                '}';
    }
}
