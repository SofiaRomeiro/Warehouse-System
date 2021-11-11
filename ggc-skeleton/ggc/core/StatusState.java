package ggc.core;


// serve para contabilização de pontos e aplicação de multas e descontos

interface StatusState {

    public void updateState(StatusContext stctx);
	public void depromote(StatusContext stctx);
	public double applyDiscount(Date date, String period);
	public double applyFee(Date date, String period);
	public String getStatus();

}