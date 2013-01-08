/**
 *  have convex pareto front
 */
package emoAlgorithms;

/**
 * @author Shahab
 *	in zdt problems number of objectives are fix and is 2.
 */
public class ZDT1 extends ZDT {
	
	/**
	 * constructor for objects of this type
	 * @param m	number of variables
	 */
	public ZDT1(int m) {
		this.size_h = 500;		//this value is set in the main paper of NSGA2
		this.setNumObjectives(2);
		this.setNumVariables(m);
		this.mType = MOProblem.OPType.Min;
		// setting decision variable boundaries to [0,1];
		// but first creating the matrix
		decision_extremes = new double[getNumVariables()][];
		for (int i = 0;i < this.decision_extremes.length;i++) {
			decision_extremes[i] = new double[2];
			decision_extremes[i][0] = 0;
			decision_extremes[i][1] = 1;
		}		
		
		prepare();		//compute some known pareto optimal solutions spreaded in the front
		
	}

	/* (non-Javadoc)
	 * @see emoAlgorithms.ZDT#g(double[])
	 */
	@Override
	protected double g(double[] x) {
		double sum = 0;
		for (int i = 1;i < x.length;i++) {
			sum += x[i];
		}
		return (1 + 9*(sum/(x.length - 1)));
	}

	/* (non-Javadoc)
	 * @see emoAlgorithms.ZDT#f1(double[])
	 */
	@Override
	protected double f1(double[] x) {
		return x[0];
	}

	/* (non-Javadoc)
	 * @see emoAlgorithms.ZDT#h(double, double)
	 */
	@Override
	protected double h(double f1x, double gx) {
		if (gx == 0) System.out.println(gx);
		return (1 - Math.sqrt(f1x / gx));
	}

	/**
	 * this function will create a set of points(stored in matrix) of known pareto optimal solutions
	 */
	public void prepare() {
		// creating known optimal solutions set
		known_solutions = new double[size_h][];
		for (int i = 0;i < size_h;i++) {
			known_solutions[i] = new double[getNumVariables()];	//a vector for decision variables
			for (int j = 1;j < known_solutions[i].length;j++) {
				known_solutions[i][j] = 0;
			}
			known_solutions[i][0] = i / (double)size_h;
		}
		//changing the last solution so we will have the two extreme solutions in the set
		known_solutions[size_h-1][0] = 1;
		/**
		 * seems to be ok
		 */
	}
}
