package org.firstcapital.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.firstcapital.lib.motion.*;
import org.firstcapital.lib.webapp.FireLog;
import org.firstcapital.robot.*;

public class Arm extends Subsystem {
    Timer profileTimer = new Timer();
    TrapezoidalMotionProfile profile = null;

    Spark motor = new Spark(PortMap.ARM_PWM);
    //TalonSRX intake_motor = new TalonSRX(PortMap.ARM_INTAKE_CAN);

    Constants constants;

    public Arm() {
        constants = Constants.getConstants();
        set(0);
        setIntake(0);

        //intake_motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 100);
    }

    public void set(double speed) {
        motor.set(speed);
    }

    public void setIntake(double speed) {
        //intake_motor.set(ControlMode.PercentOutput, speed);
    }

    public void goToGoal(double goal) {
       // profile = new TrapezoidalMotionProfile(goal-encoder.getDistance(), constants.ArmMaxVelocity, constants.ArmMaxAccel);
        profileTimer.reset();
        profileTimer.start();
    }

    public void initDefaultCommand() {}

    public void update() {
        FireLog.log("arm_position", 0);
        /*
        if ( profile == null )
            set(0);
        else {
            ProfilePoint point = profile.getAtTime(profileTimer.get());
            double error = point.pos - 0;
            double output = point.vel * constants.Arm_kF + error * constants.Arm_kP;
            set(output);
        }*/
    }
}
