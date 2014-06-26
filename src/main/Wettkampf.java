package main;
import javax.microedition.lcdui.Graphics;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class Wettkampf {

	
	Controlls c;

	public Wettkampf() {
		c = new Controlls(new Karte(), new Pilot(), new UltrasonicSensor(SensorPort.S2), new LightSensor(SensorPort.S1));
		c.pilot = new Pilot();
		c.pilot.setSpeed(120);
		c.pilot.setRotateSpeed(80);
	}

	public static void main(String[] args) {
		Wettkampf w = new Wettkampf();
		w.run();

	}

	public void run() {
//		kalibrieren();
		int run=0;

		SucheKorb sk = new SucheKorb(c);
		FahreZumKorb fzk = new FahreZumKorb(c);
		FahreZumEnde fze = new FahreZumEnde(c);
		SucheKorb.position[0]=30;
		SucheKorb.position[1]=30;
		SucheKorb.pos=false;
		
		sk.findeKorb();
		display(c.k.feld);
		Button.waitForAnyPress();
		
		fzk.fahr();
		System.out.println("Ich bin in der N�he des Korb!");
		Button.waitForAnyPress();
		fzk.fahreGenau();
		System.out.println("Ich bin jetzt genau am Korb!");
		Button.waitForAnyPress();
		fze.fahr();
		System.out.println("Ich bin jetzt an der Linie und links von mir ist das Ziel!");
		Button.waitForAnyPress();
		
		/*
		p.travel(50);
		p.rotate(180);
		SucheKorb.pos = true;
		sk.findeKorb();

		display(k.feld);
		Button.waitForAnyPress();
		double distance, angle;
		distance = FahreZumKorb.getDistance(k.getHigh());
		angle = FahreZumKorb.getRichtung(k.getHigh());
		
		int pos[] = k.getHigh();
		int pos2[] = new int[2];
		if(!SucheKorb.pos){
			pos2[0]=1;
			pos2[1]=1;
		}else{
			pos2[0]=18;
			pos2[1]=18;
		}
		angle=-angle*180/Math.PI;
		distance*=100;
		
		System.out.println("Position X: "+pos2[0]+ " Y: "+pos2[1]);
		System.out.println("High X: "+pos[0]+ " Y: "+pos[1]);
		Button.waitForAnyPress();
		LCD.clear();
		System.out.println("Distance: "+distance);
		System.out.println("Winkel: "+angle);
		Button.waitForAnyPress();
		
		
		p.rotate(angle);
		p.travel(distance);
		*/
		
		
		
		
	}
	



	public void kalibrieren() {
		System.out.println("weiss");
		Button.waitForAnyPress();
		c.weiss = c.ls.getNormalizedLightValue();

		System.out.println("schwarz");
		Button.waitForAnyPress();
		c.schwarz = c.ls.getNormalizedLightValue();

		System.out.println("gegner");
		Button.waitForAnyPress();
		c.gegner = c.ls.getNormalizedLightValue();

		System.out.println("eigen");
		Button.waitForAnyPress();
		c.eigen = c.ls.getNormalizedLightValue();
		LCD.clear();
		System.out.println("Eigen: " + c.eigen + " Gegner: " + c.gegner + " Weiss: " + c.weiss + " schwarz: " + c.schwarz);

	}

	public static void display(int map[][]){
		LCD.clear();
		Graphics g= new Graphics();
		for(int y=0;y<5;y++){
				g.drawLine(y, y, map[0].length+10, y);
				g.drawLine(y, y, y, map[0].length+10);
				
				g.drawLine(map[0].length+y+5, map[0].length+10, y, map[0].length+10+y);
				
				g.drawLine(map[0].length+10, map[0].length+y+5, map[0].length+10+y, y);
		}
				
			
		
		for(int y=0;y<map[0].length;y++)
			for(int x=0;x<map.length;x++)
				if(map[x][y]>0)
				g.drawLine(5+x, 5+y, 5+x, 5+y);
		LCD.refresh();
	}
}