package ggc.core;

//import ggc.core.Partner;
//import ggc.core.Product;

import java.util.Comparator;

import java.io.Serializable;

public class Batch implements Serializable {

	private static final long serialVersionUID = 2021091927689L;
	private double _price;
	private int _quantity;
	private Product _product;
	private Partner _partner;

	public Batch(double price, int quantity, Product product, Partner partner) {
		_price = price;
		_quantity = quantity;
		_product = product;
		_partner = partner;
	}

	public double getPrice() {return _price; }

	public int getQuantity() { return _quantity; }

	public String toString() {
		return _product.getId() + "|" + _partner.getKey() + "|" + Math.round(_price) + "|" + _quantity;
	}

	public String getPartnerKey() {
		return _partner.getKey();
	}

	public String getId() {return _product.getId(); }


	
}