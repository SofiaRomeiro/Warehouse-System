package ggc.core;

public class AggregateProduct extends Product {

    private Recipe _recipe;
    private double _alpha;

    public AggregateProduct(String id, Recipe recipe, double alpha) {
        super(id);
        _recipe = recipe;
        _alpha = alpha;
    }

    public Recipe getRecipe() {
        return _recipe;
    }

    public double getAlpha() {
        return _alpha;
    }

    public String toString() {
        return super.toString() + _recipe.toString();
    }

    public boolean isSimple() {
        return false;
    }
}
