/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    /**************************************************************************
     * Gamepad Ports
     *************************************************************************/
    public static final int DRIVER_GAMEPAD_PORT = 0;
    public static final int OPERATOR_GAMEPAD_PORT = 1;

    /**************************************************************************
     * Drivetrain Motor Ports
     *************************************************************************/
    public static final int DRIVETRAIN_LEFT_TOP_MOTOR_PORT = 3;
    public static final int DRIVETRAIN_LEFT_MIDDLE_MOTOR_PORT = 2;
    public static final int DRIVETRAIN_LEFT_BOTTOM_MOTOR_PORT = 1;

    public static final int DRIVETRAIN_RIGHT_TOP_MOTOR_PORT = 6;
    public static final int DRIVETRAIN_RIGHT_MIDDLE_MOTOR_PORT = 5;
    public static final int DRIVETRAIN_RIGHT_BOTTOM_MOTOR_PORT = 4;

    /******************************************************************************
     * Drivetrain Encoder/Movement Constants
     *****************************************************************************/
    public static final double DRIVETRAIN_WHEEL_DIAMETER = 6.0;
    public static final int DRIVETRAIN_ENCODERS_PULSES_PER_REVOLUTION = 256;
    public static final int DRIVETRAIN_ENCODERS_FACTOR = 4;
    public static final double DRIVETRAIN_ENCODERS_INCHES_PER_REVOLUTION = Math.PI * DRIVETRAIN_WHEEL_DIAMETER;

    private static final double DRIVETRAIN_EMPERICAL_RAW_MULTIPLIER = (63.7 / 63.0) * 61.1 / ((463.544 + 461.814) / 2.0);
    
    public static final double DRIVETRAIN_RAW_MULTIPLIER = 
      DRIVETRAIN_EMPERICAL_RAW_MULTIPLIER * DRIVETRAIN_ENCODERS_INCHES_PER_REVOLUTION
      / (DRIVETRAIN_ENCODERS_PULSES_PER_REVOLUTION * DRIVETRAIN_ENCODERS_FACTOR);

}
