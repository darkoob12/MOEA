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
	protected final double _defaultPm = 0.05;		//default value for mutation rate
	protected final double _defaultPc = 0.88;		//default value for crossover rate
	protected final int _defaultPopSize = 100;		//default value for size of population
	protected final int _defaultMaxGen = 500;		//default value for maximum number of generations
	
	public MOProblem mProblem;	// problem which algorithm tries to solve
	private double Pm;	// mutation rate
	private double Pc;  // crossover rate
	private int pop_size;	// size of population in each generation
	private int max_gen;	//maximum number of generations, used for stopping condition
	public int gen_count;	// number of generations passed till now
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
}
