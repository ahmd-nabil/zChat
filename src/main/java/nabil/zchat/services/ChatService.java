package nabil.zchat.services;

import lombok.RequiredArgsConstructor;
import nabil.zchat.dtos.ChatResponse;
import nabil.zchat.mappers.ChatMapper;
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
    private final ChatMapper chatMapper;

    public ChatResponse getChatById(Long id) {
        return this.chatMapper.toChatResponse(this.chatRepo.findById(id).orElseThrow());
    }

    public List<ChatResponse> getAllChatsByUserSubject(String subject) {
        return this.chatRepo.getAllChatsByUserSubject(subject).stream().map(this.chatMapper::toChatResponse).toList();
    }
}
