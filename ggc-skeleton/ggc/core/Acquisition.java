package ggc.core;

public class Acquisition extends Transaction {
    
    public Acquisition(int id, int date, double baseValue, int quantity, Product product, Partner partner) {
        super(id, date, baseValue, quantity, product, partner);
    }

    public  boolean isPaid() {
        return true;
    }
    public  int getPaymentDate() {
        return super.getDate();
    }

    public String toString() {
        return "COMPRA|" + super.getId() + "|" + super.getPartner().getKey() + "|"  + super.getProduct().getId() + "|" + super.getQuantity() + "|" + getBaseValue() + "|" + super.getDate();
    }
}
