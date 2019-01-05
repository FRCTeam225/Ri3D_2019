package org.techfire225.robot.commands.drivetrain;

import org.techfire225.robot.Constants;
import org.techfire225.robot.OI;
import org.techfire225.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


public class CheesyDrive extends Command {
    
    double turn_gain = 1;
    double skim_gain = 0;
    double turn_velocity_multiplier_gain = 1.1;
    
    Constants constants = Constants.getConstants();
    
    public CheesyDrive() {
        requires(Robot.drivetrain);
    }

    protected void initialize() {
    }
    
    protected void execute() {
        double throttle = OI.driver.getRawAxis(1);
        double turnInput = OI.driver.getRawAxis(4);
        double wheelNonLinearity = 0.5;
        turnInput = Math.sin((Math.PI/2)*wheelNonLinearity*turnInput)/Math.sin((Math.PI/2)*wheelNonLinearity);
        turnInput = Math.sin((Math.PI/2)*wheelNonLinearity*turnInput)/Math.sin((Math.PI/2)*wheelNonLinearity);
        turnInput = Math.sin((Math.PI/2)*wheelNonLinearity*turnInput)/Math.sin((Math.PI/2)*wheelNonLinearity);
        
        if ( Math.abs(turnInput) < 0.07 )
            turnInput = 0;
        
        double turn = 0;
        if ( Math.abs(throttle) < 0.07 ) {
            throttle = 0;
            turn = turnInput*turn_gain;
        }
        else
            turn = (turnInput*turn_gain)*Math.abs(turn_velocity_multiplier_gain*OI.driver.getRawAxis(1));
        
        double left_orig = throttle-turn;
        double right_orig = throttle+turn;

        double left = left_orig+skim(right_orig);
        double right = right_orig+skim(left_orig);

        Robot.drivetrain.set(-left, -right);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.drivetrain.set(0, 0);
    }
    
    private double skim(double v) {
        if (v > 1.0)
            return -((v - 1.0) * skim_gain);
        else if (v < -1.0)
            return -((v + 1.0) * skim_gain);
        return 0;
    }
}