package org.firstcapital.robot;

import java.io.File;

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
    // 12 inches -> 1 ft
    //public double DriveEncoder_TickConversion = (1.0/4.0)*(1.0/256.0)*(1.0/3.0)*(34.0/50.0)*(4.0*Math.PI)*(1.0/12.0);

    public double SomeTunableConstant = 0;
    public double AnotherConstant = 0;
}
