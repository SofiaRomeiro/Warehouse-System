package ggc.core.Product;


import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.Locale;
import java.text.Collator;


public class Component {

	private int _quantity;
	private Product _product;

	public Component(int quantity, Product product) {
		_quantity = quantity;
		_product = product;
	}

	public String toString() {
		return _product.getId() + ":" + _quantity;
	}

	

	





}