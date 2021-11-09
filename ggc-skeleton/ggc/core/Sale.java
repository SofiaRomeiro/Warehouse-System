package ggc.core;

public abstract class Sale extends Transaction {

    private double _valuePaid;
    public Sale(int id, int date, double baseValue, int quantity, Product product, Partner partner) {
        super(id, date, baseValue, quantity, product, partner);
    } 
    
    public double getValuePaid() { return _valuePaid;}
    
}
