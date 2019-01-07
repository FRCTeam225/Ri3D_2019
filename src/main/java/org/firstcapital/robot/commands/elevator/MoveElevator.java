package org.firstcapital.robot.commands.elevator;

import org.firstcapital.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class MoveElevator extends Command {
	double goal;
	public MoveElevator(double goal) {
		requires(Robot.elevator);
		this.goal = goal;
	}
	
	public void initialize() {
		Robot.elevator.goToGoal(goal);
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
}