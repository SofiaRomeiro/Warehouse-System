package ggc.core;

import java.io.Serializable;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.util.Iterator;

/**
 * Classe Product This class presents the behavior of a Product.
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
	private List<Batch> _batches = new ArrayList<>();
	private List<Observer> _observers;

	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param maxPrice
	 * @param lowestPrice
	 * @param highestPrice
	 * @param currentQuantity
	 * @param observers
	 */
	public Product(String id, double maxPrice, double lowestPrice, double highestPrice, int currentQuantity) {
		_id = id;
		_maxPrice = maxPrice;
		_lowestPrice = lowestPrice;
		_highestPrice = highestPrice;
		_currentQuantity = currentQuantity;
		_observers = new ArrayList<>();
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 */
	public Product(String id) {
		this(id, 0, 0, 0, 0);
	}

	public Product(String id, int quantity) {
		this(id, 0, 0, 0, quantity);
	}

	public String getId() {
		return _id;
	}

	public double getMaxPrice() {
		return _maxPrice;
	}

	public double getLowestPrice() {
		return _lowestPrice;
	}

	public String getObserversToString() {
		String s = new String();
		for (Observer o : _observers) {
			Partner p = (Partner) o;
			s += "| " + p.getKey() + " | ";
		}
		return s;
	}

	public double getBreakdownSalePrice() {
		if (_currentQuantity == 0)
			return _maxPrice;
		return _lowestPrice;
	}

	public int getCurrentQuantity() {
		return _currentQuantity;
	}

	public void updateCurrentQuantity(int quantity) {
		_currentQuantity += quantity;
	}

	private boolean add(Observer obs) {
		return _observers.add(obs);
	}

	private boolean remove(Observer obs) {
		return _observers.remove(obs);
	}

	public void toggleNotifications(Observer obs) {
		if (!_observers.contains(obs)) {
			add(obs);
		} else
			remove(obs);
	}

	public void notifyObservers(Notification notification) {
		for (Observer obs : _observers)
			obs.update(notification);
	}

	/**
	 * Update the product prices
	 * 
	 * @param price
	 */
	public void updatePrices(double price) {

		// produto acabado de criar
		if (_maxPrice == 0 && _highestPrice == 0 && _lowestPrice == 0) {
			_maxPrice = price;
			_highestPrice = price;
			_lowestPrice = price;
		}

		if (price > _maxPrice) {
			_maxPrice = price;
			_highestPrice = price;
		}

		if (_batches.size() == 0) {
			_lowestPrice = price;
		}

		if (price < _lowestPrice) {
			_lowestPrice = price;
			Notification brg = new Notification(NotificationType.BARGAIN, this, price);
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

	public void removeEmptyBatch() {
		Iterator<Batch> iter = _batches.iterator();
		while (iter.hasNext()) {
			if (iter.next().getQuantity() == 0)
				iter.remove();
		}
		setLowestPrice();
	}

	public void setLowestPrice() {
		if (_batches.size() != 0) {
			_lowestPrice = _batches.get(0).getPrice();
			for (Batch b : _batches) {
				if (b.getPrice() < _lowestPrice)
					_lowestPrice = b.getPrice();
			}
		}
	}

	/**
	 * Returns a list of all Batches order by: batch id->partner
	 * Key->price->quantity
	 * 
	 * @return
	 */
	public Collection<Batch> getAllBatches() {

		Collections.sort(_batches, new BatchesComparator());
		return Collections.unmodifiableCollection(_batches);
	}

	public Collection<Batch> getAllBatchesByPrice() {
		
		Collections.sort(_batches, new BatchesComparatorByPrice());
		return Collections.unmodifiableCollection(_batches);
	}

	protected final static class BatchesComparatorByPrice implements Comparator<Batch> {

		@Override
		public int compare(Batch b1, Batch b2) {

			if (b1.getPrice() != b2.getPrice())
				return (int) (b1.getPrice() - b2.getPrice());
			else
				return (b1.getQuantity() - b2.getQuantity());

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
		Notification newNot = new Notification(NotificationType.NEW, this, _lowestPrice);
		return newNot.toString();
	}

	public String bargainNotification() {
		Notification bargainNot = new Notification(NotificationType.BARGAIN, this, _lowestPrice);
		return bargainNot.toString();
	}

	public abstract boolean isSimple();
}
