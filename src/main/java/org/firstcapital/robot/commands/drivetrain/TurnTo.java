package org.firstcapital.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

import org.firstcapital.lib.motion.*;
import org.firstcapital.robot.Constants;
import org.firstcapital.robot.Robot;
import org.firstcapital.robot.subsystems.Drivetrain;

public class TurnTo extends Command {
    Constants constants;
    double error;
    double heading;
    double lastError;
    double lastTime;

    public TurnTo(double heading) {
        constants = Constants.getConstants();
        requires(Robot.drivetrain);
        this.heading = heading;
    }

    public void initialize() {
    }

    public void execute() {
        double now = Timer.getFPGATimestamp();
        double dt = now - lastTime;
        lastTime = now;

        error = heading - Robot.drivetrain.getAngle();
        double rate = (error - lastError) / dt;
        double turn = error * constants.Drive_Turn_kP + constants.Drive_Turn_kD * rate;
        Robot.drivetrain.set(turn, -turn);
    }

    public boolean isFinished() {
        return Math.abs(error) < constants.Drive_Turn_OkayError;
    }

    public void end() {
        Robot.drivetrain.set(0, 0);
    }
}