package ggc.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.Collection;

import java.io.Serializable;

/**
 * Classe Recipe This class presents the behavior of a Recipe.
 * 
 * @author Edson da Veiga 100731
 * @author Sofia Romeiro 98968
 * @version 1.0
 */
public class Recipe implements Serializable {

	private static final long serialVersionUID = 123409192006L;
	private double _alpha;
	private List<Component> _components;

	/**
	 * Constructor.
	 * 
	 * @param alpha
	 */
	public Recipe(double alpha) {
		_alpha = alpha;
		_components = new ArrayList<>();
	}

	public double getAlpha() {
		return _alpha;
	}

	public Collection<Component> getComponents() {
		return Collections.unmodifiableCollection(_components);
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
		}
		return "|" + recipe;
	}
}