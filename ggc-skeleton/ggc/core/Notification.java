package ggc.core;

import java.io.Serializable;

/**
 * Classe Notifications This class presents the behavior of a Notification.
 * 
 * @author Edson da Veiga 100731
 * @author Sofia Romeiro 98968
 * @version 1.0
 */
public class Notification implements Serializable {

	private String _notificationType;
	private Product _product;
	private double _price;
	private static final long serialVersionUID = 202109190000L;

	/**
	 * Constructor.
	 */
	public Notification(String type, Product product, double price) {
		_notificationType = type;
		_product = product;
		_price = price;
	}

	@Override
	public String toString() {
		return _notificationType + "|" + _product.getId() + "|" + Math.round(_price);
	}
}