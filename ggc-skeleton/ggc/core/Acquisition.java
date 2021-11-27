package ggc.core;

public class Acquisition extends Transaction implements Label {

    public Acquisition(int id, Date transactionDate, double baseValue, int quantity, Product product, Partner partner) {
        super(id, transactionDate, baseValue, quantity, product, partner, Label.ACQUISITION);
    }

    public boolean isPaid() {
        return true;
    }

    public String toString() {
        return "COMPRA|" + super.getId() + "|" + super.getPartner().getKey() + "|" + super.getProduct().getId() + "|"
                + super.getQuantity() + "|" + Math.round(getBaseValue()) * super.getQuantity() + "|"
                + super.getTransactionDate().getDate();
    }

}
