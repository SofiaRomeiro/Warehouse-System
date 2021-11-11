package ggc.core;

public class StatusContext {
     
    private StatusState _currentState;
    private int _currentPoints;
     
    public StatusContext(StatusState currentState) 
    {
        //super();
        _currentState = currentState;
    }
	
	public StatusContext() {
		_currentState = Normal.getNormalInstance();
	}
 
    public StatusState getCurrentState() {
        return _currentState;
    }
 
    public void setCurrentState(StatusState currentState) {
        _currentState = currentState;
    }
     
    public int getPoints() {
        return _currentPoints;
    }
 
    public void setPoints(int points) {
        _currentPoints = points;
    }
 
    public void update() {
        _currentState.updateState(this);
    }
	
	private String getPeriod(Date date, String productType) {
		int N = 0;
		
		if ("Simple".equals(productType))
			N = 5;
		else 
			N = 3;
		
		if ((date.getDeadlinePayment() - date.getDate()) >= N) { return "P1"; }
		else if ((date.getDeadlinePayment() - date.getDate() < N )) { return "P2"; }
		else if ((date.getDate() - date.getDeadlinePayment()) <= N ) { return "P3"; }
		else {return "P4"; }	
		
	}
	
	private boolean inPaymentPeriod(Date date, String productType) {
		return "P1".equals(getPeriod(date, productType)) || "P2".equals(getPeriod(date, productType));
	}
	
	public void pay(Date date, double payedPrice, String productType) {
		if (inPaymentPeriod(date, productType)) {
			_currentPoints += (10 * payedPrice);
			handlePointChanging();
		}
		else {
			handleDelay(date);
		}
	}

	public double getFee(Date date, String productType) {
		return _currentState.applyFee(date, getPeriod(date, productType));
	}

	public double getDiscount(Date date, String productType) {
		return _currentState.applyDiscount(date, getPeriod(date, productType));
	}
	
	private void handleDelay(Date date) {
		int delay = date.getDate() - date.getDeadlinePayment();
		
		if (delay > 15 && "ELITE".equals(_currentState)) {
			_currentState.depromote(this);
			int points = (int) ( 0.25 * _currentPoints );
			setPoints(points);
		}
		else if (delay > 2 && "SELECTION".equals(_currentState)) {
			_currentState.depromote(this);
			int points = (int) ( 0.10 * _currentPoints );
			setPoints(points);
		}
		else
			setPoints(0);
	}
	
	private void handlePointChanging() {
		if (_currentPoints > 25000) {
			setCurrentState(Elite.getEliteInstance());
		}
		else if (_currentPoints < 2500 && _currentPoints > 2000) {
			setCurrentState(Selection.getSelectionInstance());
		}
		else {
			setCurrentState(Normal.getNormalInstance());
		}
	}
	
	
}