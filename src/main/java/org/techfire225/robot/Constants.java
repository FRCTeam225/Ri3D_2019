package org.techfire225.robot;

import java.io.File;

import org.techfire225.lib.framework.InterpolatingTree;

public class Constants {
	
	private static Constants instance;
	private static boolean isPractice = false;
	private static double visionReferenceDistance = 77.0; // 80.0, calibration distance when the front of the bumpers is aligned with the boiler-side of the key tape
	
	public static Constants getConstants() {		
		if ( instance == null ) {
			File practiceBotFile = new File("/practice");
			if ( practiceBotFile.exists() ) {
				instance = new PracticeRobotConstants();
				isPractice = true;
			}
			else
				instance = new Constants();
		}
		return instance;
	}
	
	public static boolean isPracticeRobot() {
		return isPractice;
	}
	
	// Wheelbase width in inches
	public double WheelbaseWidth = 24;
	
	// Ticks / ft
	// 4x encoding
	// 256 ticks / rev
	// 1:3 from output shaft to encoder
	// 34:50 from gearbox output to drivetrain
	// 4in wheel
	// 12 inches -> 1 ft
	public double DriveEncoder_TickConversion = (1.0/4.0)*(1.0/256.0)*(1.0/3.0)*(34.0/50.0)*(4.0*Math.PI)*(1.0/12.0);
	
	// Ticks/100ms -> ft/s
	public double DriveEncoder_VelocityConversion = DriveEncoder_TickConversion * 10;
	
	public double DriveProfile_kV = 0.119;
	public double DriveProfile_kA = 0;
	public double DriveProfile_kP = 0.2;
	public double DriveProfile_theta_kP = 0.03;
	
	public double TurnProfile_kV = 0.02;
	public double TurnProfile_kA = 0;
	public double TurnProfile_kP = -0.059;
	
	public double TurnPID_kP = 0.045; // 0.059
	public double TurnPID_kI = 0.00000015; // 0
	public double TurnPID_kD = 0.0045; // 0.0045
	
	public double MaxDriveVelocity = 8000;
	
	public double LeftDriveVelocityP = 0.3;
	public double LeftDriveVelocityD = 0;
	public double LeftDriveVelocityF = 1023.0 / MaxDriveVelocity;
	
	public double RightDriveVelocityP = 0.3;
	public double RightDriveVelocityD = 0;
	public double RightDriveVelocityF = 1023.0 / MaxDriveVelocity;
	
	public double MaxDriveVelocityLow = 3000;
	
	public double LeftDriveVelocityPLow = 0;
	public double LeftDriveVelocityDLow = 0;
	public double LeftDriveVelocityFLow = 1023.0 / MaxDriveVelocityLow;

	public double RightDriveVelocityPLow = 0;
	public double RightDriveVelocityDLow = 0;
	public double RightDriveVelocityFLow = 1023.0 / MaxDriveVelocityLow;
	
	public double DriveRampRate = 120;
	
	
	public double PivotTurnPID_kP = 0.05;
	public double PivotTurnPID_kI = 0;
	public double PivotTurnPID_kD = 0.002;

	/* Flywheel constants */
	/*
	public double ShooterP = 1.5;//0.205; // 0.2
	public double ShooterI = 0.0;
	public double ShooterD = 2.5; // 0.33
	public double ShooterF = 0.039; // 0.04
	*/
	public double ShooterP = 1;//0.205; // 0.2
	public double ShooterI = 0.0;
	public double ShooterD = 1; // 0.33
	public double ShooterF = 0.0369; // 0.04
	public int ShooterIZone = 0;

	public double ShooterCloseRPM = 2650;
	public double ShooterFarRPM = 2815;
	
	public double ShooterOverpowerOffset = 40;
	public double ShooterOverpowerDuration = 0.3;

	public double AllowedDriveError = 0.25; // 0.25
	public double AllowedTurnError = 1.8;
	public double AllowedTurnPIDError = 1.3;
	public double AllowedPivotTurnError = 4.0;
	
	public int VisionServerPort = 9225;
	public int VisionMjpegPort = 5800;
	
	public double ClimberStartSpeed = 0.25;
	
	public double HatVisionAlignP = 0.04;
	
	// RPM constantly subtracted
	public double DriveAndShoot_ConstantRPMOffset = 40;
	// RPM change per foot
	public double DriveAndShoot_DistanceToRPMOffsetSlope = -100; 
	
	public final static InterpolatingTree distanceToRPM = new InterpolatingTree(20);
	
	// Table mapping distance from the goal in inches to RPM
	{
		distanceToRPM.put(0.0, 0.0);
		distanceToRPM.put(visionReferenceDistance, 2860.0+ShooterOverpowerOffset);
		distanceToRPM.put(visionReferenceDistance+7.0, 2880.0+ShooterOverpowerOffset);
		distanceToRPM.put(visionReferenceDistance+10.0, 2880.0+ShooterOverpowerOffset);
		distanceToRPM.put(visionReferenceDistance+14.5, 2910.0+ShooterOverpowerOffset);
		distanceToRPM.put(visionReferenceDistance+21.0, 3000.0+ShooterOverpowerOffset); // 2960
		distanceToRPM.put(visionReferenceDistance+27.0, 3065.0+ShooterOverpowerOffset);
		distanceToRPM.put(visionReferenceDistance+32.4, 3100.0+ShooterOverpowerOffset);
	}
	
	
	public static class PracticeRobotConstants extends Constants {	
	}
	
	
	public static class ClosedLoopDriveParams {
		public double kP, kD, kF;
		public double maxVelocity;
		
		public ClosedLoopDriveParams(double kP, double kD, double kF) {
			this.kP = kP;
			this.kD = kD;
			this.kF = kF;
			maxVelocity = 1023.0 / kF;
		}
	}
}