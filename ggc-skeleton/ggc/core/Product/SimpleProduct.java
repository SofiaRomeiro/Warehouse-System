package ggc.core.Product;

public class SimpleProduct extends Product {

    public SimpleProduct(String id) {
        super(id);
    }

    public String toString(Product p) {
        // ID|MAXPRICE|STOCKATUAL
        return p.getId() + "|" + p.getMaxPrice() + "|" + p.getCurrentQuantity();
    }
    
}
