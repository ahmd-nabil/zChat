package nabil.zchat.repositories;

import nabil.zchat.domain.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Ahmed Nabil
 */
public interface ChatUserRepo extends JpaRepository<ChatUser, Long> {
    Optional<ChatUser> findBySubject(String subject);

    @Query("select user from ChatUser user where user.subject in :subjects")
    List<ChatUser> findAllBySubject(List<String> subjects);

    Optional<ChatUser> findByEmail(String email);
}
