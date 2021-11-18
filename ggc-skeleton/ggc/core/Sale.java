package ggc.core;

import java.io.Serializable;

public abstract class Sale extends Transaction implements Serializable {

    private static final long serialVersionUID = 123416392006L;
    private double _valuePaid;

    public Sale(int id, Date transactionDate, double baseValue, int quantity, Product product, Partner partner) {
        super(id, transactionDate, baseValue, quantity, product, partner);
        _valuePaid = 0;
    } 
    
    public void setValuePaid(Double value){
        _valuePaid = value;
    }

    public double getBaseValue() { return super.getBaseValue(); }

    public double getValuePaid() { return _valuePaid;}

    public double getValueToBePaid() { 
        return super.getPartner().getValueToBePaid(super.getTransactionDate(), this ,super.getProductType());
    }
    
    

    /*public double getValueToBePaid() {
        
    }*/
    
}

