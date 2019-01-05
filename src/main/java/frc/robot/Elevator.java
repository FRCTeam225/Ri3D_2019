package frc.robot;

import edu.wpi.first.wpilibj.*;
import org.techfire225.lib.motion.*;

public class Elevator {

    public final double maxVel = 0;
    public final double maxAcc = 0;

    public final double kP = 0;
    public final double kD = 0;
    public final double kF = 0;
    public final double kGravity = 0;

    Timer profileTimer = new Timer();
    TrapezoidalMotionProfile profile = null;

    Spark[] motors = new Spark[] { new Spark(PortMap.ELEVATOR_PWM[0]), new Spark(PortMap.ELEVATOR_PWM[1]), new Spark(PortMap.ELEVATOR_PWM[2]) };

    Encoder encoder = new Encoder(PortMap.ELEVATOR_ENCODER[0], PortMap.ELEVATOR_ENCODER[1]);

    public Elevator() {
    }

    public void set(double speed) {
        for ( Spark s : motors )
            s.set(speed);
    }

    public void goToGoal(double goal) {
        profile = new TrapezoidalMotionProfile(goal-encoder.getDistance(), maxVel, maxAcc);
        profileTimer.reset();
        profileTimer.start();
    }

    public void update() {
        if ( profile == null )
            set(0);
        else {
            ProfilePoint point = profile.getAtTime(profileTimer.get());
            double error = point.pos - encoder.getDistance();
            double output = point.vel * kF + error * kP + kGravity;
            set(output);
        }
    }
}