import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;


public class Controlls {
	Karte k;
	Pilot pilot;
	UltrasonicSensor us;
	LightSensor ls;
	public Controlls(Karte k, Pilot p, UltrasonicSensor us, LightSensor ls){
		this.k=k;
		this.pilot=p;
		this.us=us;
		this.ls = ls;
	}
	
	public static void wirf() {
		int angle = 360;
		Motor.C.setSpeed(600);
		Motor.C.rotate(angle);
	}
	
}
