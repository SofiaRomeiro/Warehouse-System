package ggc.core;

import java.io.Serializable;

public class Elite implements StatusState, Serializable {
    
	private final String _status = "ELITE";
	private static Elite _eliteInstance = new Elite();
	private static final long serialVersionUID = 109885192006L;
	
	private Elite() {}

    public static Elite getEliteInstance() { return _eliteInstance; }

    public String getStatus() { return _status; }
     
    //Business logic and state transition
    public void updateState(StatusContext stctx) 
    {
        stctx.setCurrentStatus(this);
    }
	
	
    public void depromote(StatusContext stctx) 
    {
        stctx.setCurrentStatus(Selection.getSelectionInstance());
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