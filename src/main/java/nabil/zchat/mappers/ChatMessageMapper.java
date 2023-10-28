package nabil.zchat.mappers;

import nabil.zchat.dtos.ChatMessageRequestDto;
import nabil.zchat.entities.ChatMessage;
import org.mapstruct.Mapper;

/**
 * @author Ahmed Nabil
 */
@Mapper
public interface ChatMessageMapper {
    ChatMessage toChatMessage(ChatMessageRequestDto dto);
}
