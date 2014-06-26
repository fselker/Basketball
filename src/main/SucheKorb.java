package main;
import java.util.ArrayList;

import lejos.nxt.LightSensor;
import lejos.nxt.UltrasonicSensor;

public class SucheKorb extends Thread {

	public static boolean pos = false;
	public static int[] position = new int[2];
	
	ArrayList<Integer> messung;
	boolean run = true;
	Controlls c;
	public SucheKorb(Controlls c) {
		this.c=c;
	}

	public void findeKorb() {
		SucheKorb sk1 = new SucheKorb(c);

		
		int vor=0;
		if(!pos)
			vor=-1;
		else
			vor=1;
		c.pilot.travel(500);
		if (!pos) {
			position[0] = 75;
			position[1] = 75;
		}else{
			position[0] = 150;
			position[1] = 120;	
		}
		c.pilot.rotate(-vor*90);
		sk1.messung = new ArrayList<Integer>();
		sk1.start();
		c.pilot.rotate(vor*180);
		sk1.run = false;
		c.pilot.rotate(-vor*90);
		try {
			sk1.join();
		} catch (InterruptedException e) {

		}

		if (c.k.getHigh()[0] == 0) {
		
			SucheKorb sk = new SucheKorb(c);
			sk.run = true;
			c.pilot.travel(997);
			if (pos) {
				position[0] = 75;
				position[1] = 75;
			}else{
				position[0] = 150;
				position[1] = 120;	
			}
			c.pilot.rotate(vor*90);
			sk.messung = new ArrayList<Integer>();
			
			pos=!pos;
			sk.start();
			c.pilot.rotate(vor*180);
			sk.run = false;
			c.pilot.rotate(vor*90);
			try {
				sk.join();
			} catch (InterruptedException e) {

			}
			pos=!pos;

		}

	}

	public void run() {

		while (run) {
			messung.add(c.us.getDistance());
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {

			}
		}
//		messung.add(30);
//		for(int i=0;i<60;i++)
//			messung.add(255);
//
//		for(int i=0;i<30;i++)
//			messung.add(255);
//		messung.add(30);


		c.k.eintragen(messung, position);

	}

}
