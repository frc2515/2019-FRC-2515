// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2515.ENIGMA;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.*;
import org.usfirst.frc2515.ENIGMA.commands.*;
import org.usfirst.frc2515.ENIGMA.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static driveTrain driveTrain;
    public static lift lift;
    public static intake intake;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static boolean isAutoPilotEnabled;
    public static boolean isAutoPilotEngaged;
    public static boolean isCargoLoaded;
    public static boolean isHatchPanelLoaded;
    public static double accelerateMultiplier;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrain = new driveTrain();
        lift = new lift();
        intake = new intake();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        isAutoPilotEnabled = true;
        isAutoPilotEngaged = false;
        isCargoLoaded = false;
        isHatchPanelLoaded = false;
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // Add commands to Autonomous Sendable Chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        chooser.setDefaultOption("Autonomous Command", new AutonomousCommand());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        SmartDashboard.putData("Auto mode", chooser);
        
        SmartDashboard.putBoolean("Auto Pilot Enabled", isAutoPilotEnabled);
        SmartDashboard.putBoolean("Auto Pilot Engaged", isAutoPilotEngaged);
        SmartDashboard.putBoolean("Cargo Loaded", isCargoLoaded);
        SmartDashboard.putBoolean("Panel Loaded", isHatchPanelLoaded);
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit(){

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = chooser.getSelected();
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        accelerateMultiplier = (Robot.oi.driverStick.getRawAxis(3) + 3)*.25;
        SmartDashboard.putNumber("Accelerate Multiplier", accelerateMultiplier);
        SmartDashboard.putNumber("Raw Drag", Robot.oi.driverStick.getRawAxis(3));
        SmartDashboard.putBoolean("Auto Pilot Enabled", isAutoPilotEnabled);
        SmartDashboard.putBoolean("Auto Pilot Engaged", isAutoPilotEngaged);
        SmartDashboard.putBoolean("Cargo Loaded", isCargoLoaded);
        SmartDashboard.putBoolean("Panel Loaded", isHatchPanelLoaded);
        Robot.driveTrain.drive();
    }
}
