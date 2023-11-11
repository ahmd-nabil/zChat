package nabil.zchat.repositories;

import nabil.zchat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Ahmed Nabil
 */
public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {


    @Query("SELECT m FROM ChatMessage m WHERE m.sender.id = :userId or m.receiver.id = :userId")
    List<ChatMessage> findAllMessagesByUserId(@Param("userId") Long userId);

    @Query("SELECT m FROM ChatMessage m WHERE m.sender.subject = :userId or m.receiver.subject = :userSubject")
    List<ChatMessage> findAllMessagesByUserSubject(@Param("userSubject") String userSubject);

    @Query("SELECT m FROM ChatMessage m WHERE m.chat.id = :chatId")
    List<ChatMessage> findAllMessagesByChatId(@Param("chatId") Long chatId);
}
