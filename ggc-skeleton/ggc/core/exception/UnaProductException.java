package ggc.core.exception;

public class UnaProductException extends Exception{

    int _available;

	/**
	 * @param filename 
	 */
	public UnaProductException(int available) {
        _available = available;
	}

	/**
	 * @return the requested filename
	 */
	public int getAvailable() {
		return _available;
	}
    
}
