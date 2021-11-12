package ggc.core;

import java.io.Serializable;
import ggc.core.exception.NotValidDateException;

/**
 * Classe Date
 * This class presents the behavior of a Date.
 * 
 * @author Edson da Veiga 100731
 * @author Sofia Romeiro 98968
 * @version 1.0
 */
public class Date implements Serializable {

	private static Date _now = new Date();
	private final int _date;
	private int _deadlinePayment;
	private int _paymentDate;
	private static final long serialVersionUID = 257349192006L;

	/**
	 * Constructor.
	 * @param days
	 */

	public Date() {
		_date = 0;
		_deadlinePayment = 0;
	}

	public Date(int days) {
		_date = days;
		_deadlinePayment = 0;
	}

	public Date(int days, int deadlinePayment) {
		_date = days;
		_deadlinePayment = deadlinePayment;
	}

	public static Date now() { return _now; }

	public int getDate() { return _date; }

	public void setDeadlinePayment(int date) { _deadlinePayment = date; }

	public int getDeadlinePayment() { return _deadlinePayment; }

	public void setPaymentDate(int date) { _paymentDate = date; }

	public int getPaymentDate() { return _paymentDate; }

	public int difference(Date other) { return other.getDate() - _date; }

	public static Date advance(int days) throws NotValidDateException {

		if (days < 0) {
			throw new NotValidDateException();
		}		

		return _now = new Date(_now.getDate() + days);	

	}

	public Date setDate(int days) {
		return _now = new Date(days);
	}


}