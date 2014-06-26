package main;


import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class Pilot extends DifferentialPilot{
	double rotated=0;
	
	public Pilot(){
		super(57.0, 115, Motor.B, Motor.A);
	}
	public void travel(double dist){
		super.travel(dist*100/98.5);
		
	}
	public void rotate(double angle){
		rotated+=angle*356/360;
		super.rotate(angle*356/360);
	}
	
	public void setSpeed(double speed){
		setTravelSpeed(speed);
		setRotateSpeed(speed);
	}
	
	
}
