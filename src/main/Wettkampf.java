package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.microedition.lcdui.Graphics;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Sound;

public class Wettkampf {

	Controlls c;

	public Wettkampf() {
		c = new Controlls();

	}

	public static void main(String[] args) {
		Wettkampf w = new Wettkampf();
		w.run();

	}

	public void run() {
		kalibrieren();

		System.out.println("Kalibriert");
		System.out.println("Zum Starten druecken");
		Button.waitForAnyPress();
		// LinienFolger lf = new LinienFolger(new PRegler());
		// c.pilot.rotate(360);
		// // c.pilot.rotate(180);
		// lf.run();
		// if(1==1)
		// return;
		// int run=0;

		SucheKorb sk = new SucheKorb(c);
		SucheKorb sk2 = new SucheKorb(c);
		FahreZumKorb fzk = new FahreZumKorb(c);
		FahreZumEnde fze = new FahreZumEnde(c);

		SucheKorb.position[0] = 30;
		SucheKorb.position[1] = 30;
		SucheKorb.pos = false;

		sk.findeKorb();
		Controlls.k.KarteBesetzen(SucheKorb.position[0], SucheKorb.position[1]);
		// display(Controlls.k.feld);
		// Button.waitForAnyPress();
		fzk.fahr();
		System.out.println("Ich bin in der Naehe des Korbs!");
		// Button.waitForAnyPress();
		fzk.fahreGenau();
		// System.out.println("Ich bin jetzt genau am Korb!");
		// Button.waitForAnyPress();
		fze.fahr();
		System.out.println("Ich bin jetzt an der Linie und links von mir ist das Ziel!");
		// Button.waitForAnyPress();
		fze.fahreGenau();
		System.out.println("Ich bin im Feld");
		Sound.beep();
		Button.waitForAnyPress();
		// stehen im endfeld richtig
		boolean find = false;
		while (true) {
			sk = new SucheKorb(c);
			fzk = new FahreZumKorb(c);
			fze = new FahreZumEnde(c);
//			if (!SucheKorb.pos) {
//				SucheKorb.position[0] = 160;
//				SucheKorb.position[1] = 140;
//			} else {
				SucheKorb.position[0] = 30;
				SucheKorb.position[1] = 30;
//			}
			// SucheKorb.pos = !SucheKorb.pos;

			Controlls.tausch();
			if (!find)
			{
				sk2.findeKorb();
				Controlls.erzeugeGesammt();
			}
			// LCD.clear();
			// display(c.k.feld);
			// Button.waitForAnyPress();

			// Controlls.pilot.travel(500);
			// SucheKorb.position[0]=30;
			// SucheKorb.position[1]=30;

			fzk.fahr();
			fzk.fahreGenau();
			fze.fahr();
			fze.fahreGenau();
			find = true;
			System.out.println("nachfuellen");
			Button.waitForAnyPress();
		}
		/*
		 * p.travel(50); p.rotate(180); SucheKorb.pos = true; sk.findeKorb();
		 * 
		 * display(k.feld); Button.waitForAnyPress(); double distance, angle;
		 * distance = FahreZumKorb.getDistance(k.getHigh()); angle =
		 * FahreZumKorb.getRichtung(k.getHigh());
		 * 
		 * int pos[] = k.getHigh(); int pos2[] = new int[2]; if(!SucheKorb.pos){
		 * pos2[0]=1; pos2[1]=1; }else{ pos2[0]=18; pos2[1]=18; }
		 * angle=-angle*180/Math.PI; distance*=100;
		 * 
		 * System.out.println("Position X: "+pos2[0]+ " Y: "+pos2[1]);
		 * System.out.println("High X: "+pos[0]+ " Y: "+pos[1]);
		 * Button.waitForAnyPress(); LCD.clear();
		 * System.out.println("Distance: "+distance);
		 * System.out.println("Winkel: "+angle); Button.waitForAnyPress();
		 * 
		 * 
		 * p.rotate(angle); p.travel(distance);
		 */

	}

	public void kalibrieren() {
		try {
			FileInputStream fiw = new FileInputStream(new File("weiss"));
			FileInputStream fis = new FileInputStream(new File("schwarz"));

			byte[] wei, sch;
			wei = new byte[fiw.available()];

			sch = new byte[fis.available()];
			fis.read(wei);
			fiw.read(sch);

			fis.close();
			fiw.close();
			Controlls.weiss = Integer.parseInt(new String(wei));
			Controlls.schwarz = Integer.parseInt(new String(sch));
		} catch (IOException e) {

		}

	}

	public static void display(int map[][]) {
		LCD.clear();
		Graphics g = new Graphics();
		for (int y = 0; y < 5; y++) {
			g.drawLine(y, y, map[0].length + 10, y);
			g.drawLine(y, y, y, map[0].length + 10);

			g.drawLine(map[0].length + y + 5, map[0].length + 10, y, map[0].length + 10 + y);

			g.drawLine(map[0].length + 10, map[0].length + y + 5, map[0].length + 10 + y, y);
		}

		for (int y = 0; y < map[0].length; y++)
			for (int x = 0; x < map.length; x++)
				if (map[x][y] > 0)
					g.drawLine(5 + x, 5 + y, 5 + x, 5 + y);
		LCD.refresh();
	}
}
