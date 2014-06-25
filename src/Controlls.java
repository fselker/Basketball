import lejos.nxt.LightSensor;
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
}
