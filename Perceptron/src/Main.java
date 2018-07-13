import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author PC
 *
 */
public class Main {

	private static final String PATH_TESTCASE_INSEPARABLE = "src\\data\\testInseparable.csv";
	private static final String PATH_TESTCASE_SEPARABLE = "src\\data\\testSeparable.csv";
	
	private static final int MAX_ITERATIONS = 100;
	private static final int AMOUNT_POINTS = 100;

	/**
	 * The Main function
	 * 
	 */
	public static void main(String[] args) {
		
		//If no test cases exist, generate them
		if(Files.notExists(Paths.get(PATH_TESTCASE_SEPARABLE))){
			generateTestcaseSeparable(AMOUNT_POINTS);
		}
		if(Files.notExists(Paths.get(PATH_TESTCASE_INSEPARABLE))){
			generateTestcaseInseparable(AMOUNT_POINTS);
		}
		
		
		//start timer
		double startTime= System.nanoTime();
		
		//Read test case
		ArrayList<Point> points = CsvParser.parseCSV(PATH_TESTCASE_INSEPARABLE);

		PerceptronData test = PerceptronLogic.trainPerceptronSequentially(points, MAX_ITERATIONS);
		
		Visualizer v = Visualizer.getInstance(points, test);
		v.setVisible(true);
//		v.showData(points, test);
		
		//end timer, get time elapsed and convert from ns to ms
		double endTime= System.nanoTime();
		double timeElapsed= (endTime-startTime)/1000000; 
		
		//print time elapsed
		System.out.println("\nThe whole Program ran for a total of " +timeElapsed+" ms");
	}
	

	
	/**
	 * Generate a set amount of lin.separable points for each type (testing purposes)
	 * 
	 */ 
	private static ArrayList<Point> generateRandomSeparablePoints(int count) {
		ArrayList<Point> points = new ArrayList<>();
		
		//fifty random points of class 1
        for(int i=0;i<count;++i){
            points.add(new Point(
            				PerceptronLogic.randomDoubleNumber(-1, 0),
            				PerceptronLogic.randomDoubleNumber(-1, 0),
            				1));
            }
        
        //fifty random points of class 0
        for(int i=0;i<count;++i){
            points.add(new Point(
            				PerceptronLogic.randomDoubleNumber(0.01, 1),
            				PerceptronLogic.randomDoubleNumber(0.01, 1),
            				0));
        	}
		return points;
	}

	
	/**
	 * Generate a set amount of lin. NOT separable points for each type (testing purposes)
	 * 
	 */ 
	private static ArrayList<Point> generateRandomInseparablePoints(int count) {
		ArrayList<Point> points = new ArrayList<>();
		
		//fifty random points of class 1
        for(int i=0;i<count;++i){
            points.add(new Point(
            				PerceptronLogic.randomDoubleNumber(-1, 0.5),
            				PerceptronLogic.randomDoubleNumber(-1, 0.5),
            				1));
            }
        
        //fifty random points of class 0
        for(int i=0;i<count;++i){
            points.add(new Point(
            				PerceptronLogic.randomDoubleNumber(-0.5, 1),
            				PerceptronLogic.randomDoubleNumber(-0.5, 1),
            				0));
        	}
		return points;
	}

	/**
	 * Generate a set amount of lin. NOT separable points for each type (testing purposes)
	 * And save them as CSV in the PATH_TESTCASE file defined above
	 * 
	 */ 
	private static void generateTestcaseInseparable(int count) {
		//Generate Test case 
		ArrayList<Point> points= generateRandomInseparablePoints(count);	
		CsvParser.writeCSV(points, PATH_TESTCASE_INSEPARABLE);
	}
	
	/**
	 * Generate a set amount of lin. separable points for each type (testing purposes)
	 * And save them as CSV in the PATH_TESTCASE file defined above
	 * 
	 */ 
	private static void generateTestcaseSeparable(int count) {
		//Generate Test case 
		ArrayList<Point> points= generateRandomSeparablePoints(count);	
		CsvParser.writeCSV(points, PATH_TESTCASE_SEPARABLE);
	}
	
}
