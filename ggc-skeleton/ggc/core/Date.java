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
	private static final long serialVersionUID = 257349192006L;

	/**
	 * Constructor.
	 * @param days
	 */
	public Date() {
		_date = 0;
	}

	public Date(int days) {
		_date = days;
	}

	public static Date now() { return _now; }

	public int getDate() { return _date; }

	public int difference(Date other) { return other.getDate() - _date; }

	public static void advance(int days) throws NotValidDateException {

		if (days < 0) {
			throw new NotValidDateException();
		}

		_now = new Date(_now.getDate() + days);


	}


}