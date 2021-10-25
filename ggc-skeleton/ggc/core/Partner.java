package ggc.core;

public class Partner {
    
    private String _id;
    private String _name;
    private String _address;

    //temos de cria enums para os estatutos
    private String _status;
    private double _points;
    private float _purchases;
    private float _sales;
    private float _paidSales;
    

    public Partner (String id, String name, String address, String _status, double points, float purchases, float sales, float paidSales){
        _id = id;
        _name = name;
        _address = address;
        _status = "NORMAL";
        _points = points;
        _purchases = purchases;
        _sales = sales;
        _paidSales = paidSales;
    }

    public Partner (String id, String name, String address){
        this(id, name, address, "NORMAL", 0, 0, 0, 0);
    }

    


}
