package ggc.core.product;

import ggc.core.product.Component;
import ggc.core.product.Recipe;

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
