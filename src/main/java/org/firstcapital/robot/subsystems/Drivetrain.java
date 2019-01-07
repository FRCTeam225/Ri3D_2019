package org.firstcapital.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import org.firstcapital.robot.Constants;
import org.firstcapital.robot.PortMap;
import org.firstcapital.robot.commands.drivetrain.CheesyDrive;
import org.firstcapital.lib.webapp.FireLog;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem{
    TalonSRX[] leftMotors;
    TalonSRX[] rightMotors;

    Encoder left_encoder = new Encoder(PortMap.LEFT_DRIVE_ENC[0], PortMap.LEFT_DRIVE_ENC[1]);
    Encoder right_encoder = new Encoder(PortMap.RIGHT_DRIVE_ENC[0], PortMap.RIGHT_DRIVE_ENC[1]);


    Solenoid shift = new Solenoid(PortMap.SHIFTER_SOLENOID);

    ADXRS450_Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);

    Constants constants;

    public Drivetrain() {
        constants = Constants.getConstants();

        leftMotors = new TalonSRX[]{new TalonSRX(PortMap.LEFT_DRIVE[0]), new TalonSRX(PortMap.LEFT_DRIVE[1]), new TalonSRX(PortMap.LEFT_DRIVE[2])};
        rightMotors = new TalonSRX[]{new TalonSRX(PortMap.RIGHT_DRIVE[0]), new TalonSRX(PortMap.RIGHT_DRIVE[1]), new TalonSRX(PortMap.RIGHT_DRIVE[2])};

        leftMotors[0].configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 100);
        rightMotors[0].configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 100);

        leftMotors[1].set(ControlMode.Follower, PortMap.LEFT_DRIVE[0]);
        leftMotors[2].set(ControlMode.Follower, PortMap.LEFT_DRIVE[0]);

        rightMotors[1].set(ControlMode.Follower, PortMap.RIGHT_DRIVE[0]);
        rightMotors[2].set(ControlMode.Follower, PortMap.RIGHT_DRIVE[0]);

        left_encoder.setDistancePerPulse(-constants.DriveEncoder_TickConversion);
        right_encoder.setDistancePerPulse(constants.DriveEncoder_TickConversion);

        resetEncoders();
        setLowGear(false);
        
        set(0, 0);
    }

    public double getDistance() {
        return (getLeftDistance() + getRightDistance()) / 2.0;
    }

    public double getLeftDistance() {
        return left_encoder.getDistance();
    }

    public double getRightDistance() {
        return right_encoder.getDistance();
    }

    public void resetEncoders() {
        left_encoder.reset();
        right_encoder.reset();
    }

    public double getAngle() {
        return gyro.getAngle();
    }

    public void resetGyro() {
        gyro.reset();
    }

    public void set(double left, double right) {
        leftMotors[0].set(ControlMode.PercentOutput, -left);
        rightMotors[0].set(ControlMode.PercentOutput, -right);
    }

    public void setLowGear(boolean lowGear) {
        shift.set(lowGear);
    }

    public void update() {
        FireLog.log("drive_position", getDistance());
        FireLog.log("drive_left", getLeftDistance());
        FireLog.log("drive_right", getRightDistance());
        FireLog.log("drive_heading", getAngle());
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new CheesyDrive());
    }
}
