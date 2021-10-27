package ggc.core;

import java.util.Comparator;
import java.util.Locale;
import java.text.Collator;
import ggc.core.notification;


public class Partner {

    public final static Comparator<Partner> KEY_COMPARATOR =new KeyComparator();

    private static class KeyComparator implements Comparator<Partner>{
        @Override
        public int compare(Partner p1, Partner p2){
            Collator collator = Collator.getInstance(Locale.getDefault());
            return collator.compare(p1.getkey(), p2.getKey());
        }
    }
    
    private String _key;
    private String _name;
    private String _address;

    //temos de cria enums para os estatutos
    private String _status;
    private double _points;
    private float _purchases;
    private float _sales;
    private float _paidSales;
    private Notification _notifications;
    

    public Partner (String key, String name, String address, String _status, double points, float purchases, float sales, float paidSales){
        _key = key;
        _name = name;
        _address = address;
        _status = "NORMAL";
        _points = points;
        _purchases = purchases;
        _sales = sales;
        _paidSales = paidSales;
        _notifications = new Notification();
    }

    public Partner (String key, String name, String address){
        this(key, name, address, "NORMAL", 0, 0, 0, 0);
    }

    public String getKey() { return _key; }

    @Override
    public String toString() {
        return _key + "|" + _name + "|" + _status + "|" + _points + "|" + _purchases + "|" + _sales + "|" + _paidSales;
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof Partner) {
            Partner partener = (Partner) o;
            return _key.equals(partener.getKey());
        }
        return false;
    }


}
