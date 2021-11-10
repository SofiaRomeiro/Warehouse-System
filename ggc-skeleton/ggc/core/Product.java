package ggc.core;


import java.io.Serializable;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

import ggc.core.NotificationType;

/**
 * Classe Product
 * This class presents the behavior of a Product.
 * 
 * @author Edson da Veiga 100731
 * @author Sofia Romeiro 98968
 * @version 1.0
 */
public abstract class Product implements Serializable {
    
	private String _id;
	// _maxPrice ever existed
	private double _maxPrice;
	private double _lowestPrice;
	// _highestPrice is the current maxPrice of existing products
	private double _highestPrice;
	private int _currentQuantity;
	private List<Batch> _batches;
	private Set<Observer> _observers;


	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param maxPrice
	 * @param lowestPrice
	 * @param highestPrice
	 * @param currentQuantity
	 */
	public Product(String id, double maxPrice, double lowestPrice, double highestPrice, int currentQuantity) {
		_id = id;
		_maxPrice = maxPrice;
		_lowestPrice = lowestPrice;
		_highestPrice = highestPrice;
		_currentQuantity = currentQuantity;
		_batches = new ArrayList<>();
		_observers = new HashSet<>();
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 */
	public Product (String id) {
		this (id, 0, 0, 0, 0);
	}

	public Product (String id, int quantity) {
		this (id, 0, 0, 0, quantity);
	}

	public String getId() { return _id; }

	public double getMaxPrice() { return _maxPrice; }

	public double getLowestPrice() { return _lowestPrice; }

	public int getCurrentQuantity() { return _currentQuantity; } 

	private void updateCurrentQuantity(int quantity) {_currentQuantity += quantity; }

	private boolean add(Observer obs) {
		return _observers.add(obs);
	  }
	  
	private boolean remove(Observer obs) {
		return _observers.remove(obs);
	  }

	public void toggleNotifications(Observer obs) {
		if (add(obs))
			return;
		remove(obs);
	}
	
	  // may be public or private
	  private void notifyObservers(Notification notification) {
		for (Observer obs : _observers)
		  obs.update(notification);
	  }

	/**
	 * Update the product prices
	 * 
	 * @param price
	 */
	private void updatePrices(double price) {

		if (price > _maxPrice) { 
			_maxPrice = price;
			_highestPrice = price;
		}

		if (price < _lowestPrice) {
			_lowestPrice = price;			
			Notification brg = new Notification(NotificationType.BARGAIN, this);
			notifyObservers(brg);
		}
	}

	/**
	 * Add a new product batch
	 * 
	 * @param b
	 */
	public void addNewBatch(Batch b) {
		updateCurrentQuantity(b.getQuantity());
		updatePrices(b.getPrice());
		_batches.add(b);
	}

	/**
	 * Returns a list of all Batches order by: 
	 * batch id->partner Key->price->quantity
	 * 
	 * @return
	 */
	public List<Batch> getAllBatches() {

		Collections.sort(_batches, new BatchesComparator());
		return _batches;
	}

	protected final static class BatchesComparator implements Comparator<Batch> {

	    @Override
	    public int compare(Batch b1, Batch b2) {

			if (b1.getId().toLowerCase().compareTo(b2.getId().toLowerCase()) != 0) {
				return b1.getId().toLowerCase().compareTo(b2.getId().toLowerCase());
			}

			else {

				if (b1.getPartnerKey().toLowerCase().compareTo(b2.getPartnerKey().toLowerCase()) != 0) {
					return b1.getPartnerKey().toLowerCase().compareTo(b2.getPartnerKey().toLowerCase());
				} 
				else {

					if (b1.getPrice() != b2.getPrice()) {
						return (int) (b1.getPrice() - b2.getPrice());
					}
					else {
						return (int) (b1.getQuantity() - b2.getQuantity());
					}
				}
			}
	    }
  	}

	/**
	 * Returns the string representing a Product.
	 * idProduto|preco-maximo|stock-actual-total
	 * 
	 * @Override
	 * @return a string representing a Product.
	 */
    public String toString() {
        return _id + "|" + Math.round(_maxPrice) + "|" + _currentQuantity;
    }

	public String newNotification() {
		Notification newNot = new Notification(NotificationType.NEW, this);
		return newNot.toString();
	}

	public String bargainNotification() {
		Notification bargainNot = new Notification(NotificationType.BARGAIN, this);
		return bargainNot.toString();
	}
}
