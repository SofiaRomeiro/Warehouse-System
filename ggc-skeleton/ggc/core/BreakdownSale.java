package ggc.core;

import java.util.ArrayList;
import java.util.List;

public class BreakdownSale extends Sale{

    private List<String> _componentsString;

    public BreakdownSale(int id, Date transactionDate, double baseValue, int quantity, Product product, Partner partner) {
        super(id, transactionDate, baseValue, quantity, product, partner);
        _componentsString = new ArrayList<>();
        
    } 

    public  boolean isPaid() {
        return true;
    }

    public void setComponentsString(List<String> componentsString) {
        _componentsString = componentsString;
    }

    
  
    public String toString() {

        String recipe = new String();

        for (int i = 0; i < _componentsString.size(); i++) {
            recipe += _componentsString.get(i);
            if (i != _componentsString.size() - 1)
                recipe += "#";
        }
       
        return "DESAGREGAÇÃO|" + super.getId() + "|" + super.getPartner().getKey() + "|"  + super.getProduct().getId() + "|" + super.getQuantity() + "|" + Math.round(getBaseValue()) + "|" + Math.round(super.getValuePaid()) + "|" + super.getTransactionDate().getPaymentDate() + "|" + recipe;
    }
    
}
