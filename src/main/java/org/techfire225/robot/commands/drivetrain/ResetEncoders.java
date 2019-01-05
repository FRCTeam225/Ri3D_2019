package org.techfire225.robot.commands.drivetrain;

import org.techfire225.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ResetEncoders extends Command {

	public ResetEncoders() {
		requires(Robot.drivetrain);
	}
	
	public void initialize() {
		Robot.drivetrain.resetEncoders();
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

}
