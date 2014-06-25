import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Sound;


public class FahreZumKorb {
	Controlls con;

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
		
		angle=Math.acos((pos2[0]-pos[0])/getDistance(pos));
		LCD.clear();
		System.out.println("Wv: "+ angle/Math.PI*180);
		angle*=((pos2[0]-pos[0]<0)?1:-1);
		if(!SucheKorb.pos)
			angle-=Math.PI/4;
		else
			angle-=Math.PI/4*3;
		
		System.out.println("PX: "+pos2[0]+ " PY: "+pos2[1]);
		System.out.println("Ziel X: "+pos[0]+ "Ziel Y: "+pos[1]);
		System.out.println("Winkel: "+angle/Math.PI*180);
		Button.waitForAnyPress();
		
		return angle;
	}
	public static double getDistance(int pos[]){
		int pos2[] = SucheKorb.position;
		
		
		
		return Math.sqrt((pos[0]-pos2[0])*(pos[0]-pos2[0]) + (pos[1]-pos2[1])*(pos[1]-pos2[1]));
	}
	/**
	 * geht davon aus, dass der roboter nah am Korb ist
	 * dreht sich zum Korb und wirft
	 * dreht sich wieder in ausgangsstellung
	 */
	public void fahreGenau(){
		//suche den Korb per entfernungssensor und wirf den Ball rein
		
		
		con.pilot.rotate(-con.pilot.rotated);
		
	}

}
