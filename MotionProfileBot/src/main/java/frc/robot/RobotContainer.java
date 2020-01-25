/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DrivetrainFollowPathCommand;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain drivetrain; 

  private final Joystick driverGamepad; 

  private final SendableChooser<Command> chooser;

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    drivetrain = new Drivetrain(); 
    
    driverGamepad = new Joystick(Constants.DRIVER_GAMEPAD_PORT); 

    // Configure the button bindings
    configureButtonBindings();

    chooser = new SendableChooser<>();
    addAutonomousCommands();
    SmartDashboard.putData(chooser);
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  }

  private void addAutonomousCommands() {
    // TODO: add various routines
    chooser.addOption("Straight", new DrivetrainFollowPathCommand(drivetrain, "Straight"));
    chooser.addOption("Quad", new DrivetrainFollowPathCommand(drivetrain, "Quad"));
    chooser.addOption("Snake", new DrivetrainFollowPathCommand(drivetrain, "Snake")); 
    chooser.addOption("Double", new DrivetrainFollowPathCommand(drivetrain, "Double"));
    chooser.addOption("Circle", new DrivetrainFollowPathCommand(drivetrain, "Circle"));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return chooser.getSelected();
  }

  public void updateSmartDashboard() {
    SmartDashboard.putNumber("Left Distance", drivetrain.getLeftEncoderDistance()); 
    SmartDashboard.putNumber("Right Distance", drivetrain.getRightEncoderDistance()); 

    SmartDashboard.putNumber("Left Velocity", drivetrain.getLeftEncoderVelocity());
    SmartDashboard.putNumber("Right Velocity", drivetrain.getRightEncoderVelocity()); 

    SmartDashboard.putNumber("Angle (Clockwise)", drivetrain.getGyroAngleClockwise()); 

    SmartDashboard.putString("Pose", drivetrain.getPose().toString());
  }

  public Drivetrain getDrivetrain() {
    return drivetrain; 
  }

  public double getDriverLeftAxis() {
    return -driverGamepad.getRawAxis(1); 
  }

  public double getDriverRightAxis() {
    return -driverGamepad.getRawAxis(3); 
  }
}
