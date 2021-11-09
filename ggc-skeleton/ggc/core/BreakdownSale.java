package ggc.core;

import java.util.Map;
import java.util.HashMap;

public class BreakdownSale extends Sale{

    private Map<String, Batch> _batches;
    public BreakdownSale(int id, int transactionDate, double baseValue, int quantity, Product product, Partner partner) {
        super(id, transactionDate, baseValue, quantity, product, partner);
        _batches = new HashMap<>();

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
