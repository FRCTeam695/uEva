// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.TankDriveSubsystem;
import frc.robot.subsystems.ArcadeDriveSubsystem;
import frc.robot.subsystems.MotorSubsystem;
import frc.robot.subsystems.SwerveDriveSubsystem;
import frc.robot.subsystems.LEDandServoSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

//import edu.wpi.first.wpilibj.AddressableLED;
//import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  
  public static Joystick driverController = new Joystick(0);

    // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  // The robot's subsystems and commands are defined here...
  private final MotorSubsystem m_motorSubsystem = new MotorSubsystem(m_driverController);

  private final LEDandServoSubsystem m_LEDandServoSubsystem = new LEDandServoSubsystem(m_driverController);

  private final SwerveDriveSubsystem m_SwerveDriveSubsystem = new SwerveDriveSubsystem();

  //private final TankDriveSubsystem m_TankDriveSubsystem = new TankDriveSubsystem(m_driverController);

  //private final ArcadeDriveSubsystem m_ArcadeDriveSubsystem = new ArcadeDriveSubsystem(m_driverController);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    //new Trigger(m_LEDandServoSubsystem::exampleCondition)
        //.onTrue(new ExampleCommand(m_LEDandServoSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    
    m_driverController.x().whileTrue(m_LEDandServoSubsystem.commandLED());
    m_driverController.b().whileTrue(m_LEDandServoSubsystem.commandLED());
    m_driverController.a().whileTrue(m_LEDandServoSubsystem.commandLED());
    m_driverController.leftBumper().whileTrue(m_LEDandServoSubsystem.servoCommand());
    m_driverController.rightBumper().whileTrue(m_LEDandServoSubsystem.servoCommand());
    m_driverController.start().onTrue(m_motorSubsystem.motorCommand(()-> m_driverController.getRawAxis(0)));
    m_driverController.start().onTrue(m_motorSubsystem.pidMotorCommand());

    m_driverController.y().onTrue(
        m_LEDandServoSubsystem.servo_On()
        .andThen(new WaitCommand(2.0))
        .andThen(m_LEDandServoSubsystem.servo_Off())
        .andThen(new WaitCommand(2.0))
        .andThen(m_LEDandServoSubsystem.servo_On())
        .andThen(new WaitCommand(2.0))
        .andThen(m_LEDandServoSubsystem.servo_Off())
    );

    //swerve drive
    m_SwerveDriveSubsystem.setDefaultCommand(
      m_SwerveDriveSubsystem.runSwerveCommand(
        () -> m_driverController.getRawAxis(1),
        () -> m_driverController.getRawAxis(0), 
        () -> m_driverController.getRawAxis(4)
      )
    );

    //arcade drive
    /*m_ArcadeDriveSubsystem.setDefaultCommand(
      m_ArcadeDriveSubsystem.arcadeDriveCommand(
        () -> m_ArcadeDriveSubsystem.leftJoystick.getRawAxis(1), () -> m_ArcadeDriveSubsystem.rightJoystick.getRawAxis(2)
      )
      );
    */

    //tank drive
    /* m_TankDriveSubsystem.setDefaultCommand(
      m_TankDriveSubsystem.tankDriveCommand(
        () -> m_DriveSubsystem.leftJoystick.getRawAxis(1), () -> m_DriveSubsystem.rightJoystick.getRawAxis(1)
      )
    );
    */

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_motorSubsystem);
  }
  }