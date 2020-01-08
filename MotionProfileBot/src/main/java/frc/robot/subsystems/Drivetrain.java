/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private WPI_VictorSPX leftTopMotor;
  private WPI_VictorSPX leftMiddleMotor;
  private WPI_TalonSRX leftBottomMotor;
  private WPI_VictorSPX rightTopMotor;
  private WPI_VictorSPX rightMiddleMotor;
  private WPI_TalonSRX rightBottomMotor;

  private DifferentialDrive differentialDrive; 

  private AHRS navX; 

  public Drivetrain() {
    /// Left Motors
    leftTopMotor = new WPI_VictorSPX(RobotMap.DRIVETRAIN_LEFT_TOP_MOTOR_PORT);
    leftMiddleMotor = new WPI_VictorSPX(RobotMap.DRIVETRAIN_LEFT_MIDDLE_MOTOR_PORT);
    leftBottomMotor = new WPI_TalonSRX(RobotMap.DRIVETRAIN_LEFT_BOTTOM_MOTOR_PORT);

    /// Right Motors
    rightTopMotor = new WPI_VictorSPX(RobotMap.DRIVETRAIN_RIGHT_TOP_MOTOR_PORT);
    rightMiddleMotor = new WPI_VictorSPX(RobotMap.DRIVETRAIN_RIGHT_MIDDLE_MOTOR_PORT);
    rightBottomMotor = new WPI_TalonSRX(RobotMap.DRIVETRAIN_RIGHT_BOTTOM_MOTOR_PORT);

    // Followers
    leftMiddleMotor.follow(leftBottomMotor);
    leftTopMotor.follow(leftBottomMotor);
    rightMiddleMotor.follow(rightBottomMotor);
    rightTopMotor.follow(rightBottomMotor);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public double getLeftEncoderDistance() {
    return leftBottomMotor.getSelectedSensorPosition(0) * RobotMap.DRIVETRAIN_RAW_MULTIPLIER;
  }

  public double getRightEncoderDistance() {
    return -1 * rightBottomMotor.getSelectedSensorPosition(0) * RobotMap.DRIVETRAIN_RAW_MULTIPLIER;
  }

  public double getDistance() {
    return Math.max(getLeftEncoderDistance(), getRightEncoderDistance());  
  }

  public void resetEncoders() {
    leftBottomMotor.setSelectedSensorPosition(0, 0, 100);
    rightBottomMotor.setSelectedSensorPosition(0, 0, 100);
  }

  public void tankDrive(double left, double right) {
    differentialDrive.tankDrive(left, right, false);
  }

  public void stop() {
    tankDrive(0, 0);
  }

  public double getGyroAngle() {
    return navX.getAngle();
  }

  public void resetGyroAngle() {
    navX.reset();
  }
  
}
