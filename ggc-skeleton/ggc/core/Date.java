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

	private int _days;
	private static Date _date;
	private static final long serialVersionUID = 257349192006L;

	/**
	 * Constructor.
	 * @param days
	 */
	private Date() {
		_days = 0;
	}

	public static Date now() {
		if (_date == null) {
			_date = new Date();
		}

		return _date;
	}

	public int difference(Date other) {
		return other.getDays() - _days;
	}

	public int getDays() {
		return _days;
	}

	public void advanceDate(int days) throws NotValidDateException {

		if (days < _days || days < 0) {
			throw new NotValidDateException();
		}
			_days += days; 
	}


}