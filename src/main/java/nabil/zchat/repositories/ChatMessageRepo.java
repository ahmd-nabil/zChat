package nabil.zchat.repositories;

import nabil.zchat.entities.ChatMessage;

/**
 * @author Ahmed Nabil
 */
public interface ChatMessageRepo {
    ChatMessage save(ChatMessage chatMessage);
}
