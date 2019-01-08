package org.firstcapital.robot.commands.elevator;

import org.firstcapital.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArm extends Command {
	double goal;
	public MoveArm(double goal) {
		requires(Robot.arm);
		this.goal = goal;
	}
	
	public void initialize() {
		Robot.arm.armToGoal(goal);
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}
}