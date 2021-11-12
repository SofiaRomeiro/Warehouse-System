package ggc.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import java.io.Serializable;


/**
 * Classe Partner
 * This class presents the behavior of a Partner.
 * 
 * @author Edson da Veiga 100731
 * @author Sofia Romeiro 98968
 * @version 1.0
 */
public class Partner implements Serializable, Observer {

    private static final long serialVersionUID = 207564192006L;
    
    private String _key;
    private String _name;
    private String _address;
    private StatusContext _status;
    private float _purchases;
    private float _sales;
    private float _paidSales;
    private List<Notification> _notifications;
    private List<Batch> _batches;
    private List<Transaction> _transactions;
    
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
        _status = new StatusContext();
        _purchases = 0;
        _sales = 0;
        _paidSales = 0;
        _notifications = new ArrayList<>();
        _batches = new ArrayList<>();
        _transactions = new ArrayList<>();
    }

    public String getKey() { return _key; }

    public List<Batch> getAllBatches() { return _batches; }

    public List<Transaction> getAllTransactions() { return _transactions;}

    public void addBatch(Batch b) { _batches.add(b); }

    public void addTransation(Transaction t) { 
        _transactions.add(t);
        if (t instanceof Acquisition) {
            _purchases += t.getBaseValue() * t.getQuantity();
        } 
        else if (t instanceof SaleByCredit) {
            _sales += t.getBaseValue() * t.getQuantity();
        } 
    }
    
    /**
	 * Returns the string representing a Partner.
	 * id|nome|enderecco|estatuto|pontos|valor-compras|valor-vendas-efectuadas|valor-vendas-pagas
	 * 
	 * @Override
	 * @return a string representing a Partner.
	 */    
    public String toString() {
        return _key + "|" + _name + "|" + _address + "|" + _status.statusToString() + "|" + _status.getPoints() + "|" + Math.round (_purchases) + "|" + Math.round (_sales) + "|" + Math.round (_paidSales);
    }

    public void update(Notification notification) {
        _notifications.add(notification);        
    }

    private void clearNotifications() {
        _notifications.clear();
    }

    public List<String> getAllNotifications() {

        ArrayList<String> notsToString = new ArrayList<>();

        for (Notification n : _notifications) {
            notsToString.add(n.toString());
        }           
        clearNotifications();

        return notsToString;
    }

    public void removeEmptyBatch() {
        Iterator<Batch> iter = _batches.iterator();
        while (iter.hasNext()) {
            if (iter.next().getQuantity() == 0) 
                iter.remove();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Partner) {
            Partner p = (Partner) o;
            return _key.toLowerCase().equals(p.getKey().toLowerCase());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return _key.toLowerCase().hashCode();
    }
}
