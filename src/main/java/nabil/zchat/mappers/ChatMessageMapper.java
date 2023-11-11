package nabil.zchat.mappers;

import nabil.zchat.domain.ChatMessage;
import nabil.zchat.dtos.ChatMessageRequestDto;
import nabil.zchat.dtos.SimpleChatMessageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Ahmed Nabil
 */
@Mapper
public interface ChatMessageMapper {
    ChatMessage toChatMessage(ChatMessageRequestDto dto);

    @Mapping(target = "senderId", source = "chatMessage.sender.id")
    @Mapping(target = "receiverId", source = "chatMessage.receiver.id")
    @Mapping(target = "chatId", source = "chatMessage.chat.id")
    @Mapping(target = "senderSubject", source = "chatMessage.sender.subject")
    @Mapping(target = "receiverSubject", source = "chatMessage.receiver.subject")
    SimpleChatMessageResponse toSimpleChatMessageResponseDto(ChatMessage chatMessage);
}
