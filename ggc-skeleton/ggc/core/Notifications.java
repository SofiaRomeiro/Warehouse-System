package ggc.core;

import java.io.Serializable;
import java.util.List;
import java.util.LinkedList;

/**
 * Classe Notifications
 * This class presents the behavior of a Notification.
 * 
 * @author Edson da Veiga 100731
 * @author Sofia Romeiro 98968
 * @version 1.0
 */
public class Notifications implements Serializable {

	List<String> _notifications;
	private static final long serialVersionUID = 202109190000L;

	/**
	 * Constructor.
	 */
	public Notifications() {
		_notifications = new LinkedList<>();
	}
	public void addNotification(String notification) {
		_notifications.add(notification);
	}

	public void clearNotifications() {
		_notifications.clear();
	}

	public String displayNotifications() {
		String displayText = toString();
		clearNotifications();
		return displayText;
	}

	@Override
	public String toString() {
		String s = new String();
		for (String notification : _notifications) {
			s += notification + "\n";
		}
		return s;
	}
}