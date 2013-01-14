/**
 * 
 */
package emoAlgorithms;

/**  general EA
 * an abstract class representing general form of an
 * evolutionary algorithm
 * @author Shahab
 *
 */
public abstract class EvoAlgorithm {
	protected final double _defaultPm = 0.08;		//default value for mutation rate
	protected final double _defaultPc = 0.9;		//default value for crossover rate
	protected final int _defaultPopSize = 100;		//default value for size of population
	protected final int _defaultMaxGen = 250;		//default value for maximum number of generations
	protected final int _defailtMaxFcnEval = 25000;		//default value for maximum number of fitness evaluations
	
	public boolean mSilent;		// this will be used for controlling algorithms output

	
	public MOProblem mProblem;	// problem which algorithm tries to solve
	private double Pm;	// mutation rate
	private double Pc;  // crossover rate
	private int pop_size;	// size of population in each generation
	private int max_gen;	//maximum number of generations, used for stopping condition
	private int max_fit_eval;	//maximum number of fitness evaluations
	public int num_fit_eval;	//counting number of function evaluations.
	public int gen_count;	// number of generations passed till now
	private int etha_c;		//distribution index for sbx
	private int etha_m;		//distribution index for poly-muatation
	/**
	 * executes the algorithm, there is no return value since
	 * results are stored as objects states
	 */
	public abstract void run();

	/**
	 * @return the mutation rate
	 */
	public double getPm() {
		return Pm;
	}

	/**
	 * @param pm the pm to set
	 */
	public void setPm(double pm) {
		Pm = pm;
	}

	/**
	 * @return the pc
	 */
	public double getPc() {
		return Pc;
	}

	/**
	 * @param pc the pc to set
	 */
	public void setPc(double pc) {
		Pc = pc;
	}

	/**
	 * @return the pop_size
	 */
	public int getPop_size() {
		return pop_size;
	}

	/**
	 * @param pop_size the pop_size to set
	 */
	public void setPop_size(int pop_size) {
		this.pop_size = pop_size;
	}

	/**
	 * @return the max_gen
	 */
	public int getMaxGen() {
		return max_gen;
	}

	/**
	 * @param max_gen the max_gen to set
	 */
	public void setMaxGen(int max_gen) {
		this.max_gen = max_gen;
	}

	/**
	 * @return the max_fit_eval
	 */
	public int getMaxFitEval() {
		return max_fit_eval;
	}

	/**
	 * @param max_fit_eval the max_fit_eval to set
	 */
	public void setMaxFitEval(int max_fit_eval) {
		this.max_fit_eval = max_fit_eval;
	}

	/**
	 * @return the etha_c
	 */
	public int getEthaC() {
		return etha_c;
	}

	/**
	 * @param etha_c the etha_c to set
	 */
	public void setEthaC(int etha_c) {
		this.etha_c = etha_c;
	}

	/**
	 * @return the etha_m
	 */
	public int getEthaM() {
		return etha_m;
	}

	/**
	 * @param etha_m the etha_m to set
	 */
	public void setEthaM(int etha_m) {
		this.etha_m = etha_m;
	}
}
