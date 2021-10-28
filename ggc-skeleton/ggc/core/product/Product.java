package ggc.core.product;

//import exceptions

import ggc.core.Notifications;
import ggc.core.Partner;
import ggc.core.product.Batch;

import java.io.Serializable;

import java.lang.Math;

import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;
import java.util.TreeMap;
import java.util.Locale;
import java.text.Collator;


public abstract class Product implements Serializable{
    
	private String _id;
	private double _maxPrice;
	private double _lowestPrice;
	private double _highestPrice;
	private int _currentQuantity;
	private ArrayList<Batch> _batches;

	public Product(String id, double maxPrice, double lowestPrice, double highestPrice, int currentQuantity) {
		_id = id;
		_maxPrice = maxPrice;
		_lowestPrice = lowestPrice;
		_highestPrice = highestPrice;
		_currentQuantity = currentQuantity;
		_batches = new ArrayList<>();
	}

	public Product (String id) {
		this (id, 0, 0, 0, 0);
	}

	//public abstract double checkQuantity(int quantity, Partner p);

	public String getId() { return _id; }



	public double getMaxPrice() { return _maxPrice; }

	public int getCurrentQuantity() { return _currentQuantity; } 

	private void updateCurrentQuantity(int quantity) {_currentQuantity += quantity; }

	private void updatePrices(double price) {
		if (price > _maxPrice) { 
			_maxPrice = price;
			_highestPrice = price;
		}

		if (price < _lowestPrice) {
			_lowestPrice = price;
		}
	}

	public void addNewBatch(Batch b) {
		updateCurrentQuantity(b.getQuantity());
		updatePrices(b.getPrice());
		_batches.add(b);
	}

	public ArrayList<Batch> getAllBatches() {

		/*ArrayList<String> batches = new ArrayList<>();
		batches = _batches;

		for (Batch b : batches) {
			batches.add(b);
		}*/

		Collections.sort(_batches, new BatchesComparator());

		return _batches;
	}

	// criar um extra()

	protected final static class BatchesComparator implements Comparator<Batch> {

	    @Override
	    public int compare(Batch b1, Batch b2) {

			if (b1.getId().compareTo(b2.getId()) != 0) {
				return b1.getId().compareTo(b2.getId());
			}

			else {

				if (b1.getPartnerKey().compareTo(b2.getPartnerKey()) != 0) {
					return b1.getPartnerKey().compareTo(b2.getPartnerKey());
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



	@Override
    public String toString() {
        return _id + "|" + Math.round(_maxPrice) + "|" + _currentQuantity;
    }

	//public abstract String toString(Product p);
}
