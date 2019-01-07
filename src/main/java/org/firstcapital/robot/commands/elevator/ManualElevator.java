package org.firstcapital.robot.commands.elevator;

import org.firstcapital.robot.OI;
import org.firstcapital.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ManualElevator extends Command {
	public ManualElevator() {
		requires(Robot.elevator);
	}
	
	public void initialize() {
  }
    
  public void execute() {
		Robot.elevator.setManual(-OI.operator.getRawAxis(1));
  }
	
	@Override
	protected boolean isFinished() {
		return false;
	}
}
