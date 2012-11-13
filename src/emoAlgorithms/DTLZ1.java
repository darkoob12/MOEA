/**
 * 
 */
package emoAlgorithms;


/** DTLZ1 Problem 
 * 
 * this problem have a convex Pareto front
 * 
 * @author Shahab
 *
 */
public class DTLZ1 extends MOProblem {
	
	public DTLZ1(int argN) {
		this.setNumVariables(argN);
		this.setNumObjectives(2);
		this.mType = MOProblem.OPType.Min;
		// setting variable bounds
		// creating a new matrix
		decision_extremes = new double[this.getNumVariables()][2];
		for (int i = 0;i < this.getNumVariables();i++) {
			decision_extremes[i][0] = 1;		//maximum value
			decision_extremes[i][1] = 0;		//minimum value
		}
	}

	/* (non-Javadoc)
	 * @see emoAlgorithms.MOProblem#fitness(double[])
	 */
	@Override
	public double[] fitness(double[] point) {
		double ret[] = new double[2];
		ret[0] = f1(point);
		ret[1] = f2(point);
		return ret;
	}
	
	/** f1
	 * 
	 * this is the first objective function 
	 * 
	 * @param x		input vector
	 * @return	a double value
	 */
	private double f1(double[] x) {
		return x[0];
	}
	
	/** g
	 * 
	 * this is used to generate the last objective using the previous ones
	 * 
	 * @param x input vector
	 * @return  a double value
	 */
	private double g(double[] x) {
		return (1 +  9 * Utility.sum(x, 1) / (x.length - 1));
	}
	
	/** f2
	 * second objective
	 * @return a double value
	 */
	private double f2(double[] x) {
		double g_ret = g(x);
		return g_ret * (1 - Math.sqrt(x[0] / g_ret));
	}
}
