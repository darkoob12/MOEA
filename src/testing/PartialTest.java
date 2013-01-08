package testing;

import emoAlgorithms.*;

public class PartialTest {

	public static void main(String[] args) {
		ZDT1 problem = new ZDT1(2);
		NSGA2 alg = new NSGA2(problem);
		alg.initialization();
		alg.evaluate(alg.cur_pop);
		
		Chromosome foo[] = alg.simulated_bin_xover(alg.cur_pop.mMembers.get(0), alg.cur_pop.mMembers.get(1));
		
		for (int i = 0;i < 2;i++) {
			print(alg.cur_pop.mMembers.get(i).toString());			
			print(foo[i].toString());
			print("----------");
		}
	}
	
	/**
	 * prints to the console and goes to the next long
	 * @param str	string to be printed
	 */
	public static void print(String str) {
		System.out.println(str);
	}

}
