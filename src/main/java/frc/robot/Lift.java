/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Lift implements Subsystem, Brownout {

  private WPI_TalonSRX masterTalon;
  private WPI_VictorSPX followerTalon;
  private DoubleSolenoid tiltSolenoid;

  public Lift() {
    masterTalon = new WPI_TalonSRX(8);
    followerTalon = new WPI_VictorSPX(9);

    followerTalon.follow(masterTalon);

    masterTalon.setNeutralMode(NeutralMode.Brake);
    followerTalon.setNeutralMode(NeutralMode.Brake);

    masterTalon.configContinuousCurrentLimit(20, 10);
    masterTalon.configPeakCurrentLimit(0);

    tiltSolenoid = new DoubleSolenoid(1, 5, 7);
  }

  public void tiltForward() {
    tiltSolenoid.set(Value.kReverse);
  }

  public void tiltBack() {
    tiltSolenoid.set(Value.kForward);
  }

  public void move(double speed) {
    masterTalon.set(speed);
  }
  
  @Override
  public void periodic() {
  }

  @Override
  public void enableBrownout() {
    masterTalon.enableCurrentLimit(true);
  }

  @Override
  public void disableBrownout() {
    masterTalon.enableCurrentLimit(false);
  }
}
