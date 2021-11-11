package ggc.core;

import java.util.List;

public class AggregateProduct extends Product {

    private Recipe _recipe;

    public AggregateProduct (String id, List<Observer> obs, Recipe recipe) {
        super(id, obs);
        _recipe = recipe;
    }

    public Recipe getRecipe() { return _recipe; }

    public String toString() {     
        return super.toString() + _recipe.toString();

    }
}
