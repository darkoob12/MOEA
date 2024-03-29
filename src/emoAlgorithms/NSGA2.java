/**
 * for representing a set i used HashSet<>
 * and for representing a collection of sets i used ArrayList<HashSet<>>
 */
package emoAlgorithms;


import java.io.FileWriter;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Random;



/** NSGA2 algorithm
 * 
 * this is exactly the algorithm proposed in the original work by
 * Deb in 2002
 * 
 * @author Shahab
 *
 */
public class NSGA2 extends EvoAlgorithm {	
	public Population cur_pop;		// we can use this as the combined population	
	public Population child_pop;	
	public Population next_pop;
	public HashSet<Chromosome> non_dominated_set;	//final non-dominated set of solutions
	

	/**
	 * simple constructor for the class
	 */
	public NSGA2() {
		mSilent = false;
		this.setPc(_defaultPc);
		this.setPm(_defaultPm);
		this.setPop_size(_defaultPopSize);
		this.setMaxGen(_defaultMaxGen);
		this.setMaxFitEval(_defailtMaxFcnEval);
		this.num_fit_eval = 0;
		this.setEthaC(20);
		this.setEthaM(20);
	}
	
	/** Constructor 
	 * in this constructor, problem is known while creating an instance of the algorithm
	 * @param argProb
	 */
	public NSGA2(MOProblem argProb) {
		mProblem = argProb;
		mSilent = false;
		this.setPc(0.9);
		this.setPm(1 / (double) mProblem.getNumVariables());
		this.setPop_size(100);
		this.setMaxGen(250);
		this.setMaxFitEval(25000);
		this.setEthaC(20);
		this.setEthaM(20);
		this.num_fit_eval = 0;
	}
	
	/* (non-Javadoc)
	 * @see emoAlgorithms.EvoAlgorithm#run()
	 */
	@Override
	public void run() {
		// create initial population in the cur_pop variable
		initialization();
		//Evaluating fitness for the randomly generated chromosomes
		evaluate(cur_pop);
		
		// rank initial generation , this will make our implementation adhering the original paper
		ArrayList<HashSet<Chromosome>> init_frontiers = fast_non_dominated_sort(cur_pop);
		for (int s = 0;s < init_frontiers.size();s++) {
			cd_assignment(init_frontiers.get(s));
		}

		gen_count = 0;		//initializing generation counter	
		// main loop
		while (true) {
			// create new population using cur_pop and saving in child_pop
			make_new_pop();
			// evaluating fitness for the offspring population
			evaluate(child_pop);
			// combine the parent population and the children population 
			cur_pop.mergeWith(child_pop);
			// select next generation from union of parent and children populations
			//---------------------------------------------------------------------
			// sorting the merged population into different domination fronts
			ArrayList<HashSet<Chromosome>> script_f = fast_non_dominated_sort(cur_pop);
			// creating a new empty population
			next_pop = new Population(this.getPop_size());
			// while we can add a whole frontier to the next population
			
			/*
			 * debugging code
			 */
/*			System.out.println(script_f.get(0).size());
			for (Chromosome ch : script_f.get(0)) {
				System.out.println(ch);
			}*/
			
			for (int k = 0;k < script_f.size();k++) {
				//check whether we can add next front to the selected solutions completely or we 
				//must select only some of its members
				if ((next_pop.mMembers.size() + script_f.get(k).size()) <= this.getPop_size()) {
					// since crowding distance is used in tournament selection we need to do it for each set
					cd_assignment(script_f.get(k));
					//add the whole set to the next generation
					next_pop.add_set(script_f.get(k));
				} else {
					// computing crowding distance for this frontier
					cd_assignment(script_f.get(k));
					// we must select the remaining solutions from the next front
					// converting the set to array since we do not have ordering relation in sets
					Chromosome[] last_front = new Chromosome[script_f.get(k).size()];
					last_front = script_f.get(k).toArray(last_front);
					cd_sort(last_front);	// sorting the last frontier according to the crowded dominance relation
					// needed solutions for filling the next generation's population
					int needed_chroms = this.getPop_size() - next_pop.mMembers.size();
					// check for possible errors
					if (needed_chroms > last_front.length) {
						System.out.println("early exiting the main loop : last front has not enough members");
						System.exit(2);
					}
					for (int i = 0;i < needed_chroms;i++) {
						next_pop.mMembers.add(last_front[i]);
					}
					break;		//next_pop is full - no need for other frontiers
				}
			}
			// passing a generation 
			cur_pop = next_pop;
			gen_count++;
			// check for the stopping condition
			if (gen_count > getMaxGen()) {
				cur_pop.reset_dom_count();		//this will indicate the non-dominated solutions
				break;
			}
		}
		
	}
	
	/**
	 * this function will be dependent to the problem.
	 * and will set random values in decision variables ranges
	 * will create a new population cur_pop and will randomly set values
	 */
	public void initialization() {
		Random rnd = new Random(System.currentTimeMillis());
		// we need to fill current population
		cur_pop = new Population(this.getPop_size());
		
		for (int i = 0;i < getPop_size();i++) {
			// creating  new chromosome
			Chromosome new_chrom = new Chromosome(mProblem.getNumVariables());
			for (int j = 0;j < mProblem.getNumVariables();j++) {
				double lb = mProblem.decision_extremes[j][1];
				double hb = mProblem.decision_extremes[j][0];
				new_chrom.mGenes[j] = lb + rnd.nextDouble()*(hb - lb);
			}
			
			// adding new chromosome to the current population 
			cur_pop.mMembers.add(new_chrom);
		}
	}
	
	
	/**
	 * evaluates fitness vector for each chromosome in the given population
	 * @param pop	a population which we want to set fitness for it's members
	 */
	public void evaluate(Population pop) {
		for (Chromosome chrom : pop) {
			double sol[] = chrom.decode();	// decoding from geno-type space representation
			chrom.fitness_vector = mProblem.fitness(sol);	// evaluation solution's fitness
			this.num_fit_eval++;		//counting number of fitness evaluations
		}
	}
	
	/**
	 * divide solutions to different domination fronts and
	 * sort them according to the front rank which they belongs to.
	 * @param pop population to be sorted by non-domination rank  
	 */
	public ArrayList<HashSet<Chromosome>> fast_non_dominated_sort(Population pop) {
		// this is the script F in the NSGA2 Paper. and contains Fronts
		// each front is a set containing chromosomes
		ArrayList<HashSet<Chromosome>> F = new ArrayList<HashSet<Chromosome>>();
		//first we create highest non-dominated front - F1
		HashSet<Chromosome> Q = new HashSet<Chromosome>();
		// chrom is P in the original work
		for (Chromosome chrom : pop) {
			//initialize internal fields of chromosome
			chrom.domination_set.clear();		//setting to empty set
			chrom.setDCount(0);					//no one dominates this chromosome
			// other_chrom is Q in original work
			for (Chromosome other_chrom : pop) {
				if (mProblem.dominates(chrom.fitness_vector, other_chrom.fitness_vector)) {
					//add other_chrom to current chromosome's domination set
					chrom.domination_set.add(other_chrom);
				} else if (mProblem.dominates(other_chrom.fitness_vector, chrom.fitness_vector)) {
					//increase current chromosome's dominant count by 1
					chrom.setDCount(chrom.getDCount() + 1);
				} 
			}
			if (chrom.getDCount() == 0) {
				// this chromosome is non-dominated
				chrom.setRank(0);	//set rank to one
				Q.add(chrom);	//add to first non-dominated front
			}
		} // for loop - iterating through all chromosomes
		F.add(Q);		// adding first front to the collection		
		
		// now the first front is created , we will create other fronts
		// by doing this we will reduce computation time severely
		int i = 0;	// indicating the first front
		while (true) {	// this loop stops when there is no other example to rank
			Q = new HashSet<Chromosome>();	// a temporary set for saving next Frontier
			for (Chromosome p : F.get(i)) {
				for (Chromosome q : p.domination_set) {
					q.setDCount(q.getDCount() - 1);		//decrease their domination count by 1
					if (q.getDCount() == 0) {
						// this means that q belongs to the next non-dominated front
						q.setRank(i + 1);	// setting q to the next rank- zero based ranks
						Q.add(q);	// adding q to the next front
					}
					else if (q.getDCount() < 0) {
						//System.out.println("ERROR : Negative value for domination count");
						System.out.println("FOO");
					}
				}
			}
			if (Q.isEmpty()) {
				break;
			} else {
				i++;
				F.add(Q);
			}
		} //while loop
		return F;
	}	
	
	/**
	 * returns sum of sizes of all members of a given collection	
	 * @param col	a collection of Sets
	 * @return	a non-negative integer
	 */
	protected int collectionTotalSize(ArrayList<HashSet<Chromosome>> col) {
		int ret = 0;
		for (HashSet<Chromosome> set : col) { 
			ret += set.size();
		}
		return ret;
	}
	
	/**
	 * prints total size of a given collection along with size of its members each one in a line
	 * @param col	a collection of sets
	 */
	protected void printCollection(ArrayList<HashSet<Chromosome>> col) {
		System.out.println("\nSize of Collection: " + collectionTotalSize(col));
		System.out.println("------------------------------------------------------------");
		int i = 1;
		for(HashSet<Chromosome> set : col) {
			System.out.println(i + "=>" + set.size());
			i++;
		}
		System.out.println("------------------------------------------------------------\n");
	}
	
	/** generate new population
	 * creates a new population by applying genetic operators like parent selection,
	 * crossover, mutation, ... and saves the new offsprings in the child_pop object
	 * @param isInitial whether this current population is initial or not.
	 */
	public void make_new_pop() {
		// psudo-random number generator for applying mutation and crossover rates
		Random rnd = new Random(System.currentTimeMillis());
		double rnd_num = 0;   // will be used in random decision making
		// creating an empty population
		child_pop = new Population(cur_pop.getSize());
		// creating new chromosomes until the population is full
		while (child_pop.mMembers.size() < child_pop.getSize()) {
			// variables for storing created children
			Chromosome child_1 = null;
			Chromosome child_2 = null;
			// select two parents
			Chromosome par_1 = tournament_select();
			Chromosome par_2 = tournament_select();
			rnd_num = rnd.nextDouble();
			// apply crossover?
			if (rnd_num <= this.getPc()) {
				Chromosome[] children_list = simulated_bin_xover(par_1, par_2);
				child_1 = children_list[0];
				child_2 = children_list[1];
			} else {
				// in this case we simply copy the parents
				child_1 = new Chromosome(par_1);
				child_2 = new Chromosome(par_2);
			}
			// apply mutation?
			rnd_num = rnd.nextDouble();
			if (rnd_num <= this.getPm()) {
				poly_mutation(child_1);
				poly_mutation(child_2);
			}
			// first check whether the new chromosomes are feasible or not.
			check_bounds(child_1);
			check_bounds(child_2);
			// now we add the new children to the population
			// but before that we check for enough capacity of the population
			if (child_pop.mMembers.size() < child_pop.getSize()) {
				child_pop.mMembers.add(child_1);
			}
			if (child_pop.mMembers.size() < child_pop.getSize()) {
				child_pop.mMembers.add(child_2);
			}
		}
	}

	
	/**
	 * checks whether a givens chromosomes genes are in the correct interval or not
	 * @param ch	chromosome to check
	 */
	protected void check_bounds(Chromosome ch) {
		for (int i = 0;i < ch.mGenes.length;i++) { 
			if (ch.mGenes[i] < mProblem.decision_extremes[i][0]) {
				// is less than the lower bound should get corected
				ch.mGenes[i] = mProblem.decision_extremes[i][0];
			} else if (ch.mGenes[i] > mProblem.decision_extremes[i][1]) {
				//is greater than the upper bound 
				ch.mGenes[i] = mProblem.decision_extremes[i][1];
			}
		}
	}
	
	/**
	 * selects a chromosome among the population using binary tournament selection
	 * for participating in recombination
	 * @return	selected chromosome
	 */
	protected Chromosome tournament_select() {
		Random rnd = new Random(System.currentTimeMillis());
		//generate two random indices
		int index_1 = rnd.nextInt(cur_pop.getSize());
		int index_2 = rnd.nextInt(cur_pop.getSize());
		//check that they are not equal
		while (index_1 == index_2) {
			index_2 = rnd.nextInt(cur_pop.getSize());
		}
		Chromosome first_chrom = cur_pop.mMembers.get(index_1);
		Chromosome second_chrom = cur_pop.mMembers.get(index_2);
		// initial population dose not have any rank or crowding distance
		if (first_chrom.crowded_compare_to(second_chrom)) {
			return first_chrom;
		} else {
			return second_chrom;
		}
	}
	
	
	/** SBX
	 * 	simulated binary crossover
	 * 	for each loci of the chromosomes we need to generate a random number from
	 *  a specific distribution.
	 * @param parent_1	first parent
	 * @param parent_2	second parent
	 * @return	an array containing the two generated offsprings
	 */
	public Chromosome[] simulated_bin_xover(Chromosome parent_1, Chromosome parent_2) {
		//psuedo random number generator
		Random rnd = new Random(System.currentTimeMillis());
		// creating two new chromosomes
		Chromosome childs[] = new Chromosome[2];
		for (int i = 0;i < childs.length;i++) {
			childs[i] = new Chromosome(parent_1.getChromeSize());
			// adding reference to parents, it will be useful later
			childs[i].parents = new Chromosome[2];
			childs[i].parents[0] = parent_1;
			childs[i].parents[1] = parent_2;
		}
		// for each loci in the chromosomes we need to generate a random number.
		double lottery;
		for (int i = 0;i < parent_1.getChromeSize();i++){
			lottery = rnd.nextDouble();
			if (lottery <= 0.5) { 
				double beta = beta_generator_rev(getEthaC());	// generates a new beta for each loci
				childs[0].mGenes[i] = 0.5*((1-beta)*parent_1.mGenes[i] + (1+beta)*parent_2.mGenes[i]);
				childs[1].mGenes[i] = 0.5*((1+beta)*parent_1.mGenes[i] + (1-beta)*parent_2.mGenes[i]);
			} else {
				childs[0].mGenes[i] = parent_1.mGenes[i];
				childs[1].mGenes[i] = parent_2.mGenes[i];
			}
		}

		return childs;
	}

	
	/**
	 * generates a sample of beta ,in the sbx operator, distribution
	 * @param eta	this is determine how far the children be relative to their parent
	 * @return	a positive double
	 */
	protected double beta_generator(double eta) {
		double beta = 0;
		// a psudo-random number generator
		Random rnd = new Random(System.currentTimeMillis());
		
		// we need a random number for determining whether beta is less than one or greater than 1
		// since one is a symmetric point for the distribution.
		double rnd_num_1 = rnd.nextDouble();
		// this is for sampling from the beta pdf.
		double rnd_num_2 = rnd.nextDouble();
		if (rnd_num_1 <= 0.5) {
			beta = Math.pow(2 * rnd_num_2, 1 / (1 + eta));
		} else {
			beta = 1 / Math.pow(2 * (1 - rnd_num_2), 1 / (1 + eta));
		}
		
		return beta;
	}
	
	/**
	 * sampling from the beta distribution stated in the paper using only one random number.
	 * @param eta	distribution index for Beta
	 * @return	a sample
	 */
	protected double beta_generator_rev(double eta) {
		double beta = 0;
		Random rnd = new Random(System.currentTimeMillis());
		double seed = rnd.nextDouble();
		if (seed <= 0.5) {
			beta = Math.pow(2 * seed, 1.0 / (eta + 1));
		} else {
			beta = Math.pow(1 / (2 * (1 - seed)), 1.0 / (eta + 1));
		}
		return beta;
	}
	
	/**
	 * this mutation applies variable-wise perturbation 
	 * and for each loci we need to generate a random sample from a polynomial pdf
	 * @param chr  chromosome to be mutated
	 */
	protected void poly_mutation(Chromosome chr) {
		for (int i = 0;i < chr.getChromeSize();i++) {
			double delta = delta_generator(getEthaM());
			chr.mGenes[i] += (mProblem.decision_extremes[i][0] - mProblem.decision_extremes[i][1]) * delta;
		}
	}
	
	/**
	 * random number generator for polynomial distribution for polynomial mutation operator
	 * @param eta this is directly related to the mutation amount
	 * @return	a positive double value
	 */
	protected double delta_generator(double eta) {
		double delta = 0;	
		Random rnd = new Random(System.currentTimeMillis());	// psudo-random number generator
		double rnd_num_1 = rnd.nextDouble();
		if (rnd_num_1 < 0.5) {
			delta = Math.pow(2 * rnd_num_1, 1 / (eta + 1)) - 1;
		} else {
			delta = 1 - Math.pow(2 * (1 - rnd_num_1), 1 / (eta + 1));
		}
		return delta;
	}
	
	/** Crowding distance assignment
	 * 
	 * for each chromosome in the set calculates the crowding distance
	 * and save this value in a field of the chromosome object 
	 * 
	 * @param non_dominated_set a set containing some chromosomes
	 */
	public void cd_assignment(HashSet<Chromosome> non_dominated_set) {
		// first check whether this set is empty or not?
		if (non_dominated_set.isEmpty()) {
			return;
		}
		//we can not do this initialization while creating the objects since
		//this calculation may occur many times during the life of chromosome
		for (Chromosome chrom : non_dominated_set) {
			chrom.crowding_distance = 0;	
		}
		Chromosome lst[] = new Chromosome[non_dominated_set.size()];
		for (int m  = 0;m < mProblem.getNumObjectives();m++) {
			// sort the set of solutions according to m'th objective
			lst = non_dominated_set.toArray(lst);
			Utility.chr_sort(lst, m, Utility.SortType.ASC);
			//assign the first and last solution of the list
			//a crowding distance value of infinity - so they always will be selected
			lst[0].crowding_distance = lst[lst.length - 1].crowding_distance = Double.MAX_VALUE;
			double denominator = lst[lst.length - 1].fitness_vector[m] - lst[0].fitness_vector[m];	
			for (int j = 1;j < lst.length - 1;j++) {
				double foo = lst[j+1].fitness_vector[m] - lst[j-1].fitness_vector[m];
				// instead of using global minimum and maximum of m'th objective i used
				// max and min of this objective in the current set of solutions.
				lst[j].crowding_distance += foo/denominator;
			}
			
		}
	}
	
	/**
	 * sort a list of chromosomes according to the crowded_dominance_relation
	 * the most crowded chromosomes will be  at top of the list
	 * i will implement a bubble sort again  
	 * @param chrom_list	 an array of chromosomes
	 */
	public void cd_sort(Chromosome[] chrom_list) {
		for (int i = 0;i < chrom_list.length - 1;i++) {
			for (int j = i;j < chrom_list.length;j++) {
				if (chrom_list[j].crowding_distance > chrom_list[i].crowding_distance) {
					Chromosome temp = chrom_list[i];
					chrom_list[i] = chrom_list[j];
					chrom_list[j] = temp;
				}
			}
		}
	}
	
	/**
	 * creating a text file containing current population
	 * @param path	
	 */
	protected void create_log_file(String path) {
		// create log file.
		FileWriter writer;
		try {
			writer = new FileWriter(path);
			for (Chromosome p : cur_pop) {
				writer.write(cur_pop.mMembers.indexOf(p) + " : fit = " + Utility.arr2str(p.fitness_vector) 
				+ "\n domination_set = { ");
				for (Chromosome q : p.domination_set) {
					writer.write(cur_pop.mMembers.indexOf(q) + " ");
				}
				writer.write("} dominant_count = " + p.getDCount() + "\n\n\n");
			}
			writer.flush();
			writer.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}

