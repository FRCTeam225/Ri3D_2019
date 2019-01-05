package org.techfire225.robot.drivetrain.controllers;

import org.techfire225.lib.motion.ProfilePoint;
import org.techfire225.lib.motion.TrapezoidalMotionProfile;
import org.techfire225.robot.Constants;
import org.techfire225.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

/*
 * ProfileTurnController
 * 
 * Implements control loop to follow a TrapezoidalMotionProfile for angular displacement
 * 
 * 
 * Constants used:
 * - TurnProfile_kP The P term for the motion profile follower
 * - TurnProfile_kI The I term for the motion profile follower
 * - TurnProfile_kD The D term for the motion profile follower
 * - TurnProfile_kV The feedforward velocity term for the follower. This term is multiplied
 *					 with the current point's velocity value
 * - TurnProfile_kA The feedforward acceleration term for the follower. This term is multiplied
 *					 with the current point's acceleration value
 */
public class ProfileTurnController implements DrivetrainController {
	Constants constants;
	
	double startAngle;
	double startT;
	TrapezoidalMotionProfile profile;
	
	double angularError, angularActual, angularSetpoint;
	
	/*
	 * ProfileTurnController constructor
	 * 
	 * @param profile The TrapezoidalMotionProfile to follow
	 * @param theta The angle to maintain while driving straight
	 */
	public ProfileTurnController(TrapezoidalMotionProfile profile) {
		constants = Constants.getConstants();
		
		this.profile = profile;
		refreshConstants();
		start();
	}
	
	
	/*
	 * Configure this controller to begin following the profile, making t=0 the current moment
	 */
	public void start() {
		startT = Timer.getFPGATimestamp();
		startAngle = Robot.drivetrain.getAngle();
	}

	@Override
	public boolean update() {
		double t = Timer.getFPGATimestamp() - startT;
		ProfilePoint point = profile.getAtTime(t);
		
		angularActual = Robot.drivetrain.getAngle();
		angularSetpoint = point.pos;
		
		double feedforward = (point.vel * constants.TurnProfile_kV) + (point.acc * constants.TurnProfile_kA);
		angularError = (angularActual-startAngle) - point.pos;
		double output = (angularError * constants.TurnProfile_kP) + feedforward;
		Robot.drivetrain.set(output, -output);
		
		return t >= profile.getDuration();
	}


	@Override
	public void reset() {
		start();
	}

	@Override
	public void refreshConstants() {
		
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
		return angularError;
	}


	@Override
	public double getAngularActual() {
		return angularActual;
	}


	@Override
	public double getAngularSetpoint() {
		return angularSetpoint;
	}
}
