package ggc.core;

import java.io.Serializable;

public class SaleByCredit extends Sale implements Serializable{
    private boolean _paid;
    private static final long serialVersionUID = 123402752006L;

    public SaleByCredit(int id, Date transactionDate, double baseValue, int quantity, Product product, Partner partner) {
        super(id, transactionDate, baseValue, quantity, product, partner);
        _paid = false;
    } 

    public boolean isPaid() {
        return _paid;
    }

    public void pay() {
        _paid = true;
    }

    public double getValuePaid() { return super.getValuePaid(); }

    public String toString() {
        if (super.getValueToBePaid() == 0) {
            setValuePaid(super.getBaseValue());
        }   

        String message = "VENDA|" + super.getId() + "|" + super.getPartner().getKey() + "|"  + super.getProduct().getId() + "|" + super.getQuantity() + "|" + Math.round(getBaseValue()) + "|" + Math.round(super.getValueToBePaid()) + "|" + super.getTransactionDate().getDeadlinePayment();
        if (isPaid())
            return message + "|" + super.getTransactionDate().getPaymentDate();
        return message;
    }

    
}
