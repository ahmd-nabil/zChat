package nabil.zchat.services;

import lombok.RequiredArgsConstructor;
import nabil.zchat.domain.Chat;
import nabil.zchat.domain.ChatUser;
import nabil.zchat.dtos.ChatResponse;
import nabil.zchat.mappers.ChatMapper;
import nabil.zchat.repositories.ChatRepo;
import nabil.zchat.repositories.ChatUserRepo;
import org.springframework.security.core.Authentication;
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
    private final ChatUserRepo chatUserRepo;

    public ChatResponse getChatById(Long id) {
        return this.chatMapper.toChatResponse(this.chatRepo.findById(id).orElseThrow());
    }

    public List<ChatResponse> getAllChatsByUserSubject(Authentication authentication) {
        String subject = authentication.getName();
        return this.chatRepo.getAllChatsByUserSubject(subject).stream().map(this.chatMapper::toChatResponse).toList();
    }

    public ChatResponse addNewChat(List<String> chatUsersSubjects) {
        List<ChatUser> chatUsers = chatUserRepo.findAllBySubject(chatUsersSubjects);
        if(chatUsers.isEmpty()) throw new RuntimeException("1 user required at least.");
        Chat newChat = Chat.builder().chatUsers(chatUsers).build();
        return chatMapper.toChatResponse(chatRepo.save(newChat));
    }
}
