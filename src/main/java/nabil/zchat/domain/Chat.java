package nabil.zchat.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ahmed Nabil
 */
@Data
@Builder
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
    private List<ChatUser> chatUsers = new ArrayList<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public Chat(Long id, List<ChatUser> chatUsers, List<ChatMessage> chatMessages) {
        this.id = id;
        this.setChatUsers(chatUsers);
        this.setChatMessages(chatMessages);
    }

    public Chat() {
    }

    public void setChatUsers(List<ChatUser> chatUsers) {
        this.chatUsers.forEach(user -> user.addChat(this));
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        if (chatMessages == null) return;
        chatMessages.forEach(this::addMessage);
    }

    public void addMessage(ChatMessage message) {
        message.setChat(this);
        this.chatMessages.add(message);
    }

    public void removeMessage(ChatMessage message) {
        this.chatMessages.remove(message);
        message.setChat(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || this.id == null) return false;
        Chat chat = (Chat) o;
        return id == chat.id;
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", chatMessages=" + chatMessages +
                '}';
    }
}
