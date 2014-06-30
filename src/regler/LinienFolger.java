package regler;

import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import main.Controlls;
import main.Pilot;

public class LinienFolger implements SensorPortListener {

	private int weiss, schwarz, sollwert;
	private LightSensor ls;
	private Pilot p;
	private PRegler regler;
	private double baseSpeed = Controlls.travelSpeed * 2, breakFactor = 1.5, kp = 3;
	ArrayList<Double> messungen;
	UltrasonicSensor us;

	public void run() {
		boolean found = false;
		int gut = 0;
		int aPower = 0, bPower = 0, temp;
		double x = 0, w = 0, y = 0;

		w = sollwert;

		regler.setSollWert(w);

		while (!Button.ESCAPE.isDown() && baseSpeed > 10) {

			x = ls.getLightValue();
			// messungen.add(x);
			y = regler.getValue(x);
			System.out.println("X: " + x + "Y: " + y);
			if (y > 15 && baseSpeed > 10 && found) {// dynamisch
//				Sound.beep();// ein beep entspricht einer gefundenen Ecke
				baseSpeed -= 10;
			}
			if (Math.abs(y) < 15) {
				gut++;
			}
			if (gut > 20)
				found = true;
			if (Math.abs(y) < 15 && baseSpeed < Controlls.travelSpeed * 2) {
				baseSpeed += 10;
			}

			aPower = ((int) ((baseSpeed - breakFactor * Math.abs(w - x) + y)));
			bPower = ((int) ((baseSpeed - breakFactor * Math.abs(w - x) - y)));

			if (!(Controlls.re == -1)) {
				temp = aPower;
				aPower = bPower;
				bPower = temp;
			}

			Motor.A.setSpeed(aPower);
			Motor.B.setSpeed(bPower);

			Motor.A.forward();
			Motor.B.forward();
			try {
				Thread.sleep(10);
			} catch (InterruptedException ex) {

			}
		}

	}

	@Override
	public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {

		if (SensorPort.S1.equals(aSource)) {// Signal kommt vom Lichtsensor
			aNewValue = ls.getLightValue();
			if (aNewValue < schwarz)
				schwarz = aNewValue;
			else if (aNewValue > weiss)
				weiss = aNewValue;
			sollwert = (schwarz + weiss) / 2;
			regler.setSollWert(sollwert);
		}
	}

	public LinienFolger(PRegler r) {
		ls = new LightSensor(SensorPort.S1);
		messungen = new ArrayList<Double>();
		weiss = 0;
		schwarz = Integer.MAX_VALUE;
		p = new Pilot();
		ls.setFloodlight(true);
		p.setRotateSpeed(Controlls.rotateSpeed);
		p.setTravelSpeed(Controlls.travelSpeed);
		us = new UltrasonicSensor(SensorPort.S2);
		SensorPort.S1.addSensorPortListener(this);
		SensorPort.S2.addSensorPortListener(this);
		regler = r;
		double par[] = { kp };
		r.setParameter(par);

	}

	public void setParameter(int pos, double value) {
		switch (pos) {
		case 0:
			kp = value;
			regler.k_p = kp;
			System.out.println("Kp gesetzt auf: " + value);
			break;
		case 1:
			baseSpeed = value;
			System.out.println("Speed gesetzt auf: " + value);
			break;
		case 2:
			breakFactor = value;
			System.out.println("BF gesetzt auf: " + value);
			break;
		default:
			System.out.println("Ungueltige Position empfangen: " + pos);
		}
	}
}
