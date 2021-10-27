package ggc.core;

import java.io.Serializable;
import java.util.LinkedList;

public class Notifications implements Serializable {

	LinkedList<String> _notifications = new LinkedList<>();

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