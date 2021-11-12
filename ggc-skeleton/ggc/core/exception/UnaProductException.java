package ggc.core.exception;

public class UnaProductException extends Exception{

    int _available;
    String _product;

	/**
	 * @param filename 
	 */
	public UnaProductException(int available) {
        _available = available;
	}

	public UnaProductException(int available, String productKey) {
		_available = available;
		_product = productKey;
	}

	/**
	 * @return the requested filename
	 */
	public int getAvailable() {
		return _available;
	}

	public String getProductComponent() {
		return _product;
	}
    
}
