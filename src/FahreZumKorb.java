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
		System.out.println("ziel :"+con.k.getHigh()[0]+":"+con.k.getHigh()[1]);
		System.out.println("ich :"+SucheKorb.position[0]+",:"+SucheKorb.position[1]);
		Button.waitForAnyPress();
		con.pilot.rotate(this.getRichtung(con.k.getHigh())/Math.PI*180);
		System.out.println(this.getDistance(con.k.getHigh()));
		System.out.println("Winkel:"+this.getRichtung(con.k.getHigh())/Math.PI*180);
		con.pilot.travel(this.getDistance(con.k.getHigh())*10);
		Sound.beep();
		Button.waitForAnyPress();
	}
	
	public double getRichtung(int pos[]){// ausgabe in PI
		double angle =0;
		int pos2[] = SucheKorb.position;
		

		angle=Math.PI/2-Math.acos(Math.abs(pos2[0]-pos[0])/getDistance(pos));
		if(SucheKorb.pos)
			angle-=Math.PI/1*4;
		else
			angle+=Math.PI/3*4;
		return angle;
	}
	public double getDistance(int pos[]){
		int pos2[] = SucheKorb.position;
		
		
		
		return Math.sqrt((pos[0]-pos2[0])*(pos[0]-pos2[0]) + (pos[1]-pos2[1])*(pos[1]-pos2[1]));
	}
	


}
