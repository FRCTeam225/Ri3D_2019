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
    TalonSRX arm_motor = new TalonSRX(PortMap.ARM_CAN);
    TalonSRX intake_motor = new TalonSRX(PortMap.ARM_INTAKE_CAN);
    TalonSRX wrist_motor = new TalonSRX(PortMap.ARM_WRIST_CAN);

    Constants constants;

    TrapezoidalMotionProfile wristProfile = null;
    Timer wristProfileTimer = new Timer();
    double wristProfileOffset = 0;

    TrapezoidalMotionProfile armProfile = null;
    Timer armProfileTimer = new Timer();
    double armProfileOffset = 0;

    public Arm() {
        constants = Constants.getConstants();
        setWrist(0);
        setArm(0);
        setIntake(0);

        intake_motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 100);
        wrist_motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 100);
        resetEncoders();
    }   

    public void resetEncoders() {
        intake_motor.setSelectedSensorPosition(0);
        wrist_motor.setSelectedSensorPosition(0);
    }

    private void setArm(double speed) {
        arm_motor.set(ControlMode.PercentOutput, speed);
    }

    private void setWrist(double speed) {
        wrist_motor.set(ControlMode.PercentOutput, -speed);
    }

    public void setIntake(double speed) {
        intake_motor.set(ControlMode.PercentOutput, speed);
    }

    public void wristToGoal(double goal) {
        wristProfile = new TrapezoidalMotionProfile(goal-wrist_motor.getSelectedSensorPosition(), constants.WristMaxVelocity, constants.WristMaxAccel);
        wristProfileOffset = wrist_motor.getSelectedSensorPosition();
        wristProfileTimer.reset();
        wristProfileTimer.start();
    }

    public void armToGoal(double goal) {
        armProfile = new TrapezoidalMotionProfile(goal-intake_motor.getSelectedSensorPosition(), constants.ArmMaxVelocity, constants.ArmMaxAccel);
        armProfileOffset = intake_motor.getSelectedSensorPosition();
        armProfileTimer.reset();
        armProfileTimer.start();
    }

    public void initDefaultCommand() {}

    public void update() {
        FireLog.log("arm_position", intake_motor.getSelectedSensorPosition());
        FireLog.log("wrist_position", wrist_motor.getSelectedSensorPosition());

        if ( wristProfile != null ) {
            double output = 0;
            ProfilePoint point = wristProfile.getAtTime(wristProfileTimer.get());
            FireLog.log("wrist_target", point.pos);
            double error = (point.pos + wristProfileOffset) - wrist_motor.getSelectedSensorPosition();
            output += point.vel * constants.Wrist_kF + error * constants.Wrist_kP;
            FireLog.log("wrist_error", error);
            FireLog.log("wrist_output", output);
            setWrist(output);
        }
        else
            setWrist(0);

        if ( armProfile != null ) {
            System.err.println("running profile");
            double output = 0;
            ProfilePoint point = armProfile.getAtTime(armProfileTimer.get());
            FireLog.log("arm_target", point.pos);
            double error = (point.pos + armProfileOffset) - intake_motor.getSelectedSensorPosition();
            output += point.vel * constants.Arm_kF + error * constants.Arm_kP;
            FireLog.log("arm_error", error);
            FireLog.log("arm_output", output);
            setArm(output);
        }
        else
            setArm(0);
    }
}
