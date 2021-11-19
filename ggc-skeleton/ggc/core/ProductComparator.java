package ggc.core;

import java.util.Comparator;

public class ProductComparator implements Comparator<Product> {
	@Override
	public int compare(Product p1, Product p2) {
		return p1.getId().toLowerCase().compareTo(p2.getId().toLowerCase());
	}
}