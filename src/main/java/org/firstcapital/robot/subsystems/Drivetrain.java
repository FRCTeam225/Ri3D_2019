package org.firstcapital.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import org.firstcapital.robot.Constants;
import org.firstcapital.robot.PortMap;
import org.firstcapital.robot.commands.drivetrain.CheesyDrive;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem{
	
	TalonSRX[] leftMotors;
	TalonSRX[] rightMotors;
			
	Solenoid shift = new Solenoid(PortMap.SHIFTER_SOLENOID);
	public Compressor compressor = new Compressor(0);
	
	ADXRS450_Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
	
	Constants constants;
		
	PowerDistributionPanel pdp;
	
	double REMOVEME = 0;

	public Drivetrain() {
		pdp = new PowerDistributionPanel();
		
		constants = Constants.getConstants();
		
		leftMotors = new TalonSRX[]{new TalonSRX(PortMap.LEFT_DRIVE[0]), new TalonSRX(PortMap.LEFT_DRIVE[1]), new TalonSRX(PortMap.LEFT_DRIVE[2])};
		rightMotors = new TalonSRX[]{new TalonSRX(PortMap.RIGHT_DRIVE[0]), new TalonSRX(PortMap.RIGHT_DRIVE[1]), new TalonSRX(PortMap.RIGHT_DRIVE[2])};
		
		leftMotors[0].configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 100);
		rightMotors[0].configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 100);

		leftMotors[1].set(ControlMode.Follower, PortMap.LEFT_DRIVE[0]);
		leftMotors[2].set(ControlMode.Follower, PortMap.LEFT_DRIVE[0]);
		
		rightMotors[1].set(ControlMode.Follower, PortMap.RIGHT_DRIVE[0]);
		rightMotors[2].set(ControlMode.Follower, PortMap.RIGHT_DRIVE[0]);
		
		resetEncoders();
		setLowGear(true);
		set(0, 0);
	}
	
	public double getDistance() {
		return (getLeftDistance() + getRightDistance())/2;
	}
	
	public double getLeftDistance() {
		return leftMotors[0].getSelectedSensorPosition(0);
	}
	
	public double getRightDistance() {
		return rightMotors[0].getSelectedSensorPosition(0);
	}
	
	public void resetEncoders() {
		rightMotors[0].setSelectedSensorPosition(0, 0, 0);
	  	leftMotors[0].setSelectedSensorPosition(0, 0, 0);
	}
	
	public double getAngle() {
		return gyro.getAngle();
	}
	
	public void resetGyro() {
		gyro.reset();
	}
	
	public void set(double left, double right) {
		leftMotors[0].set(ControlMode.PercentOutput, -left);
		rightMotors[0].set(ControlMode.PercentOutput, right);
	}
	
	public void setLowGear(boolean lowGear) {
		shift.set(lowGear);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new CheesyDrive());
	}
}
