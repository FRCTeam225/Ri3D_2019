package org.techfire225.robot.drivetrain.controllers;

import org.techfire225.lib.motion.TFPID;
import org.techfire225.robot.Constants;
import org.techfire225.robot.Robot;

public class PivotTurnController implements DrivetrainController {

	Constants constants;
	TFPID pid;
	boolean leftSide;
	public PivotTurnController(boolean leftSide) {
		constants = Constants.getConstants();
		pid = new TFPID();
		this.leftSide = leftSide;
		
		refreshConstants();
	}
	
	public boolean update() {
		double thetaOut = pid.calculate(Robot.drivetrain.getAngle());

		if ( leftSide )
			Robot.drivetrain.set(thetaOut, 0);
		else
			Robot.drivetrain.set(0, -thetaOut);
		
		return Math.abs(pid.getError()) < constants.AllowedPivotTurnError;
	}
	
	public void setAngle(double angle) {
		pid.setSetpoint(angle);
	}
	
	@Override
	public void reset() {
		pid.reset();
	}
	
	@Override
	public void refreshConstants() {
		pid.setPIDF(constants.PivotTurnPID_kP, constants.PivotTurnPID_kI, constants.PivotTurnPID_kD, 0);
	}

	@Override
	public double getLinearError() {
		return 0;
	}

	@Override
	public double getLinearActual() {
		return 0;
	}

	@Override
	public double getLinearSetpoint() {
		return 0;
	}

	@Override
	public double getAngularError() {
		return pid.getError();
	}

	@Override
	public double getAngularActual() {
		return pid.getInput();
	}

	@Override
	public double getAngularSetpoint() {
		return pid.getSetpoint();
	}
}
