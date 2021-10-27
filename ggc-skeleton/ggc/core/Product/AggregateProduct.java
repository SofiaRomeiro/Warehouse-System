package ggc.core.Product;

import ggc.core.Product.Component;
import ggc.core.Product.Recipe;

import java.util.List;

public class AggregateProduct extends Product {

    private Recipe _recipe;

    public AggregateProduct (String id) {
        super(id);
    }

    public String toString() {     
        return super.toString() + _recipe.toString();

    }
}
