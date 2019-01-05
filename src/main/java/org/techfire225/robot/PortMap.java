package org.techfire225.robot;

public class PortMap {
	
	// drivetrain
	public static final int[] LEFT_DRIVE = {1,2,3}; // CAN
	public static final int[] RIGHT_DRIVE = {4,5,6}; // CAN
	public static final int[] LEFT_DRIVE_ENCODER = {0,1}; // DIO
	public static final int[] RIGHT_DRIVE_ENCODER = {2,3}; // DIO
	public static final int SHIFTER_SOLENOID = 0; // PCM
	
	// shooter
	public static final int[] SHOOTER = {9,10}; // CAN
	public static final int SHOOTER_HOOD = 5; // PCM
	
	// hopper 
	public static final int INTAKE = 7; // CAN
	public static final int CLIMBER = 8;
	public static final int FLOOR = 12; // CAN
	public static final int FEEDER = 11; // CAN
	public static final int GEAR_FLAP = 2; // PCM
	public static final int GEAR_DOOR = 3; // PCM
	public static final int FUEL_FLAP = 1; // PCM
}