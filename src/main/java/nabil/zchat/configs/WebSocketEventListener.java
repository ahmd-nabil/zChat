package nabil.zchat.configs;

import lombok.RequiredArgsConstructor;
import nabil.zchat.domain.ChatUser;
import nabil.zchat.repositories.ChatUserRepo;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Ahmed Nabil
 */
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final ChatUserRepo chatUserRepo;
    private final SimpMessagingTemplate simpMessagingTemplate;
    // todo keep a friendsList of each user to send status only to those who cares
    @EventListener
    private void handleSessionConnected(SessionConnectedEvent event) {
        if(event == null || event.getUser() == null) return;
        String connectUserSubject = event.getUser().getName();
        ChatUser connectedUser = chatUserRepo.findBySubject(connectUserSubject).orElseThrow();
        connectedUser.setOnline(true);
        connectedUser.setLastSeen(LocalDateTime.now());
        chatUserRepo.save(connectedUser);
        simpMessagingTemplate.convertAndSend("/topic/status", new ChatUserStatus(connectUserSubject, true, LocalDateTime.now()));
    }

    @EventListener
    private void handleSessionDisconnected(SessionDisconnectEvent event) {
        if(event == null || event.getUser() == null) return;
        String disConnectedSubject = event.getUser().getName();
        ChatUser disConnectedUser = chatUserRepo.findBySubject(disConnectedSubject).orElseThrow();
        disConnectedUser.setOnline(false);
        disConnectedUser.setLastSeen(LocalDateTime.now());
        chatUserRepo.save(disConnectedUser);
        simpMessagingTemplate.convertAndSend("/topic/status", new ChatUserStatus(disConnectedSubject, false, LocalDateTime.now()));
    }
    private record ChatUserStatus(String subject, boolean isOnline, LocalDateTime lastSeen) {}
}
