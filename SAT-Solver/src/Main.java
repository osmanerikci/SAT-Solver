
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Main {
	static Integer variableNum;
	static Integer equationNum;

	public static void main(String[] args) {

		try {
			PrintWriter outputFile = new PrintWriter("output.cnf");

			boolean SAT = false;
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader("input3.cnf"));
				System.out.println("started");
				String line = reader.readLine();

				String[] numbers = line.split("\\s+");
				variableNum = Integer.parseInt(numbers[2]);
				equationNum = Integer.parseInt(numbers[3]);
				Integer[][] myArray = new Integer[equationNum][variableNum];

				for (int row = 0; row < equationNum; row++) {

					String tempstr = (String) reader.readLine();
					int[] temp = Arrays.stream(tempstr.split("\\s+")).mapToInt(Integer::parseInt).toArray();
					for (int column = 0; column < variableNum; column++) {
						if (column >= temp.length) {
							continue;
						}
						if (temp[column] == 0) {
							continue;
						} else {
							myArray[row][Math.abs(temp[column]) - 1] = temp[column];
							// System.out.print(myArray[row][Math.abs(temp[column])-
							// 1]);
						}
						// System.out.print(" ");
					}
					// System.out.println("");
				}
				for (int row = 0; row < equationNum; row++) {
					for (int column = 0; column < variableNum; column++) {
						if (myArray[row][column] == null) {
							myArray[row][column] = 0;
						}
						// System.out.print(myArray[row][column] + " ");
					}
					// System.out.println("");
				}
				int possibilities = (int) Math.pow(2, variableNum);
				for (int i = 0; i < possibilities; i++) {
					String truthInBinary = Integer.toBinaryString(i);
					while (truthInBinary.length() < variableNum) {
						truthInBinary = "0" + truthInBinary;
					}
					// System.out.println(truthInBinary);
					if (controlInArray(myArray, truthInBinary) == true) {

						for (int index = 0; index < truthInBinary.length(); index++) {
							if (truthInBinary.charAt(index) == '0') {
								outputFile.print(((index + 1) * -1) + " ");
								System.out.print(((index + 1) * -1) + " ");

							} else {
								outputFile.print((index + 1) + " ");
								System.out.print((index + 1) + " ");

							}
						}
						outputFile.println("");
						System.out.println("");
						SAT = true;

					}

				}
				if (SAT == false) {
					outputFile.println("UNSAT");
					System.out.println("UNSAT");
				} else {
					outputFile.println("SAT");
					System.out.println("SAT");

				}
				System.out.println("output.cnf file is on project directory");

				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			outputFile.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public static boolean controlInArray(Integer[][] myArray, String binary) {
		int inRow = 0;
		boolean betweenRows = true;
		Integer[][] truth = new Integer[equationNum][variableNum];
		for (int row = 0; row < truth.length; row++) {
			inRow = 0;
			for (int column = 0; column < truth[row].length; column++) {

				truth[row][column] = myArray[row][column];
				if (truth[row][column] != 0) {
					truth[row][column] = truth[row][column] / Math.abs(truth[row][column]);
				}
				if (binary.charAt(column) == '0' && truth[row][column] != 0) {
					truth[row][column] *= -1;
				}
				if (binary.charAt(column) == '1' && truth[row][column] < 0) {
					truth[row][column] = 0;
				}
				if (truth[row][column] < 0) {
					truth[row][column] = 0;
				}
//				 System.out.print(truth[row][column] + " ");
				inRow += truth[row][column];
			}
//		 		System.out.println("");
			if (inRow == 0) {
				betweenRows = false;
			}
		}

		return betweenRows;
	}
}