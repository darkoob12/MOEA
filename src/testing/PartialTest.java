package testing;

import emoAlgorithms.*;
import java.util.Random;

public class PartialTest {

	public static void main(String[] args) {
		Random rnd_gen = new Random(System.currentTimeMillis());
		ZDT problem = new ZDT1(2);
		// generating random vectors.
		double matrix[][] = new double[10][];
		for (int i = 0;i < 10;i++){
			matrix[i] = new double[2];
			for (int j = 0;j < 2;j++) {
				matrix[i][j] = rnd_gen.nextDouble();
			}
		}
		// testing the domination relation
		for (int i = 0;i < 10;i++) {
			for (int ii = 0;ii < 10;ii++) {
				if (problem.dominates(matrix[i], matrix[ii])) {
					System.out.println(Utility.arr2str(matrix[i])
							+ " dominates " + Utility.arr2str(matrix[ii]));
				} else {
					System.out.println(Utility.arr2str(matrix[i])
							+ " do not dominate " + Utility.arr2str(matrix[ii]));
				}
			}
		}
	}

}
