package org.techfire225.robot.commands.drivetrain;

import org.techfire225.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class Shift extends InstantCommand {
	
	boolean state;
	
	public Shift(boolean state) {
		this.state = state;
	}
	
	public void execute() {
		Robot.drivetrain.setLowGear(state);
	}
}
