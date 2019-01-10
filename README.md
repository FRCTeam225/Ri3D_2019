# Ri3D_2019

## Final Thoughts

1. **OI** Our OI could have been improved with more time. Currently presets are bound to multiple buttons on a F310 gamepad. With a little more time we would have tried some system where you have an up/down button. This would let you sequentially step through setpoints (i.e ground, level1, level2, level3) with only two buttons, maybe with an additional selector for cargo/hatch scoring.

1. **SRX Control** There was some uncertainty in which motors would be used where, so I opted to do all control on the RIO. For the motors that are connected to Talon SRX (or paired with a SRX following a Victor SPX) with sensors, motion magic could have accomplished the same thing.

1. **Profile generation** The motion profiler we used was designed for use on the drivetrain (created in 2015). A profiler that handles live changes to the goal position would be better suited for use on actuators like this.

1. **Tuning** The elevator is only using a P term to track the profile due to some last minute gearbox changes requiring a retune. There is also a gearbox issue on the Arm that resulted in an excessive amount of chain slop, making repeatability of setpoints difficult.

1. **Dashboard** For live tuning, we used a RIO-based web application we built in 2016. If you're looking to do the same thing, using Shuffle/Smart Dashboard is a much more well supported route for the average team.

1. **Encoders** The elevator/arm/wrist all use incremental encoders. Ideally an implementation would use some absolute sensor (available with the VP integrated encoder, but we had the rollover position inside our range of movement)

The good news is you have 6 weeks to do better :)
