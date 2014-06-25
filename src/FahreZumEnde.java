import lejos.nxt.LightSensor;
import lejos.nxt.UltrasonicSensor;


public class FahreZumEnde extends Thread{
	Karte k;
	Pilot p;
	UltrasonicSensor us;
	LightSensor ls;
	public FahreZumEnde(Controlls c){
		k=c.k;
		p=c.pilot;
		us = c.us;
		ls = c.ls;
	}
	public void fahr(){
		p.rotate(45);
		p.forward();
		
	}
	
	public void run(){
		
	}

}
