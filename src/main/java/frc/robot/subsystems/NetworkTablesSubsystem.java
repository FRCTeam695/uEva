// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class NetworkTablesSubsystem extends SubsystemBase {
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

  /** Creates a new ExampleSubsystem. */
  public NetworkTablesSubsystem(CommandXboxController controller) {

    // Get the default instance of NetworkTables that was created automatically
    // when the robot program starts
    NetworkTableInstance inst = NetworkTableInstance.getDefault();

    // Get the table within that instance that contains the data. There can
    // be as many tables as you like and exist to make it easier to organize
    // your data. In this case, it's a table called datatable.
    NetworkTable table = inst.getTable("datatable");

    // Start publishing topics within that table that correspond to the X and Y values
    // for some operation in your program.
    // The topic names are actually "/datatable/x" and "/datatable/y".
    xPub = table.getDoubleTopic("x").publish();
    yPub = table.getDoubleTopic("y").publish();
    xButton = table.getBooleanTopic("xButton").publish();
    xStick = table.getDoubleTopic("xStick").publish();
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

  @Override
  public void periodic() {

    //motor pid calculation method

    //Publish values that are constantly increasing.
    xPub.set(x);
    yPub.set(y);

    //changes values by 1 when A is pressed
    if(exampleController.getHID().getAButton()){
        x += 1.0;
        y += 1.0;
        
    }

    //changes values by 0.5 when B is pressed
    if(exampleController.getHID().getBButton()){
        x += 0.5;
        y += 0.5;
    }

    //changes values by 100 when Y is pressed
    if(exampleController.getHID().getYButton()){
        x += 100.0;
        y += 100.0;
    }

    //changes values to 0 when X is pressed
    if(exampleController.getHID().getXButton()){
        x = 0.0;
        y = 0.0;
       }

    if(exampleController.getHID().getLeftBumper()){
        x = 695;
        y = 695;

    }
  }

}

