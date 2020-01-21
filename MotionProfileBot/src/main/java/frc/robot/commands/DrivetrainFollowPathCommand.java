/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.IOException;
import java.nio.file.Paths;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class DrivetrainFollowPathCommand extends CommandBase {
  /**
   * Creates a new DrivetrainFollowPathCommand.
   * 
   * This Command is a wrapper for WPIlib's Ramsete Command 
   */

  Drivetrain drivetrain; 

  Trajectory trajectory;
  
  RamseteCommand ramsete; 

  public DrivetrainFollowPathCommand(Drivetrain drivetrain, String path) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrain);

    try {
      trajectory = TrajectoryUtil.fromPathweaverJson(Paths.get("/home/lvuser/deploy/paths/" + path + ".wpilib.json"));
    }catch(IOException e) {
      end(false); 
    }
    
    ramsete = new RamseteCommand(
      trajectory, 
      drivetrain::getPose, 
      new RamseteController(Constants.kRamseteB, Constants.kRamseteZeta), 
      Constants.MOTOR_FEEDFORWARD, 
      Constants.DRIVE_KINEMATICS, 
      drivetrain::getWheelSpeeds, 
      new PIDController(Constants.kp, Constants.ki, Constants.kd), 
      new PIDController(Constants.kp, Constants.ki, Constants.kd), 
      drivetrain::tankDrive, 
      drivetrain
    ); 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    ramsete.initialize();

    drivetrain.resetEncoders();
    drivetrain.resetGyroAngle();
    drivetrain.resetOdometer();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ramsete.execute();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ramsete.end(interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return ramsete.isFinished();
  }
}
