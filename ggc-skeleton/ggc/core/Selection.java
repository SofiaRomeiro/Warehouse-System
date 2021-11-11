package ggc.core;

public class Selection implements StatusState {

	public final String _status = "SELECTION";
	private static Selection _selectionInstance = new Selection();
	
	private Selection() {}

    public static Selection getSelectionInstance() { return _selectionInstance; }  
 
 	public String getStatus() { return _status; }
     
    //Business logic and state transition
    
	public void updateState(StatusContext stctx) {
        stctx.setCurrentStatus(Elite.getEliteInstance());
    }
	
	
    public void depromote(StatusContext stctx) {
        stctx.setCurrentStatus(Normal.getNormalInstance());
    }
	
	
	public double applyDiscount(Date date, String period) {

		if ("P1".equals(period))
			return 0.1;
		else if ("P2".equals(period)) {
			if ((date.getDate() - date.getDeadlinePayment()) >= 2)
				return 0.05;
			else 
				return 0;
		}		
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
			if ((date.getDeadlinePayment() - date.getDate()) == 1)
				return 0.0;
			else {
				Double days = Double.valueOf(daysPassedSinceDeadline);
				return 0.02 * days;
			}
		}			
		else {
			Double days = Double.valueOf(daysPassedSinceDeadline);	
			return 0.05 * days;
		}
	}
	
}