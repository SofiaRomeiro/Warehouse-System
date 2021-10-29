package ggc.core;

import java.io.Serializable;

/**
 * Classe Date
 * This class presents the behavior of a Date.
 * 
 * @author Edson da Veiga 100731
 * @author Sofia Romeiro 98968
 * @version 1.0
 */
public class Date implements Serializable {

	private int _days;
	private static final long serialVersionUID = 257349192006L;

	/**
	 * Constructor.
	 * @param days
	 */
	public Date(int days) {
		_days = days;
	}

	public int difference(Date other) {
		return other.getDays() - _days;
	}

	public int getDays() {
		return _days;
	}

	public static /*Date*/ void now() {
		// ??????
	}

	public boolean advanceDate(int days) {
		if (days < _days || days < 0) {
			return false;
		}
		else {
			_days += days; 
		}
		return true;
	}


}