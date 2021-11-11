package ggc.core;

import java.util.List;

public class AggregateProduct extends Product {

    private Recipe _recipe;

    public AggregateProduct (String id, Recipe recipe) {
        super(id);
        _recipe = recipe;
    }

    public String getRecipe() { return _recipe.toString(); }

    public String toString() {     
        return super.toString() + _recipe.toString();

    }
}
