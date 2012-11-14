package emoAlgorithms;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;

/** Chromosome
 * 
 * it is a real coded chromosome
 * 
 * @author Shahab
 *
 */
public class Chromosome implements Comparator<Chromosome>, Serializable {
	/**
	 * version for the class for later changes
	 */
	private static final long serialVersionUID = 1L;
	private int mChromeSize;
	public double[] mGenes;
	// these fields are required when using NSGA2 and its sub-classes
	/**
	 * a set containing chromosomes which this one dominates.
	 * Si sets in the original work
	 */
	public HashSet<Chromosome> domination_set;
	/**
	 * number of solutions which dominate this one
	 * it this count becomes zero then chromosome is non-dominated
	 */
	protected int dominant_count;	
	/**
	 * each chromosome should know to which domination front it belongs
	 */
	protected int domination_rank;		
	public double[] fitness_vector;		// this chromosome knows nothing about number of objectives
										// other classes will set this field
	public double crowding_distance;	//this will be set by NSGA2 algorithm
	public Chromosome parents[];		// reference to the parents of this chromosome if available
	
	//////////////////////////////////////////////////////////////////////////////////////////
	//Constructors and methods
	//////////////////////////////////////////////////////////////////////////////////////////
	
	/** Constructor
	 * 
	 * this function creates a new object of this type
	 * simply initialize the member variables to default values
	 * @param argSize
	 */
	public Chromosome(int argSize) {
		this.setChromeSize(argSize);
		mGenes = new double[this.getChromeSize()];
		domination_set = new HashSet<Chromosome>();
		dominant_count = 0;
		domination_rank = Integer.MAX_VALUE;
	}
	
	
	/**
	 * this is a copy constructor
	 * @param source a chromosome to copy data from
	 */
	public Chromosome(Chromosome source) {
		this.crowding_distance = source.crowding_distance;
		this.dominant_count = source.getDCount();
		this.domination_rank = source.domination_rank;
		this.domination_set = new HashSet<Chromosome>();	//we do not need this to be copied
		this.fitness_vector = source.fitness_vector;
		this.mChromeSize = source.getChromeSize();
		this.mGenes = new double[source.mGenes.length];
		for (int i = 0;i < mGenes.length;i++) {
			mGenes[i] = source.mGenes[i];
		}
		this.parents = source.parents;
	}
	//////////////////////////////////////////////////////////////////////////////////////////
	//getter / setter methods
	//////////////////////////////////////////////////////////////////////////////////////////

	
	/**	Gets Size
	 * 
	 * number of genes in a chromosome
	 * 
	 * @return the mChromeSize
	 */
	public int getChromeSize() {
		return mChromeSize;
	}
	/** Sets Size
	 * 
	 * sets number of genes in a chromosome
	 * 
	 * @param mChromeSize the mChromeSize to set
	 */
	public void setChromeSize(int mChromeSize) {
		this.mChromeSize = mChromeSize;
	}
	
	/**
	 * returns domination rank of the chromosome
	 * @return
	 */
	public int getRank() {
		return domination_rank;
	}
	
	/**
	 * sets a new domination rank for this chromosome
	 * @param newRank
	 */
	public void setRank(int newRank) {
		this.domination_rank = newRank;
	}
	
	/**
	 * setter and getter for dominant count
	 */
	public int getDCount() {
		return dominant_count;
	}
	public void setDCount(int newCount){
		this.dominant_count = newCount;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////
	// other methods
	//////////////////////////////////////////////////////////////////////////////////////////

	

	/**
	 * since this chromosome is real coded there is nothing for decoding
	 * @return a double vector which is in the pheno-type space
	 */
	public double[] decode() {
		return this.mGenes;
	}
	
	
	/**
	 * compares current chromosome with the argument according to rank an crowding distance
	 * true -> this is better than the other 
	 * false -> the other is better than this one 
	 * @param other_chrom
	 * @return
	 */
	public boolean crowded_compare_to(Chromosome other_chrom) {
		boolean ret = false;
		if ((this.getRank() < other_chrom.getRank()) || 
		((this.getRank() == other_chrom.getRank()) && (this.crowding_distance > other_chrom.crowding_distance))) {
			ret = true;		
		}
		return ret;
	}

	/**
	 * this will help us to use java built-in sorting algorithms
	 */
	@Override
	public int compare(Chromosome arg0, Chromosome arg1) {
		if (arg0.crowded_compare_to(arg1)) {
			return 1;
		} else {
			return -1;
		}
	}
	
	/**
	 * this is an override of object method
	 * returns a string describing this chromosome
	 */
	@Override
	public String toString() {
		String ret = "< ";
		for (double el : mGenes) {
			ret += el + " ";
		}
		ret += ">";
		if (this.fitness_vector != null) {
			ret += " :: ( ";
			for (double objective : fitness_vector) {
				ret += objective + " ";
			}
			ret += ")";
		}
		return ret;
	}
	
	/**
	 * reset this object's genes to zero vector
	 */
	public void reset() { 
		for (int i = 0;i < mGenes.length;i++) {
			mGenes[i] = 1.1111;
		}
	}
	
}
