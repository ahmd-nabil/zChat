package nabil.zchat.services;

import nabil.zchat.dtos.ChatMessageRequestDto;

/**
 * @author Ahmed Nabil
 */
public interface ChatService {

    void sendMessage(ChatMessageRequestDto dto);
}
