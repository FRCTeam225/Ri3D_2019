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
    public double DriveEncoder_TickConversion = (1.0/4.0)*(1.0/256.0)*(1.0/3.0)*(24.0/60.0)*(6.0*Math.PI);

    public double Drive_kP = 0.1;
    public double Drive_kF = 0;
    public double Drive_TurnHold_kP = 0;
    public double Drive_Turn_kP = 0;
    public double Drive_Turn_kD = 0;
    public double Drive_OkayError = 2;
    public double Drive_Turn_OkayError = 5;

    public double ElevatorMaxVelocity = 15000;
    public double ElevatorMaxAccel = 20000;
    public double Elevator_kP = 0.0008;
    public double Elevator_kD = 0;
    public double Elevator_kF = 0.00004;
    public double Elevator_kGravity = 0.09;

    public double ArmMaxVelocity = 0;
    public double ArmMaxAccel = 0;
    public double Arm_kP = 0;
    public double Arm_kD = 0;
    public double Arm_kF = 0;
    public double Arm_kGravity = 0;
}
