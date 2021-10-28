package ggc.core.product;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.text.Collator;

import ggc.core.product.Component;

public class Recipe {

	private double _alpha;
	private List<Component> _components;

	public Recipe(double alpha) {
		_alpha = alpha;
		_components = new ArrayList<>();
	}

	public double getAlpha() { return _alpha; }

	public List<Component> getComponents() {
		return _components;
	}

	public void addComponent(Component c) {
		_components.add(c);
	}

	@Override
	public String toString() {

		String recipe = new String();

		Iterator<Component> iter = _components.iterator(); 
		while (iter.hasNext()) {
			recipe += iter.next().toString();
			if (iter.hasNext())
				recipe += "#";
			break; 
		}

		return "|" + recipe;
	}
}