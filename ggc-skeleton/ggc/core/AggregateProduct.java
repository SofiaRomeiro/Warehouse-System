package ggc.core;

public class AggregateProduct extends Product {

    private Recipe _recipe;

    public AggregateProduct (String id) {
        super(id);
    }

    public String toString() {     
        return super.toString() + _recipe.toString();

    }
}
