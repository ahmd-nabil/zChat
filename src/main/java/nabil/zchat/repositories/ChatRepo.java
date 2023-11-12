package nabil.zchat.repositories;

import nabil.zchat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Ahmed Nabil
 */
public interface ChatRepo extends JpaRepository<Chat, Long> {
    @Query("SELECT c FROM Chat c JOIN c.chatUsers u WHERE u.subject = :subject")
    List<Chat> getAllChatsByUserSubject(String subject);
}