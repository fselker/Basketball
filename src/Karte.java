import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Karte {
	int feld[][] = new int[20][20];
	int radius = 250;

	public Karte() {
		int reduce = Integer.MAX_VALUE;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				feld[i][j] = -reduce;
				feld[i][feld.length - 1 - j] = -reduce;
				feld[feld.length - 1 - i][j] = -reduce;
				feld[feld.length - 1 - i][feld.length - 1 - j] = -reduce;
			}
		}
	}

	public void eintragen(ArrayList<Integer> arl, boolean pos) {
		double gpm = Math.PI / 2 / arl.size();
		double x = 0;
		double y = 0;
		int px, py;
		for (int i = 0; i < arl.size(); i++) {
			{// if (arl.get(i) < radius) {
				// System.out.println("drin");
				// Button.waitForAnyPress();
				x = (arl.get(i) * Math.cos(gpm * i));
				y = Math.sqrt(arl.get(i) * arl.get(i) - x * x);
				px = (int) ((x + 5) / 10);
				py = (int) ((y + 5) / 10);
//				System.out.println("Distance:" + arl.get(i));
//				System.out.println("Angle:" + gpm * i / Math.PI * 180);
//				System.out.println("X: " + px + ", Y: " + py);
//				Button.waitForAnyPress();
//				LCD.clear();
				try {
					if (pos)
						KarteBesetzen(18 - px, 18 - py);
					else
						KarteBesetzen(px + 1, py + 1);
				} catch (Exception e) {
//					System.out.println("error:" + px + "," + py);
//					Button.waitForAnyPress();
				}
				// System.out.println("raus");
				// Button.waitForAnyPress();
			}
		}

	}
	
	public void eintragen(ArrayList<Integer> arl, int pos[]) {
		double gpm = Math.PI / arl.size();
		double x = 0;
		double y = 0;
		int px, py;
		for (int i = 0; i < arl.size(); i++) {
			 if (arl.get(i) < radius) {
				 System.out.println(arl.get(i));
				 Button.waitForAnyPress();
				 
				 double win=0;
				 if(SucheKorb.pos)
					 win=Math.PI*1.0/4;
				 else
					 win=-Math.PI*3.0/4;
				 
					x = (arl.get(i) * Math.cos(gpm * i-win));
					y = (arl.get(i) * Math.sin(gpm * i+win));
				
				
				
				
					px = (int) ((x + 5+SucheKorb.position[0]) / 10)-1;
					py = (int) ((y + 5+SucheKorb.position[1]) / 10)-1;
//				System.out.println("Distance:" + arl.get(i));
//				System.out.println("Angle:" + gpm * i / Math.PI * 180);
				System.out.println("X: " + px + ", Y: " + py);
				Button.waitForAnyPress();
				 Button.waitForAnyPress();
				LCD.clear();
				try {
					
					KarteBesetzen(px, py);
					
				} catch (Exception e) {
					System.out.println("error:" + px + "," + py);
					Button.waitForAnyPress();
				}
				// System.out.println("raus");
				// Button.waitForAnyPress();
			}
		}

	}

	private void KarteBesetzen(int px, int py) {
		if (px > feld.length || px < 0 || py > feld[0].length || py < 0)
			return;
		feld[px][py] += 5;
		if (px + 1 < feld.length) {
			if (py < feld[0].length)
				feld[px + 1][py + 1] += 4;

			feld[px + 1][py] += 4;
			if (py - 1 >= 0)
				feld[px + 1][py - 1] += 4;
		}
		if (py < feld[0].length)
			feld[px][py + 1] += 4;
		if (py - 1 >= 0)
			feld[px][py - 1] += 4;
		if (px - 1 >= 0) {
			if (py < feld[0].length)
				feld[px - 1][py + 1] += 4;
			feld[px - 1][py] += 4;
			if (py - 1 >= 0)
				feld[px - 1][py - 1] += 4;
		}
	}

	public int[] getHigh() {
		int ret[] = new int[2];
		int x = -1, y = -1;
		int high = 1;
		for (int i = 1; i < 19; i++)
			for (int j = 1; j < 19; j++)
				if (feld[i][j] > high) {
					x = i;
					y = j;
					high = feld[i][j];
				}

		ret[0] = x*10;
		ret[1] = y*10;
		return ret;
	}
}
