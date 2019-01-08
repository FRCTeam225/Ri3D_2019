package org.firstcapital.robot.subsystems;

import edu.wpi.first.wpilibj.*;

public class Pegs {
    Solenoid front = new Solenoid(4);
    Solenoid back = new Solenoid(3);
    Solenoid ejector = new Solenoid(0);

    public Pegs() {
        
    }

    public void set(boolean f, boolean b) {
        front.set(f);
        back.set(b);
    }

    public void setEjector(boolean s) {
        ejector.set(s);
    }
}