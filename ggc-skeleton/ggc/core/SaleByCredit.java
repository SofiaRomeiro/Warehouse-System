package ggc.core;

public class SaleByCredit extends Sale {
    
    public SaleByCredit(int id, Date transactionDate, double baseValue, int quantity, Product product, Partner partner) {
        super(id, transactionDate, baseValue, quantity, product, partner);
    } 

    public  boolean isPaid() {
        return true;
    }

    public String toString() {
        return "VENDA|" + super.getId() + "|" + super.getPartner().getKey() + "|"  + super.getProduct().getId() + "|" + super.getQuantity() + "|" + Math.round(getBaseValue()) + "|" + Math.round(super.getValuePaid()) + "|" + super.getTransactionDate().getDealinePayment() + "|" + super.getTransactionDate().getPaymentDate();
    }
}
