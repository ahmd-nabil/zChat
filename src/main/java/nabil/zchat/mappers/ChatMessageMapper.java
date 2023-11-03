package nabil.zchat.mappers;

import nabil.zchat.domain.ChatMessage;
import nabil.zchat.dtos.ChatMessageRequestDto;
import org.mapstruct.Mapper;

/**
 * @author Ahmed Nabil
 */
@Mapper
public interface ChatMessageMapper {
    ChatMessage toChatMessage(ChatMessageRequestDto dto);
}
