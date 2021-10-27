package ggc.core.Product;

import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.Locale;
import java.text.Collator;

public class Recipe {

	private double _alpha;
	private List<Component> _components;

	public Recipe(double alpha) {
		_alpha = alpha;
	}

	public double getAlpha() { return _alpha; }

	public List<Component> getComponents() {
		return _components;
	}

	@Override
	public String toString() {
		return "|";
	}
}