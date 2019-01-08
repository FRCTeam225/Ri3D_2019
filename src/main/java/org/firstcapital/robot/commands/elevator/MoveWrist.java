package org.firstcapital.robot.commands.elevator;

import org.firstcapital.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class MoveWrist extends Command {
	double goal;
	public MoveWrist(double goal) {
		requires(Robot.arm);
		this.goal = goal;
	}
	
	public void initialize() {
		Robot.arm.wristToGoal(goal);
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}
}