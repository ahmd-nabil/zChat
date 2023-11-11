package nabil.zchat.domain;

import jakarta.persistence.*;
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
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private Chat chat;

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", content='" + content +
                '}';
    }
}
