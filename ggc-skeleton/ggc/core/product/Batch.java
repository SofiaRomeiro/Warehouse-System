package ggc.core.product;

public class Batch {

	private double _price;
	private int _quantity;
	private Product _product;
	private Partner _partner;

	public Batch(double price, int quantity, Product product, Partner partner) {
		_price = price;
		_quantity = quantity;
		_product = productKey;
		_partner = partnerKey;
	}

	public String toString() {
		return _product.getId() + "|" + _partner.getId() + "|" + _price + "|" + _quantity;
	}




}