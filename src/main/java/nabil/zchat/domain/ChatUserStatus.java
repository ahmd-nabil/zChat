package nabil.zchat.domain;

import java.time.LocalDateTime;

/**
 * @author Ahmed Nabil
 */
public record ChatUserStatus(String subject, boolean isOnline, LocalDateTime lastSeen) {}

