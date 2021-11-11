package ggc.core;

import java.util.List;

public class SimpleProduct extends Product{

    public SimpleProduct(String id, List<Observer> obs) {
        super(id, obs);
    }  

    public SimpleProduct(String id, int quantity, List<Observer> obs) {
        super(id, quantity, obs);
    }

    public String toString() {
        return super.toString();
    }
}
