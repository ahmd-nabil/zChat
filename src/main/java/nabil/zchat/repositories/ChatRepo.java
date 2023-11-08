package nabil.zchat.repositories;

import nabil.zchat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ahmed Nabil
 */
public interface ChatRepo extends JpaRepository<Chat, Long> {
}