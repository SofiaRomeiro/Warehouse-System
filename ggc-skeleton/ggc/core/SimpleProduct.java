package ggc.core;

import java.util.List;

public class SimpleProduct extends Product{

    public SimpleProduct(String id) {
        super(id);
    }  

    public SimpleProduct(String id, int quantity, List<Observer> obs) {
        super(id, quantity);
    }

    public String toString() {
        return super.toString();
    }
}
