package ggc.core;

import java.io.Serializable;

/**
 * Classe Balance
 * This class presents the behavior of a Balance.
 * 
 * @author Edson da Veiga 100731
 * @author Sofia Romeiro 98968
 * @version 1.0
 */
public class Balance implements Serializable{

	private static final long serialVersionUID = 202987692006L;
	private double _available;
	private double _accountant;
	private static Balance _balance;

	/**
	 * Constructor.
	 * 
	 * @param available
	 * @param accountant
	 */
	private Balance() {
		_available = 0;
		_accountant = 0;
	}

	public static Balance getBalance() {
		if (_balance == null) {
			_balance = new Balance();
		}
		return _balance;
	}
	/**
	 * Returns the current available balance.
	 * 
	 * @return
	 */
	public double getCurrentAvailable() {
		return _available;
	}

	/**
	 * Returns the current accountant balance.
	 * 
	 * @return
	 */
	public double getCurrentAccountant() {
		return _accountant;
	}

	/**
	 * Update current available balance.
	 * 
	 * @param balance
	 */
	public void setCurrentAvailable(double balance) {
		_available += balance;
	}

	/**
	 * Update current accountant balance.
	 * 
	 * @param balance
	 */
	public void setCurrentAccountant(double balance) {
		_accountant += balance;
	}


}