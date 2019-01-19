// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2515.ENIGMA.subsystems;


import org.usfirst.frc2515.ENIGMA.Robot;
import org.usfirst.frc2515.ENIGMA.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.*;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SPI.Port;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class sensors extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private DigitalInput rightLineSensor;
    private DigitalInput leftLineSensor;
    private DigitalInput digitalDistanceSensor;
    private AnalogInput analogDistanceSensor;
    private AHRS ahrs;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public Timer lineDetectionTimer;
    public boolean lineDetectionTimerStarted;
    public double lineDetectedStart = 0.0;
    public static double secondsBeforeAutoEngaged;
    public double delayAutoPilot;
    private String line;
    private Double turn;

    public sensors() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        rightLineSensor = new DigitalInput(0);
        addChild("rightLineSensor",rightLineSensor);
        
        
        leftLineSensor = new DigitalInput(1);
        addChild("leftLineSensor",leftLineSensor);
        
        
        digitalDistanceSensor = new DigitalInput(2);
        addChild("digitalDistanceSensor",digitalDistanceSensor);
        
        
        analogDistanceSensor = new AnalogInput(0);
        addChild("analogDistanceSensor",analogDistanceSensor);
        
        
        ahrs = new AHRS(Port.kMXP, (byte)50);
        addChild(ahrs);
        
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        lineDetectionTimer = new Timer();
        lineDetectionTimerStarted = false;
        secondsBeforeAutoEngaged = 0.3;
        delayAutoPilot = 0.0;
        turn = 0.0;
        line = "00";
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
        SmartDashboard.putNumber("Line Timer", lineDetectionTimer.get());
        SmartDashboard.putBoolean("Line Detected", isLineDetected());
        SmartDashboard.putBoolean("Digital Distance", digitalDistanceSensor.get());
        SmartDashboard.putString("Line Value", line);
        SmartDashboard.putNumber("Auto Turn", turn);
        SmartDashboard.putNumber("Distance (volts)", analogDistanceSensor.getVoltage());
        SmartDashboard.putNumber("Distance (inches)", getDistanceInInches());

        /* Display 6-axis Processed Angle Data */
        SmartDashboard.putBoolean("IMU_Connected", ahrs.isConnected());
        SmartDashboard.putBoolean("IMU_IsCalibrating", ahrs.isCalibrating());
        SmartDashboard.putNumber("IMU_Yaw", ahrs.getYaw());
        SmartDashboard.putNumber("IMU_Pitch", ahrs.getPitch());
        SmartDashboard.putNumber("IMU_Roll", ahrs.getRoll());
        /* Display tilt-corrected, Magnetometer-based heading (requires */
        /* magnetometer calibration to be useful) */
        SmartDashboard.putNumber("IMU_CompassHeading", ahrs.getCompassHeading());
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public boolean isLineDetected(){

        if (!Robot.autoPilotEnabled && leftLineSensor.get() || rightLineSensor.get()){

            if(!lineDetectionTimerStarted){

                lineDetectionTimer.start();

                lineDetectionTimerStarted = true;

            }

            if(lineDetectionTimer.get() >= secondsBeforeAutoEngaged){

                Robot.autoPilotEngaged = true;

            }

            return true;
        }

        Robot.autoPilotEngaged = false;
        lineDetectionTimer.stop();
        lineDetectionTimerStarted = false;
        lineDetectionTimer.reset();

        return false;
    }
    public double traceLine(){       
        line = Integer.toString(leftLineSensor.get() ? 1 : 0);
        line = line.concat(Integer.toString(rightLineSensor.get() ? 1: 0));
        SmartDashboard.putString("Line", line);
        switch(line){
            case "10":
                turn = 0.6;
                // robot needs slight right turn
                break;
            case "01":
                turn = -0.6;
                // robot needs slight left turn
                break;
            case "11":
                turn = 0.0;
                // robot is straight
                break;
            case "00":
                turn = 0.0;
                Robot.autoPilotEngaged = false;
                break;
            default:
                turn = 0.0;
                Robot.autoPilotEngaged = false;
                break;
        }
        return turn;
    }
    public boolean isWallDetected(){
        if(!digitalDistanceSensor.get()){
            return true;
        }
        return false;
    }

    public float getDistanceInInches() {
        float inches = (float) ((analogDistanceSensor.getVoltage() / .009766 )/2);
        return inches;
    }

}

