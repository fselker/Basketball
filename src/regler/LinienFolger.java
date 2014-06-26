package regler;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import lejos.nxt.UltrasonicSensor;
import main.Controlls;
import main.Pilot;

public class LinienFolger implements SensorPortListener {

	private int weiss, schwarz, sollwert;
	private LightSensor ls;
	double speed;
	private Pilot p;
	private PRegler regler;
	private double baseSpeed = 100, breakFactor = 1, kp = 10;
	public boolean run =true;
	public int threshold=30;

	public void run() {
		int aPower = 0, bPower = 0;
		double x = 0, w = 0, y = 0;
		p.rotate(360); // bestimmung der miniMotor.Al und Motor.AxiMotor.Al
						// werte
		w = sollwert;
		regler.setSollWert(w);
		while (run) {

			x = ls.getLightValue();

			y = regler.getValue(x);
			System.out.println("X: "+x+ " Y: "+y);
			if(Math.abs(x-y)>threshold){
				System.out.println("Ist hier die Ecke?");
				Button.waitForAnyPress();
				//hier wird sonst der Linienfolger beendet
			}

			aPower = ((int) (speed * (baseSpeed - breakFactor * Math.abs(w - x) + y)));
			bPower = ((int) (speed * (baseSpeed - breakFactor * Math.abs(w - x) - y)));
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

	public static void main(String args[]) {
		PRegler pr = new PRegler();
		LinienFolger lf = new LinienFolger(pr);
		lf.run();

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
		weiss = Controlls.weiss;
		schwarz = Controlls.schwarz;
		p = new Pilot();
		ls.setFloodlight(true);
		p.setRotateSpeed(80);
		p.setTravelSpeed(80);
		SensorPort.S1.addSensorPortListener(this);
		regler = r;
		double par[] = { kp };
		r.setParameter(par);

	}

}
