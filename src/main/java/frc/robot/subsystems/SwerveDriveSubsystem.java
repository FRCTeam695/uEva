// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.lang.Math;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.BooleanPublisher;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkBase;

import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.DigitalInput;

import java.util.Scanner;

import javax.lang.model.util.ElementScanner14;
import javax.swing.JOptionPane;

import edu.wpi.first.math.MathUtil;

//swerve specific
import edu.wpi.first.wpilibj.Encoder;

import com.ctre.phoenix6.jni.CtreJniWrapper;
import com.ctre.phoenix6.hardware.ParentDevice;
import com.ctre.phoenix6.hardware.core.CoreTalonFX;
import com.ctre.phoenix6.hardware.TalonFX;

import com.ctre.phoenix6.jni.CtreJniWrapper;
import com.ctre.phoenix6.hardware.ParentDevice;
import com.ctre.phoenix6.hardware.core.CoreCANcoder;
import com.ctre.phoenix6.hardware.CANcoder;

import com.ctre.phoenix6.jni.CtreJniWrapper;
import com.ctre.phoenix6.hardware.ParentDevice;
import com.ctre.phoenix6.hardware.core.CoreCANcoder;

import edu.wpi.first.math.controller.PIDController;

public class SwerveDriveSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */

  //controller
  public CommandXboxController controller = new CommandXboxController(0);

  //publishers
  DoublePublisher xPub;
  DoublePublisher yPub;
  BooleanPublisher xButton;
  BooleanPublisher yButton;
  BooleanPublisher bButton;
  BooleanPublisher aButton;
  DoublePublisher xStick;
  BooleanPublisher rightBumper;
  BooleanPublisher leftBumper;

  //new motor
  CANSparkFlex motor;

  //swerve motors
  TalonFX speedMotor;
  TalonFX directionMotor;

  //swerve encoders
  CANcoder encoder;

  //values
  DoubleSupplier FWDvalue;
  DoubleSupplier STRvalue;
  DoubleSupplier RCWvalue;

  double length;
  double width;

  double R;

  //pid controller
  PIDController pidController;

  /** Creates a new ExampleSubsystem. */
  public SwerveDriveSubsystem() {

    // when the robot program starts

    pidController = new PIDController(0.005, 0, 0);

    //motors for swerve drive
    speedMotor = new TalonFX(33);
    directionMotor = new TalonFX(32);

    //endcoders
    encoder = new CANcoder(31);
      //directionMotor.set(pidController.calculate(encoder.getAbsolutePosition().getValueAsDouble()*360, 0));

    //lenght and width
    //temporary values
    //this should be half the original length and width
    length = 25.0/2;
    width = 25.0/2;

    R = Math.sqrt(Math.pow(length, 2) + Math.pow(width, 2));

    pidController.enableContinuousInput(-180.0, 180.0);

    encoder.setPosition(0);
  }

  //final calculated forward value
  public double finalFWD(DoubleSupplier FWD, DoubleSupplier STR, DoubleSupplier RCW) {

    double RCW_FWD = RCW.getAsDouble() * (length/R);

    SmartDashboard.putNumber("RCW_FWD", RCW_FWD);
    SmartDashboard.putNumber("finalFWD", FWD.getAsDouble() + RCW_FWD);
    return FWD.getAsDouble() + RCW_FWD;
  }

  //finla calculated strafe value
  public double finalSTR(DoubleSupplier FWD, DoubleSupplier STR, DoubleSupplier RCW) {

    double RCW_STR = RCW.getAsDouble() * (width/R);

    SmartDashboard.putNumber("finalSTR", STR.getAsDouble() - RCW_STR);
    SmartDashboard.putNumber("RCW_STR", RCW_STR);
     return STR.getAsDouble() - RCW_STR;
  }

  //calculated speed
  public double speed(DoubleSupplier FWD, DoubleSupplier STR, DoubleSupplier RCW) {
      return Math.sqrt(Math.pow(finalFWD(FWD, STR, RCW), 2) + Math.pow(finalSTR(FWD, STR, RCW),2));
  }

  //calculated steering angle
  public double steeringAngle(DoubleSupplier FWD, DoubleSupplier STR, DoubleSupplier RCW){
    return Math.atan2(finalSTR(FWD, STR, RCW),finalFWD(FWD, STR, RCW)) * 180/Math.PI;
  }

  public Command runSwerveCommand(DoubleSupplier FWD, DoubleSupplier STR, DoubleSupplier RCW)
  {

    return new RunCommand(

      // ** EXECUTE
      ()-> 
       {
        if((-0.05 < RCW.getAsDouble() && RCW.getAsDouble() < 0.05) && (-0.1 < FWD.getAsDouble() && FWD.getAsDouble() < 0.1) && (-0.1 < STR.getAsDouble() && STR.getAsDouble() < 0.1)) {
          speedMotor.set(0);
          directionMotor.set(0);
        }
        else {
        if(-0.1 < speed(FWD, STR, RCW) && speed(FWD, STR, RCW) < 0.1)
          speedMotor.set(0);
        else
          if(speed(FWD, STR, RCW) < 0)
            speedMotor.set(speed(FWD, STR, RCW) * -1);
          else
            speedMotor.set(speed(FWD, STR, RCW));

        if(-0.1 < pidController.calculate((encoder.getAbsolutePosition().getValueAsDouble()*360), steeringAngle(FWD, STR, RCW)) && pidController.calculate((encoder.getAbsolutePosition().getValueAsDouble()*360), steeringAngle(FWD, STR, RCW)) < 0.1)
          directionMotor.set(0);
        else
          directionMotor.set(pidController.calculate((encoder.getAbsolutePosition().getValueAsDouble()*360), steeringAngle(FWD, STR, RCW)));
        }

        SmartDashboard.putNumber("speed motor val", speedMotor.get());
        SmartDashboard.putNumber("steering motor val", directionMotor.get());

        SmartDashboard.putNumber("encoder value", encoder.getAbsolutePosition().getValueAsDouble());
        SmartDashboard.putNumber("FWD", FWD.getAsDouble());
        SmartDashboard.putNumber("STR", STR.getAsDouble());
        SmartDashboard.putNumber("RCW", RCW.getAsDouble());

        SmartDashboard.putNumber("speed", speed(FWD, STR, RCW));
        SmartDashboard.putNumber("steering angle", steeringAngle(FWD, STR, RCW));
      }
      ,this
    );
  }

  @Override
  public void periodic() {
  
  }

}