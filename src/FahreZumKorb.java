import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.LCD;
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
//		System.out.println("ziel :"+con.k.getHigh()[0]+":"+con.k.getHigh()[1]);
//		System.out.println("ich :"+SucheKorb.position[0]+",:"+SucheKorb.position[1]);
//		Button.waitForAnyPress();
		con.pilot.rotate(getRichtung(con.k.getHigh())/Math.PI*180);
//		System.out.println(getDistance(con.k.getHigh()));
//		System.out.println("Winkel:"+getRichtung(con.k.getHigh())/Math.PI*180);
		con.pilot.travel(getDistance(con.k.getHigh())*10);
		Sound.beep();
//		Button.waitForAnyPress();
	}
	
	public static double getRichtung(int pos[]){// ausgabe in PI
		double angle =0;
		int pos2[] = SucheKorb.position;
//		pos2[0]=0;
//		pos2[1]=0;
//		pos[0]=0;
//		pos[1]=1;
		LCD.clear();
		System.out.println(pos2[0]+","+pos2[1]+",+"+pos[0]+",+"+pos[1]);
		Button.waitForAnyPress();
		angle=Math.PI/2-Math.acos((pos2[0]-pos[0])/getDistance(pos));
//		angle=Math.PI/2-Math.acos(-1);//-0.7071067812);
		LCD.clear();
		System.out.println("Wv: "+ angle/Math.PI*180);
		angle=((pos2[1]-pos[1]>0)?Math.PI-angle:angle);
		System.out.println("Wv: "+ angle/Math.PI*180);
		System.out.println(SucheKorb.pos);
		if(!SucheKorb.pos)
			angle+=Math.PI/4;
		else
			angle-=Math.PI/4*3;
		
//		System.out.println("PX: "+pos2[0]+ " PY: "+pos2[1]);
//		System.out.println("Ziel X: "+pos[0]+ "Ziel Y: "+pos[1]);
		System.out.println("Winkel: "+angle/Math.PI*180);
		Button.waitForAnyPress();
		
		return -angle;
	}
	public static double getDistance(int pos[]){
		int pos2[] = SucheKorb.position;
		
		
		
		return Math.sqrt((pos[0]-pos2[0])*(pos[0]-pos2[0]) + (pos[1]-pos2[1])*(pos[1]-pos2[1]));
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
		run=false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int dist=40;
		int rot=0;
		for(int i=0;i<messung.size();i++)
			if(messung.get(i)<dist){
				rot=i;
				dist=messung.get(i);
			}
		con.pilot.rotate(-rot*180/(messung.size()-1));	
		con.pilot.forward();
		while(con.us.getDistance()>5);
		con.pilot.stop();
		con.wirf();
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
