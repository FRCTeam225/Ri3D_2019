package org.techfire225.robot;

import org.techfire225.robot.commands.autonomous.DoNothing;
import org.techfire225.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

public class Robot extends TimedRobot {
	
	public static Constants constants;
	public static Drivetrain drivetrain;
	
	Command autonomousCommand = null;
	
	public Robot() {
		constants = Constants.getConstants();
		
		drivetrain = new Drivetrain();
		
		drivetrain.resetEncoders();
		
		OI.init();
		
		AutonomousChooser.getInstance().setAutonomi(
			new Autonomous[] {
    			new DoNothing(),
			}
		);
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
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		Robot.drivetrain.set(0, 0);
		Robot.drivetrain.setLowGear(false);
	}
	
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}
}