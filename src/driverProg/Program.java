package driverProg;

import emoAlgorithms.*;

public class Program {

	/**
	 * main routine of the program
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		// first define a problem to solve;
		ZDT myProb = new ZDT1(9);
		
		
		// Creating an instance of algorithm to solve the problem
		NSGA2 myAlg = new NSGA2(myProb);
		myAlg.run();
		System.out.println(myAlg.non_dominated_set.size());
		System.out.println("Hello World~");
	}

}
