package nabil.zchat.repositories;

import nabil.zchat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ahmed Nabil
 */
public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {
}
