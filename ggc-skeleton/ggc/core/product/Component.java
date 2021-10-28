package ggc.core.product;


import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.Locale;
import java.io.Serializable;
import java.text.Collator;


public class Component implements Serializable{

	private static final long serialVersionUID = 123409186756L;
	private int _quantity;
	private Product _product;

	public Component(int quantity, Product product) {
		_quantity = quantity;
		_product = product;
	}

	public String getId() { return _product.getId(); }

	public int getQuantity() { return _quantity; }

	public String toString() {
		return _product.getId() + ":" + _quantity;
	}

	

	





}