package ggc.core.product;

//import exceptions

import ggc.core.Notifications;
import ggc.core.Partner;
import ggc.core.product.Batch;

import java.io.Serializable;

import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Locale;
import java.text.Collator;

public abstract class Product {
    
	private String _id;
	private float _maxPrice;
	private float _lowestPrice;
	private float _highestPrice;
	private int _currentQuantity;
	private Map<String, Batch> _batches;

	public Product(String id, float maxPrice, float lowestPrice, float highestPrice, int currentQuantity) {
		_id = id;
		_maxPrice = maxPrice;
		_lowestPrice = lowestPrice;
		_highestPrice = highestPrice;
		_currentQuantity = currentQuantity;
		_batches = new TreeMap<>();
	}

	public Product (String id) {
		this (id, 0, 0, 0, 0);
	}

	//public abstract float checkQuantity(int quantity, Partner p);

	public String getId() { return _id; }

	public float getMaxPrice() { return _maxPrice; }

	public int getCurrentQuantity() { return _currentQuantity; } 

	public void addNewBatch(Batch b) {
		_batches.put(b.getPartnerKey(), b);
	}

	public TreeMap<String, String> getAllBatches() {

		TreeMap<String, String> batches = new TreeMap<>();

		for (Batch b : _batches.values()) {
			batches.put(b.getPartnerKey().toLowerCase(), b.toString());
		}

		return batches;
	}

	// criar um extra()

	@Override
    public String toString() {
        return _id + "|" + _maxPrice + "|" + _currentQuantity;
    }

	//public abstract String toString(Product p);
}
