package main;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;


public class Controlls {
	
	public static int rotateSpeed=80;
	public static int travelSpeed = 150;
	
	
	public static Karte k;
	public static Pilot pilot;
	public static UltrasonicSensor us;
	public static LightSensor ls;
	public static TouchSensor rechts, links;
	public static int weiss, schwarz, eigen, gegner, weiss2, schwarz2;
	public static int re=1;
	
	public Controlls(){
		k = new Karte();
		pilot = new Pilot();
		us = new UltrasonicSensor(SensorPort.S2);
		ls = new LightSensor(SensorPort.S1);
		rechts = new TouchSensor(SensorPort.S4);
		links= new TouchSensor(SensorPort.S3);
		
		pilot.setSpeed(travelSpeed);
		pilot.setRotateSpeed(rotateSpeed);
	}
	
	public static void wirf() {
		int angle = -150;
		Motor.C.setSpeed(600);
		Motor.C.rotate(angle);
		Motor.C.rotate(-angle);
		
	}
	
	public static double getRichtung(int pos[]){// ausgabe in PI
		double angle =0;
		int pos2[] = SucheKorb.position;
//		pos2[0]=0;
//		pos2[1]=0;
//		pos[0]=0;
//		pos[1]=1;
		LCD.clear();
//		System.out.println(pos2[0]+","+pos2[1]+",+"+pos[0]+",+"+pos[1]);
//		Button.waitForAnyPress();
		angle=Math.PI/2-Math.acos((pos2[0]-pos[0])/getDistance(pos));
//		angle=Math.PI/2-Math.acos(-1);//-0.7071067812);
		LCD.clear();
//		System.out.println("Wv: "+ angle/Math.PI*180);
		angle=((pos2[1]-pos[1]>0)?Math.PI-angle:angle);
//		System.out.println("Wv: "+ angle/Math.PI*180);
//		System.out.println(SucheKorb.pos);
		if(!SucheKorb.pos)
			angle+=Math.PI/4;
		else
			angle-=Math.PI/4*3;
		
//		System.out.println("PX: "+pos2[0]+ " PY: "+pos2[1]);
//		System.out.println("Ziel X: "+pos[0]+ "Ziel Y: "+pos[1]);
//		System.out.println("Winkel: "+angle/Math.PI*180);
//		Button.waitForAnyPress();
		
		return -angle;
	}
	public static double getDistance(int pos[]){
		int pos2[] = SucheKorb.position;
		
		
		
		return Math.sqrt((pos[0]-pos2[0])*(pos[0]-pos2[0]) + (pos[1]-pos2[1])*(pos[1]-pos2[1]));
	}
	
}
