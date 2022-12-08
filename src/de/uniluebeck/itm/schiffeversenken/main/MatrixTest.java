package de.uniluebeck.itm.schiffeversenken.main;

/**
 * @author	Bendix Voss
 * @author	Fabio Junghans
 * Group	169
 */
public class MatrixTest {
	
	/**
	 * amount of columns the matrix gets
	 */
	private final int columns = 10;
	
	/**
	 * amount of rows the matrix gets
 	*/
	private final int rows = 8;

	/**
	 * the initialized matrix with the predetermined amount of columns and rows
	 */
	private final int[][] field = new int[columns][rows];
	
	/**
	 * main method use this method to use other methods and construct a matrix
	 */
	public static void main(String[] args) {
		
		MatrixTest test = new MatrixTest(); 
		
		test.iniNormal();
		test.printer();
		
		test.columnSums(5);
		
		System.out.println();
		System.out.println();

		
		test.firstRowToVariables(5);
		test.printer();
		
		System.out.println();
		
		test.iniWithVariable(5);
		test.printer();
	}
	/**
	 * iniNormal use this method to initialize the matrix normally
	 */
	public void iniNormal() {
 		int z = 0;
		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				field[x][y] = z;
				z++;
			}
		}
	}
	
	/**
	 * printer use this method to print out the normally initialized matrix 
	 */
	public void printer() {
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				if (field[x][y] < 10) {
					System.out.print("0" + field[x][y] + " ");
				}
				else {
				System.out.print(field[x][y] + " ");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * iniWithVariable use this method to initialize the matrix with a variable on the main diagonal
	 * @param k variable to which the main diagonal gets set to
	 */
	public void iniWithVariable(int k){
		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				if ( x == y) {
					field[x][y] = k;
				}
			}
		}
	}
	
	/**
	 * columnSums used this method to calculate and print out the sum of each column
	 * @param m temporary variable used to save the sum of each column 
	 */
	public void columnSums(int m){
		System.out.println("---------------------------------");
		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				m += field[x][y];
			}
			System.out.print(m + " ");
			m = 0;
		}
	}
	
	/**
	 * firstRowToVariable use this method to set the first row to a desired variable
	 * @param k variable the first to rows get set to 
	 */
	public void firstRowToVariables(int k){
		for (int x = 0; x < columns; x++) {
			for (int y = 0; y < rows; y++) {
				if ( y == 0) {
					field[x][y] = k;
				}
			}
		}
	}		
}