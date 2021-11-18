package ggc.core;

import java.io.Serializable;

public abstract class Transaction implements Serializable{

    private int _id;
    private Date _transactionDate;
    private double _baseValue;
    private int _quantity;
    private Product _product;
    private Partner _partner;
    private static final long serialVersionUID = 123402759996L;

    public Transaction(int id, Date transactionDate, double baseValue, int quantity, Product product, Partner partner) {
        _id = id;
        _transactionDate = transactionDate;
        _baseValue = baseValue;
        _quantity = quantity;
        _product = product;
        _partner = partner;
    }

    public abstract boolean isPaid();

    public int getId() { return _id;}
    public Date getTransactionDate() { return _transactionDate;}
    public double getBaseValue() { return _baseValue;}
    public int getQuantity() { return _quantity;}
    public Product getProduct(){ return _product;}
    public Partner getPartner() { return _partner;}
    
    public String getProductType() {
        if (_product instanceof SimpleProduct) { return "Simple";}
        else {return "Aggregate"; }
    }
    
}
