/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Timer;

public class BrownoutProtector implements Runnable{

    private static final Brownout[] priorityList = {Robot.acquirer, Robot.lift, Robot.drivetrain};
    private static int counterLow;
    private static int counterHigh;
    private static int index;

    public BrownoutProtector() {
        counterLow = 0;
        counterHigh = 0;
        index = 0;
    }

    public static boolean voltageLow() {
        if(Robot.pdp.getVoltage() < 10) { //10 corresponds to RobotMap.java constant LOW_VOLTAGE
            counterLow = 0;
            return false;
        }
        counterLow++;
        if(counterLow >= 3)
            return true;
        return false;
    }

    public static boolean voltageSafe() {
        if(Robot.pdp.getVoltage() > 11) { //11 corresponds to RobotMap.java constant SAFE_VOLTAGE
            counterHigh = 0;
            return false;
        }
        counterHigh++;
        if(counterHigh >= 3)
            return true;
        return false;
    }

    //TODO: get rid of all statics
    public static void priorityLimit() {
        while(true) {
            while(voltageLow() && (index < priorityList.length)) {
                priorityList[index].enableBrownout();
                index++;
                Timer.delay(.5);
            }
            while(voltageSafe() && (index > 0)) {
                index--;
                priorityList[index].disableBrownout();
                Timer.delay(.5);
            }
            Timer.delay(.5);
        }
    }

    @Override
    public void run() {
        priorityLimit();
    }
    
}
