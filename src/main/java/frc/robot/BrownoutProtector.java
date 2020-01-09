/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.hal.PDPJNI;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class BrownoutProtector {
    private static PDPJNI pdp;
    private static final Subsystem[] priorityList = {Robot.driveTrain};
    
    public BrownoutProtector() {
        pdp = new PDPJNI();
    }

    public static boolean browningOut() {
        return pdp.getPDPVoltage(-1) < 7;
    }

    public static void priorityLimit() {
        
    }
    
}
