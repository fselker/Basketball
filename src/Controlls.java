import lejos.nxt.UltrasonicSensor;


public class Controlls {
	Karte k;
	Pilot pilot;
	UltrasonicSensor us;
	int position[];
	
	public Controlls(Karte k, Pilot p, UltrasonicSensor us){
		this.k=k;
		this.pilot=p;
		this.us=us;
	}
}
