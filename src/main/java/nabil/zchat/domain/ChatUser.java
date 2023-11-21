package nabil.zchat.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.NaturalId;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Ahmed Nabil
 */
@Entity
@Data
@Builder
public class ChatUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NaturalId
    private String email;

    @NaturalId
    private String subject;     // should be set first time the user logs in

    @ManyToMany(mappedBy = "chatUsers")
    @Builder.Default
    @JsonIgnore
    private List<Chat> chats = new ArrayList<>();

    private LocalDateTime lastSeen;

    @JsonProperty(value = "isOnline")
    private boolean isOnline;

    public ChatUser(Long id, String name, String email, String subject, List<Chat> chats, LocalDateTime lastSeen, boolean isOnline) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.setChats(chats);
        this.lastSeen = lastSeen;
        this.isOnline = isOnline;
    }

    public ChatUser() {
        this.chats = new ArrayList<>();
    }

    public void setChats(List<Chat> chats) {
        if(chats == null) return;
        chats.forEach(chat -> chat.addChatUser(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatUser chatUser = (ChatUser) o;
        return Objects.equals(email, chatUser.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "ChatUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", chats=" + chats +
                '}';
    }
}
