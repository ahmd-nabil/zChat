package nabil.zchat.services;

import lombok.RequiredArgsConstructor;
import nabil.zchat.domain.Chat;
import nabil.zchat.repositories.ChatRepo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ahmed Nabil
 */
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepo chatRepo;

    public Chat getChatById(Long id) {
        return this.chatRepo.findById(id).get();
    }

    public List<Chat> getAllChatsByUserSubject(String subject) {
        return this.chatRepo.getAllChatsByUserSubject(subject);
    }
}
