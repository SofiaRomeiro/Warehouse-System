package ggc.core;

public abstract class Sale extends Transaction {

    private double _valuePaid;

    public Sale(int id, Date transactionDate, double baseValue, int quantity, Product product, Partner partner) {
        super(id, transactionDate, baseValue, quantity, product, partner);
        _valuePaid = 0;
    } 
    
    public void setValuePaid(Double value){
        _valuePaid = value;
    }

    public double getValuePaid() { return _valuePaid;}
    
    

    /*public double getValueToBePaid() {
        
    }*/
    
}

