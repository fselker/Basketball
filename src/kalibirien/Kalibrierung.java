package kalibirien;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.SensorPortListener;
import main.Pilot;

public class Kalibrierung implements SensorPortListener {
	int schwarz, weiss, sollwert;
	LightSensor ls;

	public Kalibrierung() {
		ls = new LightSensor(SensorPort.S1);
		SensorPort.S1.addSensorPortListener(this);
		weiss = 0;
		schwarz = Integer.MAX_VALUE;
	}

	public static void main(String[] args) {
		Kalibrierung k = new Kalibrierung();
		Pilot p = new Pilot();
		p.setRotateSpeed(80);
		p.forceRotate(360);
		try {
			FileOutputStream fow = new FileOutputStream(new File("weiss"));
			FileOutputStream fos = new FileOutputStream(new File("schwarz"));

			fow.write(Integer.toString(k.weiss).getBytes());
			fos.write(Integer.toString(k.schwarz).getBytes());
			fos.close();
			fow.close();

			FileInputStream fiw = new FileInputStream(new File("weiss"));
			FileInputStream fis = new FileInputStream(new File("schwarz"));

			int weiss = 0, schwarz = 0;

			byte[] wei, sch;
			wei = new byte[fiw.available()];

			sch = new byte[fis.available()];
			fis.read(wei);
			fiw.read(sch);

			fis.close();
			fiw.close();
			weiss = Integer.parseInt(new String(wei));
			schwarz = Integer.parseInt(new String(sch));

			System.out.println(weiss);
			System.out.println(schwarz);
			Button.waitForAnyPress();
		} catch (IOException e) {

		}

	}

	@Override
	public void stateChanged(SensorPort aSource, int aOldValue, int aNewValue) {

		if (SensorPort.S1.equals(aSource)) {// Signal kommt vom Lichtsensor
			aNewValue = ls.getNormalizedLightValue();
			if (aNewValue < schwarz)
				schwarz = aNewValue;
			else if (aNewValue > weiss)
				weiss = aNewValue;
			sollwert = (schwarz + weiss) / 2;
		}
	}

}
