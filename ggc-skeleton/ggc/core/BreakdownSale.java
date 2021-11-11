package ggc.core;

public class BreakdownSale extends Sale{

    public BreakdownSale(int id, Date transactionDate, double baseValue, int quantity, Product product, Partner partner) {
        super(id, transactionDate, baseValue, quantity, product, partner);
    } 

    public  boolean isPaid() {
        return true;
    }
  
    public String toString() {
        return "DESAGREGAÇÃO|" + super.getId() + "|" + super.getPartner().getKey() + "|"  + super.getProduct().getId() + "|" + super.getQuantity() + "|" + Math.round(getBaseValue()) + "|" + Math.round(super.getValuePaid()) + "|" + super.getTransactionDate().getPaymentDate() + "|" + ((AggregateProduct) super.getProduct()).getRecipe();
    }
    
}
