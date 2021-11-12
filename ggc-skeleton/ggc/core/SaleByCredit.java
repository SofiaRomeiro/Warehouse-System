package ggc.core;

import java.io.Serializable;

public class SaleByCredit extends Sale implements Serializable{
    
    private static final long serialVersionUID = 123402752006L;

    public SaleByCredit(int id, Date transactionDate, double baseValue, int quantity, Product product, Partner partner) {
        super(id, transactionDate, baseValue, quantity, product, partner);
    } 

    public  boolean isPaid() {
        return true;
    }

    public String toString() {
        return "VENDA|" + super.getId() + "|" + super.getPartner().getKey() + "|"  + super.getProduct().getId() + "|" + super.getQuantity() + "|" + Math.round(getBaseValue()) + "|" + Math.round(super.getValuePaid()) + "|" + super.getTransactionDate().getDeadlinePayment();
    }

    
}
