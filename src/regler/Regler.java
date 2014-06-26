package regler;

public abstract class Regler {
	protected double sollwert;
	public abstract double getValue(double w);
	public void setSollWert(double sw){
		sollwert = sw;
	}
	public abstract void setParameter(double[] par);
}
