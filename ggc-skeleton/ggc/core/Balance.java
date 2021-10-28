package ggc.core;

import java.io.Serializable;


public class Balance implements Serializable{

	private double _available;
	private double _accountant;

	public Balance(double available, double accountant) {
		_available = available;
		_accountant = accountant;
	}

	public Balance() {
		this(0,0);
		// acho que aqui podemos usar o this
		_available = 0;
		_accountant = 0;
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