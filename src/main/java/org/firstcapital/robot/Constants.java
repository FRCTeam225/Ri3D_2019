package org.firstcapital.robot;

public class Constants {

    private static Constants constants = new Constants();
    public static Constants getConstants() {
        // Swap out practice robot constants here
        return constants;
    }

    // Ticks / ft
    // 4x encoding
    // 256 ticks / rev
    // 1:3 from output shaft to encoder
    // 34:50 from gearbox output to drivetrain
    // 4in wheel
    public double DriveEncoder_TickConversion = (1.0/256.0)*(1.0/3.0)*(24.0/60.0)*(6.0*Math.PI);

    public double Drive_kP = 0.1;
    public double Drive_kF = 0.01;
    public double Drive_TurnHold_kP = 0.005;
    public double Drive_Turn_kP = 0;
    public double Drive_Turn_kD = 0;
    public double Drive_OkayError = 2;
    public double Drive_Turn_OkayError = 2;

    public double ElevatorMaxVelocity = 15000;
    public double ElevatorMaxAccel = 10000;
    public double Elevator_kP = 0.001;
    public double Elevator_kD = 0;
    public double Elevator_kF = 0;
    public double Elevator_kGravity = 0.01;

    public double WristMaxVelocity = 5000;
    public double WristMaxAccel = 6000;
    public double Wrist_kP = 0.0015;
    public double Wrist_kF = 0.00025;
    public double Wrist_kGravity = 0;

    public double ArmMaxVelocity = 10000;
    public double ArmMaxAccel = 1700;
    public double Arm_kP = 0.001;
    public double Arm_kD = 0;
    public double Arm_kF = 0.001;
    public double Arm_kGravity = 0;

    public double PickupBallArm = -400;
    public double PickupBallWrist = -4144;

    public double ScoreHatchWrist = -857;
    public double ScoreBallWrist = -1986;
    public double AlmostPickupHatchWrist = -3455;

    public double ShootingWrist = -1940;
    public double L2Arm = -2100;// 2000

    public double TopElevator = 7000; //7000

}
