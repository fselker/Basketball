import java.util.ArrayList;

import lejos.nxt.LightSensor;
import lejos.nxt.UltrasonicSensor;

public class SucheKorb extends Thread {

	public static boolean pos = false;
	public static int[] position = new int[2];
	UltrasonicSensor us;
	Pilot p;
	LightSensor ls;
	ArrayList<Integer> messung;
	boolean run = true;
	Karte k;

	public SucheKorb(Controlls c) {
		this.us = c.us;
		this.p = c.pilot;
		this.k = c.k;
		this.ls = c.ls;
	}

	public void findeKorb() {
		SucheKorb sk1 = new SucheKorb(new Controlls(k, p, us,ls));

		
		int vor=0;
		if(!pos)
			vor=-1;
		else
			vor=1;
		p.travel(500);
		if (!pos) {
			position[0] = 75;
			position[1] = 75;
		}else{
			position[0] = 125;
			position[1] = 125;	
		}
		p.rotate(-vor*90);
		sk1.messung = new ArrayList<Integer>();
		sk1.start();
		p.rotate(vor*180);
		sk1.run = false;
		p.rotate(-vor*90);
		try {
			sk1.join();
		} catch (InterruptedException e) {

		}

		if (k.getHigh()[0] == -1) {
		
			SucheKorb sk = new SucheKorb(new Controlls(k, p, us,ls));
			sk.run = true;
			p.travel(997);
			if (pos) {
				position[0] = 75;
				position[1] = 75;
			}else{
				position[0] = 125;
				position[1] = 125;	
			}
			p.rotate(vor*90);
			sk.messung = new ArrayList<Integer>();
			sk.start();
			p.rotate(vor*180);
			sk.run = false;
			try {
				sk.join();
			} catch (InterruptedException e) {

			}
			p.rotate(vor*90);
		}

	}

	public void run() {

//		while (run) {
//			messung.add(us.getDistance());
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//
//			}
//		}
		for(int i=0;i<60;i++)
			messung.add(255);
		messung.add(30);
		for(int i=0;i<60;i++)
			messung.add(255);
		k.eintragen(messung, position);

	}

}
