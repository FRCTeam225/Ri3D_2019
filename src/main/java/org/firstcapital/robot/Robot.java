package org.firstcapital.robot;

import org.firstcapital.lib.webapp.FireLog;
import org.firstcapital.lib.webapp.Webserver;
import org.firstcapital.lib.framework.*;
import org.firstcapital.robot.commands.autonomous.DoNothing;
import org.firstcapital.robot.subsystems.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {
	
	public static Constants constants;
	public static Drivetrain drivetrain;
	public static Elevator elevator;
	public static Arm arm;
	public static Pegs pegs;
	Webserver webserver;
	
	Command autonomousCommand = null;

	final int ARM_UP = -1200;
	final int ARM_DOWN = 0;

	AxisButton pickupHatch;
	AxisButton pickupCargo;

	int[][] presets = new int[][] {
		// elevator, arm, wrist
		{0, ARM_DOWN, 0}, // stow
		{0, ARM_DOWN, }, // stow
	};
	
	public Robot() {
		// RT
		pickupHatch = new AxisButton(OI.operator, OI.X, 0.5);
		// LT
		pickupCargo = new AxisButton(OI.operator, OI.B, 0.5);
		constants = Constants.getConstants();
		drivetrain = new Drivetrain();
		elevator = new Elevator();
		arm = new Arm();
		pegs = new Pegs();
		
		
		drivetrain.resetEncoders();
		
		OI.init();
		
		AutonomousChooser.getInstance().setAutonomi(
			new Autonomous[] {
    			new DoNothing(),
			}
		);

		try {
			webserver = new Webserver();
		} catch (Exception e) {
			System.err.println("Webapp server crashed");
		}
	}
	
	public void disabledInit() {
	}
	
	public void disabledPeriodic() {
		AutonomousChooser.getInstance().consoleSelectorUI(OI.autoSelectUp, OI.autoSelectDown);
	}

	public void autonomousInit() {
		Robot.drivetrain.setLowGear(true);
		Robot.drivetrain.resetEncoders();
		Robot.drivetrain.resetGyro();
		
		autonomousCommand = AutonomousChooser.getInstance().getSelectedAutonomous();

		if (autonomousCommand != null)
			autonomousCommand.start();
	}
	
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}
	
	public void teleopInit() {
		Robot.elevator.setManual(0);
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		Robot.drivetrain.set(0, 0);
		Robot.drivetrain.setLowGear(false);
	}
	
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		if ( OI.driver.getRawButton(OI.A) )
			Robot.arm.setIntake(1);
		else if ( OI.driver.getRawButton(OI.B) )
			Robot.arm.setIntake(-1);
		else
			Robot.arm.setIntake(0);

		if ( OI.driver.getRawButton(OI.X) )
			Robot.pegs.set(true, false);
		else if ( OI.driver.getRawButton(OI.Y) )
			Robot.pegs.set(false, true);
		else
			Robot.pegs.set(false, false);

		Robot.pegs.setEjector(OI.driver.getRawButton(OI.RB));


		if ( OI.operator.getRawButton(OI.LB) ) {
			Robot.arm.wristToGoal(constants.ScoreHatchWrist);
		}
		else if ( OI.operator.getRawButton(OI.RB) ) {
			Robot.arm.wristToGoal(constants.ScoreBallWrist);
		}

		// Pickup
		if ( OI.operator.getRawButton(OI.START) ) {
			Robot.arm.armToGoal(0);
			Robot.arm.wristToGoal(-3500);
			Robot.elevator.goToGoal(0);
		}
		// Pickup Cargo
		if ( pickupCargo.get() ) {
			Robot.arm.armToGoal(constants.PickupBallArm);
			Robot.arm.wristToGoal(constants.PickupBallWrist);
			Robot.elevator.goToGoal(0);
		}
		
		// Pickup Hatch
		if ( pickupHatch.get() ) {
			Robot.arm.armToGoal(0);
			Robot.arm.wristToGoal(constants.AlmostPickupHatchWrist);
			Robot.elevator.goToGoal(0);
		}

		// Down everything
		if ( OI.operator.getRawButton(OI.BACK) ) {
			Robot.arm.armToGoal(0);
			Robot.arm.wristToGoal(0);
			Robot.elevator.goToGoal(0);
		}	
		
		if ( OI.operator.getRawButton(OI.Y) ) {
			Robot.arm.armToGoal(constants.L2Arm);
			Robot.arm.wristToGoal(constants.ScoreHatchWrist);
		}

		if ( OI.operator.getRawButton(OI.X) ) {
			Robot.elevator.goToGoal(constants.TopElevator);
		}
	}

	public void robotPeriodic() {
		//FireLog.log("gyroconn", gyro.isConnected());
		Robot.drivetrain.update();
		Robot.elevator.update();
		Robot.arm.update();
	}
}