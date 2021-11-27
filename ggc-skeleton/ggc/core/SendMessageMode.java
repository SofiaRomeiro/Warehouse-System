package ggc.core;

import java.util.Collection;

public interface SendMessageMode {
    void sendMessage(Collection<Notification> notifications);
}
