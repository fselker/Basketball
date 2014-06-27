package main;


import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class Pilot extends DifferentialPilot{
	double rotated=0;
	double angle=0;
	
	public Pilot(){
		super(57.0, 115, Motor.B, Motor.A);
	}
	public void travel(double dist){
		super.travel(dist*100/98.5);
		
	}
	public void rotate(double angle){
		angle%=360;
		if(angle>190)
			angle-=360;
		else if(angle<-190)
			angle+=360;
		rotated+=angle*356/360;
		super.rotate(angle*356/360);
	}
	
	public void addrotate(double angle){
		this.angle+=angle;
	}
	public void runRotate(){
		rotate(angle);
		angle=0;
	}
	public void forceRotate(double angle){
		rotated+=angle*356/360;
		super.rotate(angle*356/360);
	}
	
	
	
	public void setSpeed(double speed){
		setTravelSpeed(speed);
		setRotateSpeed(speed);
	}
	
	
}
