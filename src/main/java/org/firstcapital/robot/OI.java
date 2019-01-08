package org.firstcapital.robot;

import org.firstcapital.robot.commands.elevator.*;
import org.firstcapital.robot.commands.drivetrain.*;
import org.firstcapital.lib.framework.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
    public static final int A = 1;
    public static final int B = 2;
    public static final int X = 3;
    public static final int Y = 4;
    
    public static final int LB = 5;
    public static final int RB = 6;
    
    public static final int BACK = 7;
    public static final int START = 8;
	
	public static final int LS = 9;
	public static final int RS = 10;
	
	public static Joystick driver = new Joystick(0);
	public static Joystick operator = new Joystick(1);
	
	public static Button autoSelectUp = new JoystickButton(driver, B);
	public static Button autoSelectDown = new JoystickButton(driver, A);
	
	public static void init() {
        new AxisButton(driver, B, 0.5).whenPressed(new Shift(true));
        new AxisButton(driver, X, 0.5).whenPressed(new Shift(false));

        //new JoystickButton(driver, A).whenPressed(new DriveProfiledDistance(5*12.0, 0, 24, 12));
        //new JoystickButton(driver, A).whenPressed(new TurnTo(90));
        //new JoystickButton(operator, Y).whenPressed(new MoveElevator(5000));
        //new JoystickButton(operator, X).whenPressed(new MoveElevator(3000));
        //new JoystickButton(operator, Y).whenPressed(new MoveWrist(-3400));
        //new JoystickButton(operator, A).whenPressed(new MoveWrist(-1000));
        //new JoystickButton(operator, X).whenPressed(new MoveWrist(0));
        //new JoystickButton(operator, A).whenPressed(new MoveArm(0));
        //new JoystickButton(operator, Y).whenPressed(new MoveArm(-1200));
        //new JoystickButton(operator, B).whenPressed(new ManualElevator());
	}
}