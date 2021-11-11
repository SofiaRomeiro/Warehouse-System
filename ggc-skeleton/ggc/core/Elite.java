package ggc.core;

public class Elite implements StatusState {
    
	private final String _status = "ELITE";
	private static Elite _eliteInstance = new Elite();
	
	private Elite() {}

    public static Elite getEliteInstance() { return _eliteInstance; }

    public String getStatus() { return _status; }
     
    //Business logic and state transition
    public void updateState(StatusContext stctx) 
    {
        stctx.setCurrentState(this);
    }
	
	
    public void depromote(StatusContext stctx) 
    {
        stctx.setCurrentState(Selection.getSelectionInstance());
    }
	
	
	public double applyDiscount(Date date, String period) {

		

		if ("P1".equals(period))
			return 0.1;
		else if ("P2".equals(period)) 
			return 0.1;	
		else if ("P3".equals(period))
			return 0.05;
		else 	
			return 0.0;
	}
	
	
	public double applyFee(Date date, String period) {

		if ("P1".equals(period))
			return 0.0;
		else if ("P2".equals(period))
			return 0.0;
		else if ("P3".equals(period)) {
			return 0.0;
		}			
		else 	
			return 0.0;

	}
	
}