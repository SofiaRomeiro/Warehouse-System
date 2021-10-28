package ggc.core.product;

import ggc.core.Partner;
import ggc.core.product.Product;

public class Batch {

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

	public String toString() {
		return _product.getId() + "|" + _partner.getKey() + "|" + _price + "|" + _quantity;
	}

	public String getPartnerKey() {
		return _partner.getKey();
	}
}