/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class Acquirer implements Subsystem, Brownout {
  
  private WPI_VictorSPX motor;

  public Acquirer() {
    motor = new WPI_VictorSPX(10);
    motor.setInverted(true);
    motor.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void periodic() {
  }

  public void setSpeed(double speed) {
    motor.set(speed);
  }

  @Override
  public void enableBrownout() {
    
  }

  @Override
  public void disableBrownout() {
	
  }
}
