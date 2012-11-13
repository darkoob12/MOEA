package emoAlgorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/** Population
 * 
 * this population class contains real coded chromosomes
 * 
 * @author Shahab
 *
 */
public class Population implements Iterable<Chromosome> {
	private int mSize;
	
	/**
	 * an array of chromosomes, population size can not change during the run
	 */
	public ArrayList<Chromosome> mMembers; 
	
	/** Constructor
	 * 
	 * creates a new instance of this class
	 * 
	 * @param argSize
	 */
	public Population(int argSize) {
		setSize(argSize);
		mMembers = new ArrayList<Chromosome>();
	}
	
	/** Copy Constructor
	 *  creates a copy of the argument
	 * @param otherPop
	 */
	public Population(Population otherPop) {
		setSize(otherPop.getSize());
		mMembers = otherPop.mMembers;
	}
	
	/**
	 * merges another population with this one
	 * it will be used when we are trying to merge offspring population and parent population 
	 * @param pop	other Population
	 */
	public void mergeWith(Population pop) {
		setSize(getSize() + pop.getSize());		// increasing population size
		for (Chromosome chrom : pop) {
			mMembers.add(chrom);
		}
	} 

	/** adds a set of chromosomes to the population
	 * this will be useful when we want to add a complete frontier to the population
	 * @param set	a hash set containing chromosomes
	 */
	public void add_set(HashSet<Chromosome> set){
		for (Chromosome chr : set) {
			this.mMembers.add(chr);
		}
	}
	
	/**
	 * @return the mSize
	 */
	public int getSize() {
		return mSize;
	}

	/**
	 * @param mSize the mSize to set
	 */
	public void setSize(int mSize) {
		this.mSize = mSize;
	}

	/**
	 * return a string containing members of this population
	 */
	@Override
	public String toString() {
		String ret = "";
		for (Chromosome p : this) {
			ret += p.toString() + "\n";
		}
		return ret;
	}

	/**
	 * this function help other classes iterate through this population easily
	 */
	@Override
	public Iterator<Chromosome> iterator() {
		return mMembers.iterator();
	}
}
