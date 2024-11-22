// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
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


public class MotorSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  
  //ints and doubles
  double x = 0;
  double y = 0;
  int maxcnt = 50;
  int cnt = 0;

  //controller
  CommandXboxController exampleController = new CommandXboxController(0); // Creates an XboxController on port 0.
  CommandXboxController controller;

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

  //pid
  SparkPIDController pidController;

  //joysticks
  Joystick rightJoystick = new Joystick(0);
  Joystick leftJoystick = new Joystick(1);

  //pid variables
  double setPoint = 0;
  double kP = 0.00005;
  double kI = 0;
  double kD = 0;
  double kIz = 0;
  double kMinOutput = -1;
  double kMaxOutput = 1;
  double kF = 0.000151;

  //limit switch variables
  DigitalInput limitSwitch = new DigitalInput(0);


  /** Creates a new ExampleSubsystem. */
  public MotorSubsystem(CommandXboxController controller) {

    // Get the default instance of NetworkTables that was created automatically
    // when the robot program starts
    exampleController = controller;

    //motor = new CANSparkFlex(55, MotorType.kBrushless);
    //pidController = motor.getPIDController();

  }
  
  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean buttonPressed() {
    // Query some boolean state, such as a digital sensor.
      return false;
  }

  //motor commands
  public Command motorCommand(DoubleSupplier target)
  {

    return new FunctionalCommand(

      // ** INIT
      ()-> testInit7(),
     
      // ** EXECUTE
      ()-> {
      if(!limitSwitch.get()) {
        motor.set(0);
        System.out.println("the limit switch is on");
      }
      else {
        motor.set(target.getAsDouble());
        System.out.println("the limit switch is off");
      }
      },
     
      // ** ON INTERRUPTED
      interrupted-> testInterrupt7(),
     
      // ** END CONDITION
      ()-> testEndCondition7(),

      // ** REQUIREMENTS
      this);

  }

  private void testInit7()
  {}

  private void testInterrupt7()
  {
    motor.set(0);
  }

  private boolean testEndCondition7()
  {
    return(false);
  }

  //pid commands
  public Command pidMotorCommand()
  {

    return new FunctionalCommand(

      // ** INIT
      ()-> testInit8(),
     
      // ** EXECUTE
      ()-> testExecute8(),
     
      // ** ON INTERRUPTED
      interrupted-> testInterrupt8(),
     
      // ** END CONDITION
      ()-> testEndCondition8(),

      // ** REQUIREMENTS
      this);

  }

  private void testInit8()
  {}

  private void testExecute8()
  {
    if(limitSwitch.get()) {
    // Set the pid controller values
    pidController.setP(kP);
    pidController.setI(kI);
    pidController.setD(kD);
    pidController.setIZone(kIz);
    // Set kFF
    pidController.setFF(kF);

    // Set the minimum and maximum outputs of the motor
    pidController.setOutputRange(kMinOutput, kMaxOutput);

    pidController.setReference(2000, CANSparkBase.ControlType.kVelocity);
    SmartDashboard.putNumber("Motor Speed", motor.getEncoder().getVelocity());
    }

    if(!limitSwitch.get()){
      motor.set(0);
    }

  }

  private void testInterrupt8()
  {
    motor.set(0);
  }

  private boolean testEndCondition8()
  {
    if(!limitSwitch.get())
      return (true);
    return(false);
  }

  @Override
  public void periodic() {
  
    //motor pid calculation method

  }

}
