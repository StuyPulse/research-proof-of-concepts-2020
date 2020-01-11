/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.DrivetrainDriveCommand;

public class Drivetrain extends SubsystemBase {
  
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
    leftTopMotor = new WPI_VictorSPX(Constants.DRIVETRAIN_LEFT_TOP_MOTOR_PORT);
    leftMiddleMotor = new WPI_VictorSPX(Constants.DRIVETRAIN_LEFT_MIDDLE_MOTOR_PORT);
    leftBottomMotor = new WPI_TalonSRX(Constants.DRIVETRAIN_LEFT_BOTTOM_MOTOR_PORT);

    /// Right Motors
    rightTopMotor = new WPI_VictorSPX(Constants.DRIVETRAIN_RIGHT_TOP_MOTOR_PORT);
    rightMiddleMotor = new WPI_VictorSPX(Constants.DRIVETRAIN_RIGHT_MIDDLE_MOTOR_PORT);
    rightBottomMotor = new WPI_TalonSRX(Constants.DRIVETRAIN_RIGHT_BOTTOM_MOTOR_PORT);

    // Followers
    leftMiddleMotor.follow(leftBottomMotor);
    leftTopMotor.follow(leftBottomMotor);
    rightMiddleMotor.follow(rightBottomMotor);
    rightTopMotor.follow(rightBottomMotor);

    /// Brake Mode
    leftTopMotor.setNeutralMode(NeutralMode.Brake);
    leftMiddleMotor.setNeutralMode(NeutralMode.Brake);
    leftBottomMotor.setNeutralMode(NeutralMode.Brake);
    rightTopMotor.setNeutralMode(NeutralMode.Brake);
    rightMiddleMotor.setNeutralMode(NeutralMode.Brake);
    rightBottomMotor.setNeutralMode(NeutralMode.Brake);

    leftBottomMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0); 
    rightBottomMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    
    differentialDrive = new DifferentialDrive(leftBottomMotor, rightBottomMotor); 

    navX = new AHRS(SPI.Port.kMXP);

    setDefaultCommand(new DrivetrainDriveCommand());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public double getLeftEncoderDistance() {
    return leftBottomMotor.getSelectedSensorPosition(0) * Constants.DRIVETRAIN_RAW_MULTIPLIER;
  }

  public double getRightEncoderDistance() {
    return -1 * rightBottomMotor.getSelectedSensorPosition(0) * Constants.DRIVETRAIN_RAW_MULTIPLIER;
  }

  public double getEncoderDistance() {
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
