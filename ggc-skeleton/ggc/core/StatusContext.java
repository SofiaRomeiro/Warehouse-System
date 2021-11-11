package ggc.core;

import java.io.Serializable;

public class StatusContext implements Serializable {
     
    private StatusState _currentStatus;
    private int _currentPoints;
    private static final long serialVersionUID = 209685192006L;

     
    /*public StatusContext(StatusState currentStatus) 
    {
        _currentStatus = currentStatus;
    }*/
	
	public StatusContext() {
		_currentStatus = Normal.getNormalInstance();
		_currentPoints = 0;
	}
 
    public StatusState getCurrentStatus() {
        return _currentStatus;
    }

    public String statusToString() { return _currentStatus.getStatus(); }
 
    public void setCurrentStatus(StatusState currentStatus) {
        _currentStatus = currentStatus;
    }
     
    public int getPoints() {
        return _currentPoints;
    }
 
    public void setPoints(int points) {
        _currentPoints = points;
    }
 
    public void update() {
        _currentStatus.updateState(this);
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
		return _currentStatus.applyFee(date, getPeriod(date, productType));
	}

	public double getDiscount(Date date, String productType) {
		return _currentStatus.applyDiscount(date, getPeriod(date, productType));
	}
	
	private void handleDelay(Date date) {
		int delay = date.getDate() - date.getDeadlinePayment();
		
		if (delay > 15 && "ELITE".equals(_currentStatus)) {
			_currentStatus.depromote(this);
			int points = (int) ( 0.25 * _currentPoints );
			setPoints(points);
		}
		else if (delay > 2 && "SELECTION".equals(_currentStatus)) {
			_currentStatus.depromote(this);
			int points = (int) ( 0.10 * _currentPoints );
			setPoints(points);
		}
		else
			setPoints(0);
	}
	
	private void handlePointChanging() {
		if (_currentPoints > 25000) {
			setCurrentStatus(Elite.getEliteInstance());
		}
		else if (_currentPoints < 2500 && _currentPoints > 2000) {
			setCurrentStatus(Selection.getSelectionInstance());
		}
		else {
			setCurrentStatus(Normal.getNormalInstance());
		}
	}
	
	
}