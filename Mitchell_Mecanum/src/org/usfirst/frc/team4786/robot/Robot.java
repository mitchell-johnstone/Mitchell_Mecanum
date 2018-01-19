/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4786.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	TalonSRX leftFront = new TalonSRX(13);
	TalonSRX leftBack = new TalonSRX(14);
	TalonSRX rightFront = new TalonSRX(15);
	TalonSRX rightBack = new TalonSRX(16);
	boolean front = true;
	//Joystick JoyVal = new Joystick(0);
	XboxController xbox = new XboxController(2);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// m_autoSelected = SmartDashboard.getString("Auto Selector",
		// 		kDefaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		double yVal = xbox.getY(Hand.kLeft);
		double xVal = xbox.getX(Hand.kLeft);
		double turnVal = xbox.getX(Hand.kRight);
		
		
		//gives the rotation value of each of the wheels
		// LFR = Left Front Right (wheel position on the robot)
		double LFR = yVal+xVal;
		double LBR = yVal-xVal;
		double RFR = yVal-xVal;
		double RBR = yVal+xVal;
				
				
		//switches front of robot
		if (front) {
			LBR = LBR*-1;
			LFR = LFR*-1;
		} else {
			RFR = RFR*-1;
			RBR = RBR*-1;
		}
		
		//switches value of front
		if (xbox.getYButtonPressed()) {
			front = !front;
		} else {
		}
		
		//speed control
				if (LFR < 0.5 && LFR > -.5) {
					LFR = LFR*0.0;
					RBR = RBR*0.0;
				} else {
					LFR = LFR*1.0;
					RBR = RBR*1.0;
				}
				
				if (LBR < 0.5 && LBR > -.5) {
					LBR = LBR*0.0;
					RFR = RFR*0.0;
				} else {
					LBR = LBR*1.0;
					RFR = RFR*1.0;
				}
		/*
		if (LFR >= .5 || RBR >= .5){
			LFR = (LFR-.5)*2;
		RBR = (RBR-.5)*2;
		} else {
		}
		
		if (LFR <= -.5 || RBR <= .5){
			LFR = (LFR+.5)*2;
		RBR = (RBR+.5)*2;
		} else { 
		}
		
		if (LBR >= .5 || RFR >= .5){
			LBR = (LBR-.5)*2;
		RFR = (RFR-.5)*2;
		} else {
		}
		
		if (LBR <= -.5 || RFR <= .5){
			LBR = (LBR+.5)*2;
		RFR = (RFR+.5)*2;
		} else { 
		}
		*/
		
		if (turnVal > .5) {
			LFR = LFR+25;
			LBR = LBR+25;
			RFR = RFR-25;
			RBR = RBR-25;
		} else if (turnVal < -.5) {
			RFR = RFR+25;
			RBR = RBR+25;
			LFR = LFR-25;
			LBR = LBR-25;
		} else {
		}
		
		leftFront.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, LFR);
		leftBack.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, LBR);
		rightFront.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, RFR);
		rightBack.set(com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput, RBR);
		

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
