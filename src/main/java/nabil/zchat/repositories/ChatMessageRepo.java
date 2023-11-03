package nabil.zchat.repositories;

import nabil.zchat.domain.ChatMessage;

/**
 * @author Ahmed Nabil
 */
public interface ChatMessageRepo {
    ChatMessage save(ChatMessage chatMessage);
}
