package org.techfire225.robot.drivetrain.controllers;

import org.techfire225.lib.motion.TFPID;
import org.techfire225.robot.Constants;
import org.techfire225.robot.Robot;

/*
 * TurnPIDController
 * 
 * Implements a PID controller for turning to a desired angle
 * No output restrictions / motion profiling are applied.
 */
public class TurnPIDController implements DrivetrainController {
	Constants constants;
	TFPID pid;
	
	public TurnPIDController() {
		constants = Constants.getConstants();
		pid = new TFPID();
		refreshConstants();
	}
	
	public void setAngle(double theta) {
		pid.setSetpoint(theta);
	}
	
	@Override
	public boolean update() {
		double thetaOut = pid.calculate(Robot.drivetrain.getAngle());

		Robot.drivetrain.set(thetaOut, -thetaOut);
		
		return Math.abs(pid.getError()) < constants.AllowedTurnPIDError;
	}

	@Override
	public void reset() {
		pid.reset();
	}
	
	@Override
	public void refreshConstants() {
		pid.setPIDF(constants.TurnPID_kP, constants.TurnPID_kI, constants.TurnPID_kD, 0);
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
