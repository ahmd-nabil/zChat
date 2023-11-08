package nabil.zchat.repositories;

import nabil.zchat.domain.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ahmed Nabil
 */
public interface ChatUserRepo extends JpaRepository<ChatUser, Long> {
}
