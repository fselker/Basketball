package main;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;

public class Controlls {

	private static final int fieldsize = 20;

	public static int rotateSpeed = 100;
	public static int travelSpeed = 190;

	public static Karte k;
	public static Karte kersatz;
	public static Pilot pilot;
	public static UltrasonicSensor us;
	public static LightSensor ls;
	public static TouchSensor rechts, links;
	public static int weiss, schwarz, eigen, gegner, weiss2, schwarz2;
	public static int re = 1;
	private static Karte endfeld1;
	private static Karte endfeld2;
	private static boolean pos = true;

	public static void tausch() {

		Karte ker = k;
		k = kersatz;
		kersatz = ker;
		pos = !pos;
	}

	public Controlls() {
		k = new Karte();
		endfeld1 = new Karte();
		endfeld2 = new Karte();
		kersatz = new Karte();
		pilot = new Pilot();
		us = new UltrasonicSensor(SensorPort.S2);
		ls = new LightSensor(SensorPort.S1);
		rechts = new TouchSensor(SensorPort.S4);
		links = new TouchSensor(SensorPort.S3);

		pilot.setSpeed(travelSpeed);
		pilot.setRotateSpeed(rotateSpeed);
	}

	public static void wirf() {
		int angle = -150;
		Motor.C.setSpeed(600);
		Motor.C.rotate(angle);
		Motor.C.rotate(-angle);

	}

	public static double getRichtung(int pos[]) {// ausgabe in PI
		double angle = 0;
		int pos2[] = SucheKorb.position;
		// pos2[0]=0;
		// pos2[1]=0;
		// pos[0]=0;
		// pos[1]=1;
		LCD.clear();
		// System.out.println(pos2[0]+","+pos2[1]+",+"+pos[0]+",+"+pos[1]);
		// Button.waitForAnyPress();
		angle = Math.PI / 2 - Math.acos((pos2[0] - pos[0]) / getDistance(pos));
		// angle=Math.PI/2-Math.acos(-1);//-0.7071067812);
		LCD.clear();
		// System.out.println("Wv: "+ angle/Math.PI*180);
		angle = ((pos2[1] - pos[1] > 0) ? Math.PI - angle : angle);
		// System.out.println("Wv: "+ angle/Math.PI*180);
		// System.out.println(SucheKorb.pos);
		if (!SucheKorb.pos)
			angle += Math.PI / 4;
		else
			angle -= Math.PI / 4 * 3;

		// System.out.println("PX: "+pos2[0]+ " PY: "+pos2[1]);
		// System.out.println("Ziel X: "+pos[0]+ "Ziel Y: "+pos[1]);
		// System.out.println("Winkel: "+angle/Math.PI*180);
		// Button.waitForAnyPress();

		return -angle;
	}

	public static double getDistance(int pos[]) {
		int pos2[] = SucheKorb.position;

		return Math.sqrt((pos[0] - pos2[0]) * (pos[0] - pos2[0]) + (pos[1] - pos2[1]) * (pos[1] - pos2[1]));
	}

	public static void erzeugeGesammt() {
		for (int i = 0; i < fieldsize; i++)
			for (int j = 0; j < fieldsize; j++) {
				if(k.feld[i][j]>=0)
				endfeld2.feld[fieldsize-1 - i][fieldsize-1 - j] = endfeld1.feld[i][j] = k.feld[i][j] + kersatz.feld[fieldsize-1 - i][fieldsize-1 - j];
				else
					endfeld2.feld[fieldsize-1 - i][fieldsize-1 - j] = endfeld1.feld[i][j]=-200;
			
			}
		k = endfeld2;
		kersatz = endfeld1;
		pos = true;
	}

	public static Karte getEndfeld() {
		if (pos)
			return endfeld1;
		else
			return endfeld2;
	}
}
