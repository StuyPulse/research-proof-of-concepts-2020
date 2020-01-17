/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Subsystem;

public class Drivetrain implements Subsystem, Brownout {

  private CANSparkMax leftTopMotor,
                      leftMiddleMotor,
                      leftBottomMotor,
                      rightTopMotor,
                      rightMiddleMotor,
                      rightBottomMotor;

  private SpeedControllerGroup leftMotors, rightMotors;

  private DifferentialDrive differentialDrive;

  private Solenoid solenoid;

  public Drivetrain() {
    
    leftTopMotor = new CANSparkMax(2, MotorType.kBrushless);
    leftMiddleMotor = new CANSparkMax(3, MotorType.kBrushless);
    leftBottomMotor = new CANSparkMax(1, MotorType.kBrushless);
    rightTopMotor = new CANSparkMax(6, MotorType.kBrushless);
    rightMiddleMotor = new CANSparkMax(7, MotorType.kBrushless);
    rightBottomMotor = new CANSparkMax(5, MotorType.kBrushless);

    leftTopMotor.setInverted(true);
    leftMiddleMotor.setInverted(true);
    leftBottomMotor.setInverted(true);
    rightTopMotor.setInverted(true);
    rightMiddleMotor.setInverted(true);
    rightBottomMotor.setInverted(true);

    leftTopMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
    leftMiddleMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    leftBottomMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
    rightTopMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
    rightMiddleMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
    rightBottomMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);

    leftMotors = new SpeedControllerGroup(leftTopMotor, leftMiddleMotor, leftBottomMotor);
    rightMotors = new SpeedControllerGroup(rightTopMotor, rightMiddleMotor, rightBottomMotor);
  
    differentialDrive = new DifferentialDrive(leftMotors, rightMotors);

    solenoid = new Solenoid(0);
  }

  public void tankDrive(double left, double right) {
    differentialDrive.tankDrive(left, right);
  }

  public void setLowGear() {
    solenoid.set(true);
  }

  public void setHighGear() {
    solenoid.set(false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void enableBrownout() {
    leftTopMotor.setSmartCurrentLimit(65);
    leftMiddleMotor.setSmartCurrentLimit(65);
    leftBottomMotor.setSmartCurrentLimit(65);
    rightTopMotor.setSmartCurrentLimit(65);
    rightMiddleMotor.setSmartCurrentLimit(65);
    rightBottomMotor.setSmartCurrentLimit(65);
  }

  @Override
  public void disableBrownout() {
    leftTopMotor.setSmartCurrentLimit(0);
    leftMiddleMotor.setSmartCurrentLimit(0);
    leftBottomMotor.setSmartCurrentLimit(0);
    rightTopMotor.setSmartCurrentLimit(0);
    rightMiddleMotor.setSmartCurrentLimit(0);
    rightBottomMotor.setSmartCurrentLimit(0);

  }

}
