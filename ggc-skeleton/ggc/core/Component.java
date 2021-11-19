package ggc.core;

import java.io.Serializable;

public class Component implements Serializable {

	private static final long serialVersionUID = 123409186756L;
	private int _quantity;
	private Product _product;

	/**
	 * Constructor.
	 * 
	 * @param quantity
	 * @param product
	 */
	public Component(int quantity, Product product) {
		_quantity = quantity;
		_product = product;
	}

	public String getId() {
		return _product.getId();
	}

	public Product getProduct() {
		return _product;
	}

	public int getQuantity() {
		return _quantity;
	}

	public String toString() {
		return _product.getId() + ":" + _quantity;
	}
}