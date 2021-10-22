package ggc.core;



public class Date {

	private int _days;

	public Date(int days) {
		_days = days;
	}

	public Date() {
		this(0);
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