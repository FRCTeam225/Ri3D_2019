package org.firstcapital.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.firstcapital.lib.motion.*;
import org.firstcapital.lib.webapp.*;
import org.firstcapital.robot.*;
import org.firstcapital.robot.commands.elevator.*;

public class Elevator extends Subsystem {

    Timer profileTimer = new Timer();
    double profileOffset = 0;
    TrapezoidalMotionProfile profile = null;

    Spark[] motors = new Spark[] { new Spark(PortMap.ELEVATOR_PWM[0]), new Spark(PortMap.ELEVATOR_PWM[1]) };

    Encoder encoder = new Encoder(PortMap.ELEVATOR_ENCODER[0], PortMap.ELEVATOR_ENCODER[1]);

    Constants constants;



    enum Mode {
        MANUAL,
        HOLD,
        PROFILE
    };

    Mode currentMode = Mode.MANUAL;
    double holdPosition = 0;

    public Elevator() {
        constants = Constants.getConstants();
        set(0);
    }

    public void setManual(double speed) {
        currentMode = Mode.MANUAL;
        set(speed + constants.Elevator_kGravity);
    }

    public void holdPosition() {
        holdPosition = encoder.getDistance();
        currentMode = Mode.HOLD;
    }

    public void goToGoal(double goal) {
        profile = new TrapezoidalMotionProfile(goal-encoder.getDistance(), constants.ElevatorMaxVelocity, constants.ElevatorMaxAccel);
        System.out.println("Generating profile for "+(goal-encoder.getDistance()));
        profileOffset = encoder.getDistance();
        profileTimer.reset();
        profileTimer.start();

        currentMode = Mode.PROFILE;
    }

    private void set(double speed) {
        for ( Spark s : motors )
            s.set(-speed);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new ManualElevator());
    }

    public void update() {
        FireLog.log("elevator_position", encoder.getDistance());

        if ( currentMode == Mode.MANUAL )
            return;

        double output = constants.Elevator_kGravity;
        if ( currentMode == Mode.PROFILE && profile != null ) {
            ProfilePoint point = profile.getAtTime(profileTimer.get());
            FireLog.log("elevator_target", point.pos);
            double error = (point.pos + profileOffset) - encoder.getDistance();
            output += point.vel * constants.Elevator_kF + error * constants.Elevator_kP;
            FireLog.log("elevator_error", error);
        }
        else if ( currentMode == Mode.MANUAL ) {
            double error = holdPosition - encoder.getDistance();
            output += error * constants.Elevator_kP;
            FireLog.log("elevator_error", error);
        }

        if ( output < 0 && encoder.getDistance() < 50 )
            output = 0;
        set(output);

    }
}
