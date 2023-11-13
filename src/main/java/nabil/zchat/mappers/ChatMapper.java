package nabil.zchat.mappers;

import nabil.zchat.domain.Chat;
import nabil.zchat.dtos.ChatResponse;
import org.mapstruct.Mapper;

/**
 * @author Ahmed Nabil
 */

@Mapper(uses = {ChatMessageMapper.class})
public interface ChatMapper {
    ChatResponse toChatResponse(Chat chat);
}
