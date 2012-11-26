/**
 * 
 */
package emoAlgorithms;


/** DTLZ1 Problem 
 * 
 * this problem is designed to introduce difficulty in converging to the Pareto front
 * 
 * @author Shahab
 * @version 2.0
 */
public class DTLZ1 extends MOProblem {
	
	protected int param_H; // this is the number of points used to evaluate the Pareto front
	protected int param_k;	// this is size of g_function's domain in the dtlz paper
	
	public DTLZ1(int argN, int argM) {
		this.setNumVariables(argN);
		this.setNumObjectives(argM);
		this.mType = MOProblem.OPType.Min;
		// setting variable bounds
		// creating a new matrix
		decision_extremes = new double[this.getNumVariables()][2];
		for (int i = 0;i < this.getNumVariables();i++) {
			decision_extremes[i][0] = 1;		//maximum value
			decision_extremes[i][1] = 0;		//minimum value
		}
		param_H = 500;  //this value is set in the original NSGA2 paper
		param_k = getNumVariables() - getNumObjectives() + 1;
		prepare();
	}
	
	/**
	 * creates a set of equally distributed points in the pareto front
	 * for evaluation of results
	 */
	protected void prepare() {
		
	}
	
	/**
	 * returns vector containing k last element of a point
	 * @param point  a point in the decision space
	 * @return k dimension vector
	 */
	protected double[] get_Xm(double point[]) {
		double ret[] = new double[param_k];
		for (int i = getNumObjectives() - 1;i < getNumVariables();i++){
			ret [i] = point[i];
		}
		return ret;
	}

	/**
	 * This is second version of DTLZ1 fitness function which is scalable
	 * @param point	a point in the decision space to be evaluated
	 * @return returns a point in the objective space
	 * @version 2.0
	 */
	@Override
	public double[] fitness(double[] point) {
		int m = getNumObjectives();
		double ret[] = new double[m];		//creating return vector
		double _g_Xm = 1 - g(get_Xm(point));	// last multiplying term in every element
		for (int i = 0;i < m - 1;i++) {
			ret[i] = 0.5;
			int j;
			for (j = 0;j < (m - 1 - i);j++) {
				ret[i] *= point[j];
			}
			if (j <( m - 1)) {
				ret[i] *= 1 - point[j];
			}
			ret[i] *= _g_Xm;
		}
		ret[m - 1] = 0.5 * (1 - point[0]) * _g_Xm;
		return ret;
	}
	
	/** f1
	 * this is the first objective function
	 *  
	 * @param x		input vector
	 * @return	a double value
	 */
	protected double f1(double[] x) {
		return x[0];
	}
	
	/** g
	 * this function is used to generate search space along the Pareto front
	 * @param x input vector(of variable size depending on input space dimension)
	 * @return  a double value
	 * @version 2.0
	 */
	private double g(double[] x) {
		double ret = x.length;
		
		for (double el : x){
			ret += Math.pow(el - 0.5,2.0) - Math.cos(20 * Math.PI * (el - 0.5));
		}
		ret *= 100;
		return ret;
		//return (1 +  9 * Utility.sum(x, 1) / (x.length - 1));
	}
	
	/** f2
	 * second objective
	 * @return a double value
	 */
	protected double f2(double[] x) {
		double g_ret = g(x);
		return g_ret * (1 - Math.sqrt(x[0] / g_ret));
	}
}
