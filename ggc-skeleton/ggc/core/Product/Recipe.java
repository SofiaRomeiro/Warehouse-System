package ggc.core.Product;

import java.util.Comparator;
import java.util.List;
<<<<<<< HEAD
import java.util.LinkedList;
import java.util.ArrayList;
=======
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
>>>>>>> 16e6d11b1cb94f9f6ec70ec640f80ece2659e9dd
import java.util.Locale;
import java.text.Collator;

import ggc.core.Product.Component;

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

	@Override
	public String toString() {
<<<<<<< HEAD
		String components;

		for (Component c : _components) {
			components.add(c.getId() + ":" + c.getQuantity());
		}

		return components;
=======
		String recipe = new String();

		Iterator<Component> iter = _components.iterator(); 
		while (iter.hasNext()) {
			recipe += iter.next().toString();
			if (iter.hasNext())
				recipe += "#";
			break; 
		}

		return "|" + recipe;
>>>>>>> 16e6d11b1cb94f9f6ec70ec640f80ece2659e9dd
	}
}