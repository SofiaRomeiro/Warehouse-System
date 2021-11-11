package ggc.core;

public class Normal implements StatusState {

	private final String _status = "NORMAL";
	private static Normal _normalInstance = new Normal();
	
    private Normal() {}

    public static Normal getNormalInstance() { return _normalInstance; }

    public String getStatus() { return _status; }
      
    //Business logic and state transition
    
    public void updateState(StatusContext stctx) {
        stctx.setCurrentState(Selection.getSelectionInstance());
    }
	
	
    public void depromote(StatusContext stctx) {
        stctx.setCurrentState(this);
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

		int daysPassedSinceDeadline = date.getDate() - date.getDeadlinePayment();

		if ("P1".equals(period))
			return 0.0;
		else if ("P2".equals(period))
			return 0.0;
		else if ("P3".equals(period)) {
			Double days = Double.valueOf(daysPassedSinceDeadline);
			return 0.05 * days;
		}
		else {
			Double days = Double.valueOf(daysPassedSinceDeadline);
			return 0.10 * days;
		}

	}	
}
