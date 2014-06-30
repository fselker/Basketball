package main;

import java.util.ArrayList;

public class FahreZumKorb extends Thread {
	Controlls con;
	boolean run = true;
	static ArrayList<Integer> messung;

	FahreZumKorb(Controlls c) {
		con = c;
		// int pos[]=con.k.getHigh();

		// richtung herausfinden, in die der Korb liegt
		// fahre auf den Korb zu (bis auf 20cm)
		// korb genau suchen
	}

	public void fahr() {
		Controlls.pilot.rotated = 0;
		System.out.println("ziel :" + Controlls.k.getHigh()[0] + ":" + Controlls.k.getHigh()[1]);
		System.out.println("ich :" + SucheKorb.position[0] + ",:" + SucheKorb.position[1]);
		// Button.waitForAnyPress();
		// Wettkampf.display(Controlls.k.feld);
		// Button.waitForAnyPress();
		Controlls.pilot.setTravelSpeed(Controlls.travelSpeed*1.4);
		Controlls.pilot.rotate(Controlls.getRichtung(Controlls.k.getHigh()) / Math.PI * 180);
		System.out.println(Controlls.getDistance(Controlls.k.getHigh()));
		System.out.println("Winkel:" + Controlls.getRichtung(Controlls.k.getHigh()) / Math.PI * 180);
		if (Controlls.getDistance(Controlls.k.getHigh()) * 10 - 200 > 20)
			Controlls.pilot.travel(Controlls.getDistance(Controlls.k.getHigh()) * 10 - 200);
		Controlls.pilot.setTravelSpeed(Controlls.travelSpeed);
		// Button.waitForAnyPress();
	}

	/**
	 * geht davon aus, dass der Roboter nah am Korb ist dreht sich zum Korb und
	 * wirft dreht sich wieder in Ausgangsstellung
	 */
	public void fahreGenau() {

		messung = new ArrayList<Integer>();
		FahreZumKorb thread = new FahreZumKorb(con);
		Controlls.pilot.rotate(90);
		Controlls.pilot.setRotateSpeed(60);
		thread.start();
		Controlls.pilot.rotate(-180);
		thread.run = false;
		Controlls.pilot.setRotateSpeed(Controlls.rotateSpeed);
		System.out.println("warte auf thread");
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("hinter run");

		int dist = 100;
		int rot = 0;
		for (int i = 0; i < messung.size(); i++)
			if (messung.get(i) < dist) {
				rot = i;
				dist = messung.get(i);
			}
		System.out.println("alle werte ausgewertet");
		Controlls.pilot.rotate(180 - rot * 180 / (messung.size() - 1));
		System.out.println("fahr vorwaerts");
		Controlls.pilot.forward();
		System.out.println("fahrt");

		boolean rechts = false, links;
		while (!(links = Controlls.links.isPressed()) && !(rechts = Controlls.rechts.isPressed())) {
			if (Controlls.ls.getNormalizedLightValue() < (Controlls.schwarz + Controlls.weiss) / 2) {
				Controlls.pilot.travel(-300);
				Controlls.pilot.addrotate(-Controlls.pilot.rotated);
				return;
			}
		}
		System.out.println("fahr ende");

		Controlls.pilot.stop();
		if (links && !Controlls.rechts.isPressed())
			Controlls.pilot.rotate(15);
		if (!links && rechts)
			Controlls.pilot.rotate(-15);
		Controlls.wirf();
		Controlls.pilot.travel(-190);
		Controlls.pilot.rotate(-Controlls.pilot.rotated);

	}

	public void run() {
		while (run) {
			messung.add(Controlls.us.getDistance());
		}
	}

}
