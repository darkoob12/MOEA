package driverProg;

import emoAlgorithms.*;

public class Program {
	
	public static final int _inputDim = 5;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// first define a problem to solve;
		DTLZ1 myProb = new DTLZ1(_inputDim);
		
		//Creating an instance of algorithm to solve the problem
		NSGA2 myAlg = new NSGA2(myProb);
		myAlg.run();

		System.out.println("Hello World~");
	}

}
