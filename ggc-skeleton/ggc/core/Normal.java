package ggc.core;

import java.io.Serializable;

public class Normal implements StatusState, Serializable {

	private final String _status = "NORMAL";
	private static Normal _normalInstance = new Normal();
	private static final long serialVersionUID = 209629462006L;

	private Normal() {
	}

	public static Normal getNormalInstance() {
		return _normalInstance;
	}

	public String getStatus() {
		return _status;
	}

	public void updateState(StatusContext stctx) {
		stctx.setCurrentStatus(Selection.getSelectionInstance());
	}

	public void depromote(StatusContext stctx) {
		stctx.setCurrentStatus(this);
	}

	public double applyDiscount(Date date, String period) {

		if ("P1".equals(period))
			return 0.1;
		else if ("P2".equals(period))
			return 0.0;
		else if ("P3".equals(period))
			return 0.0;
		else
			return 0.0;
	}

	public double applyFee(Date date, String period) {

		int daysPassedSinceDeadline = date.getPaymentDate() - date.getDeadlinePayment();

		if ("P1".equals(period))
			return 0.0;
		else if ("P2".equals(period))
			return 0.0;
		else if ("P3".equals(period)) {
			Double days = Double.valueOf(daysPassedSinceDeadline);
			return 0.05 * days;
		} else {
			Double days = Double.valueOf(daysPassedSinceDeadline);
			return 0.10 * days;
		}
	}
}
