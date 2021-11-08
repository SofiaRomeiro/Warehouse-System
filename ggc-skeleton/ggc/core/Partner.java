package ggc.core;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

import ggc.core.Notifications;

/**
 * Classe Partner
 * This class presents the behavior of a Partner.
 * 
 * @author Edson da Veiga 100731
 * @author Sofia Romeiro 98968
 * @version 1.0
 */
public class Partner implements Serializable {

    private static final long serialVersionUID = 207564192006L;
    
    private String _key;
    private String _name;
    private String _address;
    private String _status;
    private float _points;
    private float _purchases;
    private float _sales;
    private float _paidSales;
    private Notifications _notifications;
    private List<Batch> _batches;
    
    /**
     * Constructor.
     * 
     * @param key
     * @param name
     * @param address
     */
    public Partner (String key, String name, String address){
        _key = key;
        _name = name;
        _address = address;
        _status = "NORMAL";
        _points = 0;
        _purchases = 0;
        _sales = 0;
        _paidSales = 0;
        _notifications = new Notifications();
        _batches = new ArrayList<>();
    }

    public String getKey() { return _key; }
    
    /**
	 * Returns the string representing a Partner.
	 * id|nome|enderecco|estatuto|pontos|valor-compras|valor-vendas-efectuadas|valor-vendas-pagas
	 * 
	 * @Override
	 * @return a string representing a Partner.
	 */    
    public String toString() {
        return _key + "|" + _name + "|" + _address + "|" + _status + "|" + Math.round (_points) + "|" + Math.round (_purchases) + "|" + Math.round (_sales) + "|" + Math.round (_paidSales);
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof Partner) {
            Partner partener = (Partner) o;
            return _key.toLowerCase().equals(partener.getKey().toLowerCase());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return _key.toLowerCase().hashCode();
    }

    



}
