package ggc.core.exception;

public class UnaComponentException extends Exception {

	private int _available;
	private int _requested;
	private String _product;

	/**
	 * @param filename
	 */

	public UnaComponentException(int available, int requested, String productKey) {
		_available = available;
		_requested = requested;
		_product = productKey;
	}

	/**
	 * @return the requested filename
	 */
	public int getAvailable() {
		return _available;
	}

	public int getRequested() {
		return _requested;
	}

	public String getProductComponent() {
		return _product;
	}

}
