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
	
	public int size_h;
	public double known_solutions[][];	// this is H set in the Paper  -- is populated in sub-classes		 
	protected double known_sol_fitness[][];	// fitness of the H known solutions
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
	
	/**
	 * This is the metric that measures convergence of algorithm to the pareto front
	 */
	public double compute_gamma(Population f_pop) {
		comp_known_fit();		//we must use fitness vectors
		double ret = 0;
		for (Chromosome ch : f_pop) {
			double foo = min_distance(ch.fitness_vector);
			if (Double.isNaN(foo)) {
				System.out.println("Not A Number!!!!!");
				System.out.println(ch.toString());
			} else {
				ret += foo;
			}
		}		
		return (ret/f_pop.mMembers.size());
	}
	
	
	
	/**
	 * find minimum distance of the given vector to set of known solutions
	 * @return
	 */
	protected double min_distance(double fit_vect[]) {
		double min_d = Utility.euclid_distance(known_sol_fitness[0], fit_vect);
		for (int i = 1;i < known_solutions.length;i++) {
			double temp = Utility.euclid_distance(known_sol_fitness[i], fit_vect);
			if (temp < min_d) {
				min_d = temp;
			}
		}
		return min_d;
	}
	
	/**
	 * computing fitness vectors for each of the H known solutions
	 */
	public void comp_known_fit() {
		known_sol_fitness = new double[size_h][];		//size_h is number of known solutions
		for (int i = 0;i < this.size_h;i++) {
			known_sol_fitness[i] = new double[getNumObjectives()];
			known_sol_fitness[i] = fitness(known_solutions[i]);
		}
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
