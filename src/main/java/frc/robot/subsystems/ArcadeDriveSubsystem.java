// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import java.util.function.DoubleSupplier;

import java.lang.Object;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


public class ArcadeDriveSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */

  //controller
  CommandXboxController exampleController = new CommandXboxController(0); // Creates an XboxController on port 0.
  CommandXboxController controller;

  //drivetrain motors

  //right motors
  private final CANSparkMax rightMotor1 = new CANSparkMax(11, MotorType.kBrushless);
  private final CANSparkMax rightMotor2 = new CANSparkMax(13, MotorType.kBrushless);

  //left motors
  private final CANSparkMax leftMotor1 = new CANSparkMax(10, MotorType.kBrushless);
  private final CANSparkMax leftMotor2 = new CANSparkMax(12, MotorType.kBrushless);

  //differential drive
  private DifferentialDrive robotDrive;

  //joysticks
  //ports might be INCORRECT
  public Joystick leftJoystick = new Joystick(0);
  public Joystick rightJoystick = new Joystick(1);

  /** Creates a new ExampleSubsystem. */
  public ArcadeDriveSubsystem(CommandXboxController controller) {

    //set all to follow leftMotor1
    leftMotor2.follow(leftMotor1);
    rightMotor1.follow(leftMotor1);
    rightMotor2.follow(leftMotor1);

    //sets right to inverted
    rightMotor1.setInverted(true);
    
    //sets differential drive to take leftMotor1 and rightMotor1 values
    robotDrive = new DifferentialDrive(leftMotor1, rightMotor1);
  }
  
  //drive train command (arcade drive)
  public Command arcadeDriveCommand(DoubleSupplier speed, DoubleSupplier rotation)
  {
    return new RunCommand(

    // ** EXECUTE
    () -> {
      double driveSpeed = speed.getAsDouble();
      double driveRotation = rotation.getAsDouble();

      System.out.println(driveSpeed);
      System.out.println(driveRotation);

      robotDrive.arcadeDrive(driveSpeed, driveRotation);
    }
    ,this
    );
  }
}