/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.IOException;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.followers.DistanceFollower;

public class DrivetrainFollowPathCommand extends CommandBase {
  /**
   * Creates a new DrivetrainFollowPathCommand.
   */

  private DistanceFollower leftFollower;
  private DistanceFollower rightFollower; 
  
  private Notifier runner; 
  
  public DrivetrainFollowPathCommand(String pathName) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.m_robotContainer.drivetrain);

    try {
      leftFollower = new DistanceFollower(PathfinderFRC.getTrajectory(pathName + ".left"));
      rightFollower = new DistanceFollower(PathfinderFRC.getTrajectory(pathName + ".right")); 
    }catch(IOException e) {
      System.out.println("\033[31mERROR:\033[0m cannot find " + pathName + " in " + Filesystem.getDeployDirectory().toString() + "/paths");
      cancel();  
    }

    runner = new Notifier(this::run); 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.m_robotContainer.drivetrain.resetEncoders();
    Robot.m_robotContainer.drivetrain.resetGyroAngle();
    leftFollower.reset();
    rightFollower.reset();

    double kp = SmartDashboard.getNumber("kp", 0); 
    double ki = SmartDashboard.getNumber("ki", 0); 
    double kd = SmartDashboard.getNumber("kd", 0); 
    leftFollower.configurePIDVA(kp, ki, kd, 1 / Constants.MAX_VELOCITY, 0);
    rightFollower.configurePIDVA(kp, ki, kd, 1 / Constants.MAX_VELOCITY, 0);

    runner.startPeriodic(leftFollower.getSegment().dt);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.m_robotContainer.drivetrain.stop(); 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public void run() {
    if(leftFollower.isFinished() || rightFollower.isFinished()) {
      runner.stop();
      end(false);  
    }

    double left = leftFollower.calculate(Robot.m_robotContainer.drivetrain.getLeftEncoderDistance() / 12.0); 
    double right = leftFollower.calculate(Robot.m_robotContainer.drivetrain.getRightEncoderDistance() / 12.0); 

    //convert from clockwise to counter-clockwise 
    double rawHeading = Pathfinder.boundHalfDegrees(Robot.m_robotContainer.drivetrain.getGyroAngle());
    double currentHeading = Pathfinder.boundHalfDegrees(360.0 - rawHeading); 
    double targetHeading = Pathfinder.r2d(leftFollower.getHeading());
    double headingError = Pathfinder.boundHalfDegrees(targetHeading - currentHeading);
    //pid on heading error 
    double turn = 0.8 * (-1.0/80.0) * headingError; 
    
    Robot.m_robotContainer.drivetrain.tankDrive(left - turn, right + turn);
  }
}
