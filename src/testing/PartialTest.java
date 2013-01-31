package testing;

import java.util.HashSet;

import emoAlgorithms.*;

public class PartialTest {

	public static void main(String[] args) {
		ZDT1 problem = new ZDT1(2);
		NSGA2 alg = new NSGA2(problem);
		HashSet<Chromosome> set = new HashSet<Chromosome>();
		Chromosome foo;
		double[] bar;
		foo = new Chromosome(2);
		foo.mGenes[0] = 0.31;
		foo.mGenes[1] = 0.89;
		bar = new double[2];
		bar[0] = 0.31;
		bar[1] = 6.10;
		foo.fitness_vector = bar;
		set.add(foo);
		foo = new Chromosome(2);
		foo.mGenes[0] = 0.22;
		foo.mGenes[1] = 0.56;
		bar = new double[2];
		bar[0] = 0.22;
		bar[1] = 7.09;
		foo.fitness_vector = bar;
		set.add(foo);
		foo = new Chromosome(2);
		foo.mGenes[0] = 0.79;
		foo.mGenes[1] = 2.14;
		bar = new double[2];
		bar[0] = 0.79;
		bar[1] = 3.97;
		foo.fitness_vector = bar;
		set.add(foo);
		foo = new Chromosome(2);
		foo.mGenes[0] = 0.27;
		foo.mGenes[1] = 0.87;
		bar = new double[2];
		bar[0] = 0.27;
		bar[1] = 6.93;
		foo.fitness_vector = bar;
		set.add(foo);
		//test set is constructed now.
		alg.cd_assignment(set);
		for(Chromosome ch : set) {
			System.out.println(ch.toString() + "  =>  " + ch.crowding_distance); 
		}
		Chromosome list[] = new Chromosome[4];
		set.toArray(list);
		Utility.chr_sort(list, 1, Utility.SortType.ASC);
		System.out.println("----------------------");
		for (int i = 0;i < 4;i++) {
			System.out.println(list[i]);
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
