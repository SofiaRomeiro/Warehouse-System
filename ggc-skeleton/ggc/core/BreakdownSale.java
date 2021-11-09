package ggc.core;

public class BreakdownSale extends Sale{


    public BreakdownSale(int id, int transactionDate, double baseValue, int quantity, Product product, Partner partner) {
        super(id, transactionDate, baseValue, quantity, product, partner);
    } 

    public  boolean isPaid() {
        return true;
    }
    public  int getPaymentDate() {
        return super.getTransactionDate();
    }

    public String toString() {
        return "DESAGREGAÇÃO|" + super.getId() + "|" + super.getPartner().getKey() + "|"  + super.getProduct().getId() + "|" + super.getQuantity() + "|" + getBaseValue() + "|" + super.getValuePaid() + "|" + super.getTransactionDate() + "|" + ((AggregateProduct) super.getProduct()).getRecipe();
    }
    
}
