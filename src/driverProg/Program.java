package driverProg;

import emoAlgorithms.*;

public class Program {

	/**
	 * main routine of the program
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		
		// first define a problem to solve;
		ZDT1 myProb = new ZDT1(20);
		
		
		// Creating an instance of algorithm to solve the problem
		NSGA2 myAlg = new NSGA2(myProb);
		myAlg.run();
		System.out.print("Number of Variables : ");
		System.out.println(myProb.known_solutions[0].length);
		System.out.print("Number of Objectives : ");
		System.out.println(myAlg.cur_pop.mMembers.get(0).fitness_vector.length);
		double gamma = myProb.compute_gamma(myAlg.cur_pop);
		System.out.println(gamma);
		if (gamma == 0) {
			for (Chromosome ch : myAlg.cur_pop) {
				if (ch.getDCount() == 0) {
					System.out.println(Utility.arr2str(ch.mGenes));
				}
			}
		}
		System.out.println("Hello World~");
	}
		
}
