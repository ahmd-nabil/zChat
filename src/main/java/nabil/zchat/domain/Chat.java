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
@Entity
@Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<ChatUser> chatUsers = new ArrayList<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @OneToOne
    private ChatMessage lastMessage;

    public Chat(Long id, List<ChatUser> chatUsers, List<ChatMessage> chatMessages, ChatMessage lastMessage) {
        this.id = id;
        this.setChatUsers(chatUsers);
        this.setChatMessages(chatMessages);
        this.lastMessage = lastMessage;
    }

    public Chat() {
        this.chatUsers = new ArrayList<>();
        this.chatMessages = new ArrayList<>();
    }

    public void setChatUsers(List<ChatUser> chatUsers) {
        if (chatUsers == null) return;
        chatUsers.forEach(this::addChatUser);
    }

    public void addChatUser(ChatUser chatUser) {
        chatUser.getChats().add(this);
        this.chatUsers.add(chatUser);
    }

    public void removeChatUser(ChatUser chatUser) {
        this.chatUsers.remove(chatUser);
        chatUser.getChats().remove(this);
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        if (chatMessages == null) return;
        chatMessages.forEach(this::addMessage);
    }

    public void addMessage(ChatMessage message) {
        message.setChat(this);
        this.chatMessages.add(message);
        this.lastMessage = message;
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
