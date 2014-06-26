package regler;

import java.io.DataInputStream;

import lejos.nxt.comm.NXTConnection;

public class ConnectionListener implements Runnable{

	NXTConnection nxt;
	LinienFolger lf;
	public ConnectionListener(NXTConnection nxt, LinienFolger lf){
		this.nxt=nxt;
		this.lf=lf;
	}
	@Override
	public void run() {
		DataInputStream dis = nxt.openDataInputStream();
		int pos;
		double value;
		while(true){
			try{
			pos=dis.readInt();
			value=dis.readDouble();
//			lf.setParameter(pos, value);
			
			
			
			
				Thread.sleep(100);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		
	}

}
