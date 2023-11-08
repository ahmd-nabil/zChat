package nabil.zchat.repositories;

import nabil.zchat.domain.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Ahmed Nabil
 */
public interface ChatUserRepo extends JpaRepository<ChatUser, Long> {
    Optional<ChatUser> findBySubject(String subject);
}
