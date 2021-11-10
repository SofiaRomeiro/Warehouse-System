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
public class Notification implements Serializable {

	String _notificationType;
	Product _product;
	private static final long serialVersionUID = 202109190000L;

	/**
	 * Constructor.
	 */
	public Notification(String type, Product product) {
		_notificationType = type;
		_product = product;
	}

	/*public void addNotification(String notification) {
		_notifications.add(notification);
	} 

	public void clearNotifications() {
		_notifications.clear();
	} */

	/*
	public String displayNotifications() {
		String displayText = toString();
		clearNotifications();
		return displayText;
	} */

	@Override
	public String toString() {
		return _notificationType +"|" + _product.getId() + "|" + Math.round(_product.getLowestPrice());
	}
}