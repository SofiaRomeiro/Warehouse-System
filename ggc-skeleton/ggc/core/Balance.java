package ggc.core;

public class Balance {

	private double _available;
	private double _accountant;

	public Balance(double available, double _accountant) {
		_available = available;
		_accountant = _accountant;
	}

	public double getCurrentAvailable() {
		return _available;
	}

	public double getCurrentAccountant() {
		return _accountant;
	}

	public void setCurrentAvailable(double balance) {
		_available += balance;
	}

	public void setCurrentAccountant(double balance) {
		_accountant += balance;
	}


}