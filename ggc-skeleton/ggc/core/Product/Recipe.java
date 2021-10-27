package ggc.core.Product;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.text.Collator;

import ggc.core.Product.Component;

public class Recipe {

	private double _alpha;
	private List<Component> _components = new ArrayList<>();

	public Recipe(double alpha, List<Component> components) {
		_alpha = alpha;
		_components = components;
	}

	public double getAlpha() { return _alpha; }

	public List<Component> getComponents() {
		return _components;
	}

	@Override
	public String toString() {
		String recipe;
		for (int i = 0; i < _alpha; i++) {
			if(i != 0)
				recipe += "#";
			recipe += _components[i].toString;
		}
		return "|" + recipe;
	}
}