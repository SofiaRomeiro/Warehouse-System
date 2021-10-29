package ggc.core;

import java.io.Serializable;


public class Balance implements Serializable{

	private static final long serialVersionUID = 202987692006L;
	private double _available;
	private double _accountant;

	public Balance(double available, double accountant) {
		_available = available;
		_accountant = accountant;
	}

	public Balance() {
		this(0,0);
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