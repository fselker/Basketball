import lejos.nxt.Button;
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
		System.out.println("ziel :"+con.k.getHigh()[0]+":"+con.k.getHigh()[1]);
		System.out.println("ich :"+SucheKorb.position[0]+",:"+SucheKorb.position[1]);
		Button.waitForAnyPress();
		con.pilot.rotate(getRichtung(con.k.getHigh())/Math.PI*180);
		System.out.println(getDistance(con.k.getHigh()));
		System.out.println("Winkel:"+getRichtung(con.k.getHigh())/Math.PI*180);
		con.pilot.travel(getDistance(con.k.getHigh())*10);
		Sound.beep();
		Button.waitForAnyPress();
	}
	
	public static double getRichtung(int pos[]){// ausgabe in PI
		double angle =0;
		int pos2[] = SucheKorb.position;
		

		angle=Math.PI/2-Math.acos(Math.abs(pos2[0]-pos[0])/getDistance(pos));
		if(SucheKorb.pos)
			angle-=Math.PI/1*4;
		else
			angle+=Math.PI/3*4;
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
