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
		//faehrt, bis die linie erreicht wird
		
		while(ls.getLightValue()>(Wettkampf.schwarz+Wettkampf.weiss)/2){
			try{
				Thread.sleep(10);
			}catch(InterruptedException e){
				
			}
		}
		p.stop();
//		Sound.beep();
		//jetzt der linie nach LINKS folgen
	}
	
	public void run(){
		
	}

}
