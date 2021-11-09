package ggc.core;

public class SaleByCredit extends Sale {

    private int _paymentDate;
    private int _deadlineDate;
    
    public SaleByCredit(int id, int transactionDate, double baseValue, int quantity, Product product, Partner partner) {
        super(id, transactionDate, baseValue, quantity, product, partner);
    } 

    public  boolean isPaid() {
        return true;
    }
    public  int getPaymentDate() {
        return _paymentDate;
    }

    public String toString() {
        return "VENDA|" + super.getId() + "|" + super.getPartner().getKey() + "|"  + super.getProduct().getId() + "|" + super.getQuantity() + "|" + getBaseValue() + "|" + super.getValuePaid() + "|" + _deadlineDate + "|" + _paymentDate;
    }
}
