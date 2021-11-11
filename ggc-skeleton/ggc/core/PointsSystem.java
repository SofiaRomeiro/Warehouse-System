package ggc.core;

public abstract class PointsSystem implements Rewards {
	
	public abstract promote();

	public abstract applyDiscount();

	public abstract applyFee();

	public abstract void updateState();


}