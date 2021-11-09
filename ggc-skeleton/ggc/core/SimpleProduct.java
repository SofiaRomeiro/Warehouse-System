package ggc.core;

public class SimpleProduct extends Product{

    public SimpleProduct(String id) {
        super(id);
    }  

    public SimpleProduct(String id, int quantity) {
        super(id, quantity);
    }

    public String toString() {
        return super.toString();
    }
}
