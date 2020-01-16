/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Subsystem;

public class Acquirer implements Subsystem, Brownout {
  
  private CANSparkMax motor;
  private boolean limitOn;

  public Acquirer() {
    motor = new CANSparkMax(10, MotorType.kBrushless);
    motor.setInverted(true);
    motor.setIdleMode(IdleMode.kBrake);
    limitOn = false;
  }

  @Override
  public void periodic() {
  }

  public void setSpeed(double speed) {
    if(limitOn && Math.abs(speed) > .5)
      motor.set(Math.signum(speed) * .5);
    else
      motor.set(speed);
  }

  @Override
  public void enableBrownout() {
    limitOn = true;
  }

  @Override
  public void disableBrownout() {
    limitOn = false;
  }
}
