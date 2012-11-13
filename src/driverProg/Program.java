package driverProg;

import emoAlgorithms.*;

public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// first define a problem to solve;
		DTLZ1 myProb = new DTLZ1(3);
		
		double f1[] = {2.11, 1.342, 3.333};
		double f2[] = {2.1, 1.342, 3.343};
		
		
		if (myProb.dominates(f1, f2)) {
			System.out.println("f1 dominates f2");
		} else if (myProb.dominates(f2, f1)) {
			System.out.println("f2 dominates f1");
		} else {
			System.out.println("f1 and f2  are non_dominated");
		}
		


		
		
		//Creating an instance of algorithm to solve the problem
//		NSGA2 myAlg = new NSGA2(myProb);
//		myAlg.run();

		System.out.println("Hello World~");
	}

}
