package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.BooleanPublisher;

import edu.wpi.first.wpilibj.Servo;

public class LEDandServoSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  
  //led and servo
  AddressableLED led;
  AddressableLEDBuffer ledBuffer;
  Servo servo;

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
  public LEDandServoSubsystem(CommandXboxController controller) {

    exampleController = controller;

    led = new AddressableLED(1);
    ledBuffer = new AddressableLEDBuffer(24);
    led.setLength(ledBuffer.getLength());
    for(int i= 0; i < ledBuffer.getLength(); i++) {
      ledBuffer.setRGB(i, 0, 0, 0);
    }
    led.setData(ledBuffer);
    led.start();

    servo = new Servo(0);
  }
  
  //new led command
  public Command commandLED()
  {

    return new FunctionalCommand(

      // ** INIT
      ()-> testInit1(),
     
      // ** EXECUTE
      ()-> testExecute1(),
     
      // ** ON INTERRUPTED
      interrupted-> testInterrupt1(),
     
      // ** END CONDITION
      ()-> testEndCondition1(),

      // ** REQUIREMENTS
      this);

  }

  private void testInit1()
  {
  
  }

  private void testExecute1()
  {

    if(exampleController.getHID().getXButton()) {
      
      ledBuffer.setRGB(0, 255, 0, 0);
      ledBuffer.setRGB(1, 255, 165, 0);
      ledBuffer.setRGB(2, 255, 255, 0);
      ledBuffer.setRGB(3, 0, 0, 255);
      ledBuffer.setRGB(4, 255, 0, 255);
      led.setData(ledBuffer);
    }
    else if(exampleController.getHID().getBButton()) {
      for(int i= 0; i < ledBuffer.getLength(); i++) {
      ledBuffer.setRGB(i, 255, 0, 0);
      }
      led.setData(ledBuffer);
    }
    else if(exampleController.getHID().getAButton()) {
      for(int i= 0; i < ledBuffer.getLength(); i++) {
      ledBuffer.setRGB(i, 0, 255, 0);
      }
      led.setData(ledBuffer);
    }
  }

  private void testInterrupt1()
  {
    xButton.set(false);
    for(int i= 0; i < ledBuffer.getLength(); i++) {
      ledBuffer.setRGB(i, 0, 0, 0);
    }
    led.setData(ledBuffer);
  }

  private boolean testEndCondition1()
  {
    return(false);
  }

  //new servo command
  public Command servoCommand()
  {

    return new FunctionalCommand(

      // ** INIT
      ()-> testInit2(),
     
      // ** EXECUTE
      ()-> testExecute2(),
     
      // ** ON INTERRUPTED
      interrupted-> testInterrupt2(),
     
      // ** END CONDITION
      ()-> testEndCondition2(),

      // ** REQUIREMENTS
      this);

  }

  private void testInit2()
  {
    
  }

  private void testExecute2()
  {
    if(exampleController.getHID().getLeftBumper()){
      servo.set(1.0);
      for(int i= 0; i < ledBuffer.getLength(); i++) {
      ledBuffer.setRGB(i, 255, 33, 65);
      }
      led.setData(ledBuffer);
    }
    if(exampleController.getHID().getRightBumper()) {
      servo.set(0.0);
      for(int i= 0; i < ledBuffer.getLength(); i++) {
      ledBuffer.setRGB(i, 112, 255, 98);
      }
      led.setData(ledBuffer);
    }
  }

  private void testInterrupt2()
  {
    servo.set(0.5);
  }

  private boolean testEndCondition2()
  {
    return(false);
  }

  public boolean exampleCondition() {
    return false;
  }

  public Command speedCommand(DoubleSupplier target)
  {

    return new FunctionalCommand(

      // ** INIT
      ()-> testInit3(),
     
      // ** EXECUTE
      ()-> {
      double val = (target.getAsDouble() / 2 + 0.05);
      if(exampleController.getHID().getLeftBumper()){
      servo.set(val);
      } 
      if(exampleController.getHID().getRightBumper()) {
        servo.set(val);
      }
      },
     
      // ** ON INTERRUPTED
      interrupted-> testInterrupt3(),
     
      // ** END CONDITION
      ()-> testEndCondition3(),

      // ** REQUIREMENTS
      this);

  }

  /** to change color to red */
  private void testInit3()
  {
    //stciky
  }

  //private void testExecute3()
  //{
    //in command
  //}

  private void testInterrupt3()
  {
    //servo.set(0.5);
  }

  private boolean testEndCondition3()
  {
    return(true);
  }

  //servo on off commands
  public Command servo_On()
  {

    return new FunctionalCommand(

      // ** INIT
      ()-> testInit5(),
     
      // ** EXECUTE
      ()-> testExecute5(),
     
      // ** ON INTERRUPTED
      interrupted-> testInterrupt5(),
     
      // ** END CONDITION
      ()-> testEndCondition5(),

      // ** REQUIREMENTS
      this);

  }

  private void testInit5()
  {
  
  }

  private void testExecute5()
  {
    servo.set(0.75);
  }

  private void testInterrupt5()
  {
    //servo.set(0.5);
  }

  private boolean testEndCondition5()
  {
    return(true);
  }

  public Command servo_Off()
  {

    return new FunctionalCommand(

      // ** INIT
      ()-> testInit6(),
     
      // ** EXECUTE
      ()-> testExecute6(),
     
      // ** ON INTERRUPTED
      interrupted-> testInterrupt6(),
     
      // ** END CONDITION
      ()-> testEndCondition6(),

      // ** REQUIREMENTS
      this);

  }

  private void testInit6()
  {

  }

  private void testExecute6()
  {
    servo.set(0.5);
  }

  private void testInterrupt6()
  {
    servo.set(0.5);
  }

  private boolean testEndCondition6()
  {
    return(true);
  }

}