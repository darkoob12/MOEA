package emoAlgorithms;



/** abstract class representing a general (real) MOP
 * 
 * @author Shahab
 *
 */
public abstract class MOProblem {
	private int mNumVariables;
	private int mNumObjectives;
	
	/**
	 * minimum and maximum values for each decision variable , N*2
	 * for computing this we need a prior knowledge or we need to do some pre-processing
	 */
	public double decision_extremes[][];	
	
	public OPType mType;	//minimization should be the default problem type.
	
	
	public abstract double[] fitness(double[] point);

	
	/** returns number of decision variables
	 * @return the mNumVariables
	 */
	public int getNumVariables() {
		return mNumVariables;
	}

	/** sets number of decision variables
	 * @param mNumVariables the mNumVariables to set
	 */
	public void setNumVariables(int mNumVariables) {
		this.mNumVariables = mNumVariables;
	}

	/** returns number of objectives (dimension of objective space)
	 * @return the mNumObjectives
	 */
	public int getNumObjectives() {
		return mNumObjectives;
	}

	/** sets new value for number of objectives
	 * @param mNumObjectives the mNumObjectives to set
	 */
	public void setNumObjectives(int mNumObjectives) {
		this.mNumObjectives = mNumObjectives;
	}
	

	/** Domination relation
	 * 
	 * checks whether fitnees1 dominates fitness2
	 * 
	 * i have tested this function by manual data
	 * 
	 * @param fit1	a fitness vector
	 * @param fit2	a fitness vector
	 * @return	boolean
	 * @throws Exception may throw an exception for wrong optimization type
	 */
	public boolean dominates(double[] fit1, double[] fit2) {
		boolean ret = false;
		boolean flag;
		switch (this.mType) {
		case Min:
			flag = false;
			for (int i = 0;i < fit1.length;i++) {
				if (fit1[i] > fit2[i]) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				for (int i = 0;i < fit1.length;i++) {
					if (fit1[i] < fit2[i]) {
						ret = true;
						break;
					}
				}
			}
			break;
		case Max:
			flag = false;
			for (int i = 0;i < fit1.length;i++) {
				if (fit1[i] < fit2[i]) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				for (int i = 0;i < fit1.length;i++) {
					if (fit1[i] > fit2[i]) {
						ret = true;
						break;
					}
				}
			}			
			break;
		default:
			System.out.println("Fata Error: not a standard optimization problem");
			System.exit(1);
		}
		
		return ret;
	}

	public enum OPType { Min, Max};
}
