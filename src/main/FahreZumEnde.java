package main;
import lejos.nxt.LightSensor;
import lejos.nxt.UltrasonicSensor;
import regler.LinienFolger;
import regler.PRegler;


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
		
		while(ls.getLightValue()>(Controlls.schwarz+Controlls.weiss)/2){
			try{
				Thread.sleep(10);
			}catch(InterruptedException e){
				
			}
		}
		p.stop();
		p.travel(-10);
		p.arcForward(10);
		while(ls.getLightValue()>(Controlls.schwarz+Controlls.weiss)/2){
			try{
				Thread.sleep(10);
			}catch(InterruptedException e){
				
			}
		}
		p.stop();
//		Sound.beep();
		//jetzt der linie nach RECHTS folgen
	}
	
	public void fahreGenau(){
		LinienFolger lf = new LinienFolger(new PRegler());
		lf.run();
		//soll stoppen, wenn eine schwarze linie ueberfahren wird 
		//und sich dann richtig positionieren
	}
	
	public void run(){
		
	}

}
