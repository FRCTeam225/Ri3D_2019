package org.firstcapital.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Timer;

import org.firstcapital.lib.motion.*;
import org.firstcapital.lib.webapp.FireLog;
import org.firstcapital.robot.Constants;
import org.firstcapital.robot.Robot;
import org.firstcapital.robot.subsystems.Drivetrain;

public class DriveProfiledDistance extends Command {
    Timer time = new Timer();
    TrapezoidalMotionProfile profile;
    double heading;
    Constants constants;

    double target;
    double error = Double.MAX_VALUE;
    double vf;

    public DriveProfiledDistance(double target, double heading, double maxVel, double maxAcc) {
        this(target, heading, maxVel, maxAcc, 0, 0);
    }

    public DriveProfiledDistance(double target, double heading, double maxVel, double maxAcc, double vi, double vf) {
        constants = Constants.getConstants();
        requires(Robot.drivetrain);
        this.heading = heading;
        this.profile = new TrapezoidalMotionProfile(target, maxVel, maxAcc, vi, vf);
        this.vf = vf;
        this.target = target;

        //setTimeout(profile.getDuration() + 2);
    }

    public void initialize() {
        System.out.println("test");
        time.reset();
        time.start();
        error = Double.MAX_VALUE;
        Robot.drivetrain.resetEncoders();
    }

    public void execute() {
        System.out.println("driving");
        ProfilePoint point = profile.getAtTime(time.get());
        error = point.pos - Robot.drivetrain.getDistance();
        double output = point.vel * constants.Drive_kF + error * constants.Drive_kP;
        double turn = (heading - Robot.drivetrain.getAngle()) * constants.Drive_TurnHold_kP;
        Robot.drivetrain.set(output + turn, output - turn);
    }

    public boolean isFinished() {
        FireLog.log("drive_error", target - Robot.drivetrain.getDistance());
        return Math.abs(target - Robot.drivetrain.getDistance()) < constants.Drive_OkayError;
    }

    public void end() {
        Robot.drivetrain.set(0, 0);
    }
}