package ggc.core;

public abstract class Transaction {

    private int _id;
    private int _date;
    private double _baseValue;
    private int _quantity;
    private Product _product;
    private Partner _partner;

    public Transaction(int id, int date, double baseValue, int quantity, Product product, Partner partner) {
        _id = id;
        _date = date;
        _baseValue = baseValue;
        _quantity = quantity;
        _product = product;
        _partner = partner;
    }

    public abstract boolean isPaid();
    public abstract int getPaymentDate();

    public int getId() { return _id;}
    public int getDate() { return _date;}
    public double getBaseValue() { return _baseValue;}
    public int getQuantity() { return _quantity;}
    public Product getProduct(){ return _product;}
    public Partner getPartner() { return _partner;}
    
}
