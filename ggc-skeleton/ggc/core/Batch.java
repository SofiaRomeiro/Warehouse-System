package ggc.core;

import java.io.Serializable;

/**
 * Classe Batch
 * This class presents the behavior of a Batch.
 * 
 * @author Edson da Veiga 100731
 * @author Sofia Romeiro 98968
 * @version 1.0
 */
public class Batch implements Serializable {

	private static final long serialVersionUID = 2021091927689L;
	private double _price;
	private int _quantity;
	private Product _product;
	private Partner _partner;

	/**
	 * Constructor.
	 * 
	 * @param price
	 * @param quantity
	 * @param product
	 * @param partner
	 */
	public Batch(double price, int quantity, Product product, Partner partner) {
		_price = price;
		_quantity = quantity;
		_product = product;
		_partner = partner;
	}

	/**
	 * Returns the prouct price.
	 * 
	 * @return
	 */
	public double getPrice() {return _price; }

	/**
	 * Returns the prouct quantity.
	 * 
	 * @return
	 */
	public int getQuantity() { return _quantity; }

	public void decreaseQuantity(int quantity) {
		_quantity -= quantity;
	}

	public void setQuantity(int quantity) { _quantity = quantity; }

	/**
	 * Returns the string representing a Batch.
	 * idProduto|idParceiro|preco|stock-actual
	 * 
	 * @return a string representing a Batch.
	 */
	public String toString() {
		return _product.getId() + "|" + _partner.getKey() + "|" + Math.round(_price) + "|" + _quantity;
	}

	/**
	 * Returns the Partener key.
	 * 
	 * @return
	 */
	public String getPartnerKey() {
		return _partner.getKey();
	}

	public Partner getPartner() { return _partner; }

	/**
	 * Returns the Batch id.
	 * 
	 * @return
	 */
	public String getId() { return _product.getId(); }

	public Product getProduct() { return _product; }
	
}