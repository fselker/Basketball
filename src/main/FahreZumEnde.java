package main;

import lejos.nxt.LightSensor;
import lejos.nxt.UltrasonicSensor;
import regler.LinienFolger;
import regler.PRegler;

public class FahreZumEnde extends Thread {
	Karte k;
	Pilot p;
	UltrasonicSensor us;
	LightSensor ls;
	LinienFolger lf;

	public FahreZumEnde(Controlls c) {
		k = c.k;
		p = c.pilot;
		us = c.us;
		ls = c.ls;
	}

	public void fahr() {
		lf = new LinienFolger(new PRegler());
		// wir sind 10 cm vom korb weg

		if (k.getHigh()[0] > k.getHigh()[1])
			Controlls.re = -1;
		// if(SucheKorb.pos)
		// Controlls.re*=-1;

		p.addrotate(Controlls.re * 40);
		p.runRotate();
		p.forward();
		// faehrt, bis die linie erreicht wird

		while (ls.getNormalizedLightValue() > (Controlls.schwarz + Controlls.weiss) / 2) {
			if (Controlls.rechts.isPressed() && Controlls.links.isPressed()) {


				p.travel(-20);
				p.rotate(-90);
				p.arc(300, 180);
				p.rotate(-90);
				p.forward();
			}
		}
		p.stop();

		p.travel(50);
		if (Controlls.re == 1)
			p.rotateRight();
		else
			p.rotateLeft();
		while (ls.getNormalizedLightValue() > (Controlls.schwarz + Controlls.weiss) / 2) {
			try {
				// System.out.println(ls.getLightValue());
				Thread.sleep(10);
			} catch (InterruptedException e) {

			}
		}
		p.stop();
		p.setSpeed(Controlls.travelSpeed);
		// p.travel(-10);
		// Sound.beep();
		// jetzt der linie nach RECHTS folgen
	}

	public void fahreGenau() {
		lf.run();
		p.rotate(-Controlls.re * 130);
		p.travel(340);

		// soll stoppen, wenn eine schwarze linie ueberfahren wird
		// und sich dann richtig positionieren
	}

	public void run() {

	}

}
