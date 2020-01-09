/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class DriveTrain implements Subsystem, Brownout{
  /**
   * Creates a new DriveTrain.
   */
  private CANSparkMax leftFrontMotor;
  private CANSparkMax leftRearMotor;
  private CANSparkMax rightFrontMotor;
  private CANSparkMax rightRearMotor;

  private SpeedControllerGroup leftMotors;
  private SpeedControllerGroup rightMotors;

  private DifferentialDrive driveTrain;
  public DriveTrain() {
    leftFrontMotor = new CANSparkMax(-1, MotorType.kBrushless);
    leftRearMotor = new CANSparkMax(-1, MotorType.kBrushless);
    rightFrontMotor = new CANSparkMax(-1, MotorType.kBrushless);
    rightRearMotor = new CANSparkMax(-1, MotorType.kBrushless);

    leftMotors = new SpeedControllerGroup(leftFrontMotor, leftRearMotor);
    rightMotors = new SpeedControllerGroup(rightFrontMotor, rightRearMotor);

    driveTrain = new DifferentialDrive(leftMotors, rightMotors);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void enableBrownout() {
    leftFrontMotor.setSmartCurrentLimit(65);
    leftRearMotor.setSmartCurrentLimit(65);
    rightFrontMotor.setSmartCurrentLimit(65);
    rightRearMotor.setSmartCurrentLimit(65);

  }

  @Override
  public void disableBrownout() {
    leftFrontMotor.setSmartCurrentLimit(0);
    leftRearMotor.setSmartCurrentLimit(0);
    rightFrontMotor.setSmartCurrentLimit(0);
    rightRearMotor.setSmartCurrentLimit(0);

  }

}
