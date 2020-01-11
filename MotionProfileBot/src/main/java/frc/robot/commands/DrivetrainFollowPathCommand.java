/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.io.IOException;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.followers.DistanceFollower;

public class DrivetrainFollowPathCommand extends CommandBase {
  /**
   * Creates a new DrivetrainFollowPathCommand.
   */

  private DistanceFollower leftFollower;
  private DistanceFollower rightFollower;
  
  private Notifier loop;

  public DrivetrainFollowPathCommand(String path) throws IOException {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.m_robotContainer.drivetrain);

    leftFollower = new DistanceFollower(PathfinderFRC.getTrajectory(path + ".left"));
    rightFollower = new DistanceFollower(PathfinderFRC.getTrajectory(path + ".right"));

    loop = new Notifier(this::follow); 

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  private void follow() {
  }
}
