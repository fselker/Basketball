package main;
import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.Sound;


public class FahreZumKorb extends Thread{
	Controlls con;
	boolean run=true;
	static ArrayList<Integer> messung;

	FahreZumKorb(Controlls c) {
		con = c;
		//int pos[]=con.k.getHigh();
		
		
		//richtung herausfinden, in die der Korb liegt
		//fahre auf den Korb zu (bis auf 20cm)
		//korb genau suchen
	}
	
	public void fahr(){
		con.pilot.rotated=0;
		System.out.println("ziel :"+con.k.getHigh()[0]+":"+con.k.getHigh()[1]);
		System.out.println("ich :"+SucheKorb.position[0]+",:"+SucheKorb.position[1]);
//		Button.waitForAnyPress();
		con.pilot.rotate(Controlls.getRichtung(con.k.getHigh())/Math.PI*180);
		System.out.println(Controlls.getDistance(con.k.getHigh()));
		System.out.println("Winkel:"+Controlls.getRichtung(con.k.getHigh())/Math.PI*180);
		con.pilot.travel(Controlls.getDistance(con.k.getHigh())*10-100);
		Sound.beep();
//		Button.waitForAnyPress();
	}
	
	
	/**
	 * geht davon aus, dass der Roboter nah am Korb ist
	 * dreht sich zum Korb und wirft
	 * dreht sich wieder in Ausgangsstellung
	 */
	public void fahreGenau(){
		
		messung=new ArrayList<Integer>();
		FahreZumKorb thread= new FahreZumKorb(con);
		con.pilot.rotate(90);
		thread.start();
		con.pilot.rotate(-180);
		thread.run=false;
		System.out.println("warte auf thread");
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("hinter run");
		
		int dist=100;
		int rot=0;
		for(int i=0;i<messung.size();i++)
			if(messung.get(i)<dist){
				rot=i;
				dist=messung.get(i);
			}
		System.out.println("alle werte ausgewertet");
		con.pilot.rotate(180-rot*180/(messung.size()-1));
		System.out.println("fahr vorwaerts");
		con.pilot.forward();
		System.out.println("fahrt");
		boolean rechts=false,links;
		while(!(links=con.links.isPressed()) && !(rechts=con.rechts.isPressed()));
		System.out.println("fahr ende");
		
		con.pilot.stop();
		if(links&&!con.rechts.isPressed())
			con.pilot.rotate(15);
		if(!links&&rechts)
			con.pilot.rotate(-15);
		Controlls.wirf();
		con.pilot.travel(-100);
		con.pilot.rotate(-con.pilot.rotated);
		
	}
	
	public void run(){
		while(run){
			messung.add(con.us.getDistance());				
		}
	}
	
	public void getPosition(){
		int min=Integer.MAX_VALUE;
		int pos=-1;
//		for()
		
		
		
		
	}

}
