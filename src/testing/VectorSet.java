package testing;

import java.util.ArrayList;
/**
 * A Data Structure storing a set of real vectors
 * which dose not allow duplicate items.
 * @author Shahab
 *
 */
public class VectorSet {
	private ArrayList<double[]> elements;		//saving elements
	
	/**
	 * returns cardinality of the set
	 */
	public int getSize() {
		return elements.size();
	}
	
	/**
	 * Constructor very simple
	 */
	public VectorSet() {
		elements = new ArrayList<double[]>();
	}

	/**
	 * adding new vectors to the set - checks for duplicates
	 */
	public void add(double[] vect) {
		if (!Contains(vect)) {
			elements.add(vect);
		}
	}
	
	/**
	 * returns true if the set contains the argument
	 */
	public boolean Contains(double[] new_vect) { 
		boolean ret = false;
		for (double[] vect : elements) {
			if (isEqual(vect, new_vect)) {
				ret = true;
				break;
			}
		}
		return ret;
	}
	
	/**
	 * returns true if the two arguments are the same. 
	 */
	public boolean isEqual(double x[], double y[]) {
		boolean ret = true;
		if (x.length != y.length) {
			ret = false;
		} else {
			for (int i = 0;i < x.length;i++){
				if (x[i] != y[i]) {
					ret = false;
					break;
				}
			}
		}
		return ret;
	}
}
