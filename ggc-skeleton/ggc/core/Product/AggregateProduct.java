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
        String aggregateProduct =  p.getId() + "|" + p.getMaxPrice() + "|" 
            + p.getCurrentQuantity() + "|" + _recipe.getAlpha();

        List<Component> components = _recipe.getComponents();

        for (Component c : components) {
            aggregateProduct += c.toString() + "#";
        }

    }
}
