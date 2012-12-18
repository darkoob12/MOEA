/**
 * this problems are have two objectives and are generated quiet similar
 */
package emoAlgorithms;

/**
 * @author Shahab
 *
 */
public abstract class ZDT extends MOProblem {

	int size_h;
	protected double known_solutions[][];	// this is H set in the Paper		 
	/* (non-Javadoc)
	 * @see emoAlgorithms.MOProblem#fitness(double[])
	 */
	@Override
	public double[] fitness(double[] point) {
		double ret[] =  new double[2];
		ret[0] = f1(point);
		double g_x = g(point);
		ret[1] = g_x * h(ret[0], g_x);
		return ret;
	}

	protected abstract double g(double x[]);
	protected abstract double f1(double x[]); 
	protected abstract double h(double f1x, double gx);
}
