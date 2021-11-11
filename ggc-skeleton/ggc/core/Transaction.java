package ggc.core;

public abstract class Transaction {

    private int _id;
    private Date _transactionDate;
    private double _baseValue;
    private int _quantity;
    private Product _product;
    private Partner _partner;

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
    
}
