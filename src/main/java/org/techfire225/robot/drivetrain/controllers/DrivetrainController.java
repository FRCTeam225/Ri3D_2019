package org.techfire225.robot.drivetrain.controllers;


/*
 * Generic interface for controllers operating on the drivetrain
 */
public interface DrivetrainController {
	/* 
	 * Run one cycle of this controller
	 * @returns true if on target, false if not
	 */
	public boolean update();
	
	public double getLinearError();
	public double getLinearActual();
	public double getLinearSetpoint();
	
	public double getAngularError();
	public double getAngularActual();
	public double getAngularSetpoint();
	
	public void reset();
	
	/*
	 * Reload constants that may have changed at runtime
	 * Intended to be used while tuning
	 */
	public void refreshConstants(); 
}
