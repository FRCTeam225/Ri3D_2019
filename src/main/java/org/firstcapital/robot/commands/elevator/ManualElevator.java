package org.firstcapital.robot.commands.elevator;

import org.firstcapital.lib.webapp.FireLog;
import org.firstcapital.robot.OI;
import org.firstcapital.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ManualElevator extends Command {
	boolean manualEnabled = false;
	boolean wasHolding = false;
	public ManualElevator() {
		requires(Robot.elevator);
	}
	
	public void initialize() {
		manualEnabled = false;
		wasHolding = false;
  }
    
  public void execute() {
		/*if ( Math.abs(OI.operator.getRawAxis(1)) > 0.15 ) {
			manualEnabled = true;
			wasHolding = false;
		}
		if ( manualEnabled ) {
			if ( Math.abs(OI.operator.getRawAxis(1)) > 0.1 ) {
				Robot.elevator.setManual(-OI.operator.getRawAxis(1));
				wasHolding = false;
			}
			else if ( !wasHolding ) {
				wasHolding = true;
				Robot.elevator.holdPosition();
			}
		}*/
  }
	
	@Override
	protected boolean isFinished() {
		return false;
	}
}