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
	public static double timer = 0;
	private static final String PATH_TESTCASE_INSEPARABLE_Case1 = "src\\data\\testInseparable.csv";
	private static final String PATH_TESTCASE_SEPARABLE_Case1 = "src\\data\\testSeparable.csv";
	private static final String PATH_TESTCASE_INSEPARABLE_Case2 = "src\\data\\testInseparable2.csv";
	private static final String PATH_TESTCASE_SEPARABLE_Case2 = "src\\data\\testSeparable2.csv";
	
	private static final int MAX_ITERATIONS_Case1 = 100;
	private static final int AMOUNT_POINTS_Case1 = 100;
	private static final int MAX_ITERATIONS_Case2 = 10000;
	private static final int AMOUNT_POINTS_Case2 = 100;
	private static final int MAX_ITERATIONS_Case3 = 10000;

	/**
	 * The Main function
	 * 
	 */
	public static void main(String[] args) {
		
		// If no test cases exist, generate them
		if (Files.notExists(Paths.get(PATH_TESTCASE_SEPARABLE_Case1))) {
			generateTestcaseSeparable(AMOUNT_POINTS_Case1, PATH_TESTCASE_SEPARABLE_Case1);
		}
		if (Files.notExists(Paths.get(PATH_TESTCASE_INSEPARABLE_Case1))) {
			generateTestcaseInseparable(AMOUNT_POINTS_Case1, PATH_TESTCASE_INSEPARABLE_Case1);
		}
		if (Files.notExists(Paths.get(PATH_TESTCASE_SEPARABLE_Case2))) {
			generateTestcaseSeparable(AMOUNT_POINTS_Case2, PATH_TESTCASE_SEPARABLE_Case2);
		}
		if (Files.notExists(Paths.get(PATH_TESTCASE_INSEPARABLE_Case2))) {
			generateTestcaseInseparable(AMOUNT_POINTS_Case2, PATH_TESTCASE_INSEPARABLE_Case2);
		}
//		System.out.println("seperable");
//		//Seperable
//		runTestCaseSequentially(PATH_TESTCASE_SEPARABLE_Case1, MAX_ITERATIONS_Case1); //100x100
//		runTestCaseSequentially(PATH_TESTCASE_SEPARABLE_Case2, MAX_ITERATIONS_Case2);// 2000x400
//		runTestCaseParallel(PATH_TESTCASE_SEPARABLE_Case1, MAX_ITERATIONS_Case1); //100x100
//		runTestCaseParallel(PATH_TESTCASE_SEPARABLE_Case2, MAX_ITERATIONS_Case2);// 2000x400

		System.out.println("\ninseperable-------------------------");
		//Inseperable
		runTestCaseSequentially(PATH_TESTCASE_INSEPARABLE_Case1, MAX_ITERATIONS_Case1); //100x100
		runTestCaseSequentially(PATH_TESTCASE_INSEPARABLE_Case1, MAX_ITERATIONS_Case2);// 1000x100
		runTestCaseParallel(PATH_TESTCASE_INSEPARABLE_Case1, MAX_ITERATIONS_Case1); //100x100
		runTestCaseParallel(PATH_TESTCASE_INSEPARABLE_Case1, MAX_ITERATIONS_Case2);// 1000x100
		
		runTestCaseSequentially(PATH_TESTCASE_INSEPARABLE_Case1, MAX_ITERATIONS_Case3); //10000x100
		runTestCaseParallel(PATH_TESTCASE_INSEPARABLE_Case1, MAX_ITERATIONS_Case3); //10000x100
	}
	
	private static void runTestCaseParallel(String pathTestcase, int maxIterations) {
		// start timer
		double startTime = System.nanoTime();

		// Read test case
		ArrayList<Point> points = CsvParser.parseCSV(pathTestcase);
		// change the calls here
		int calls =100;
		//reset timer
		timer = 0;
		for(int i = calls; i > 0; i--) {
			PerceptronData test = PerceptronLogic.trainPerceptronParallel(points, maxIterations);
//			if(i == 1) {
//				 Visualizer v = Visualizer.getInstance(points, test);
//				 v.setVisible(true);
//			}
		}
		
		// end timer, get time elapsed and convert from ns to ms
		double endTime = System.nanoTime();
		double timeElapsed = (endTime - startTime) / 1000000;
		timeElapsed /= calls;
		timer /=calls;
		
		// print algorithm time
		System.out.println("\nPerceptron parallel in \t\t"+ timer + "ms (average)");

		// print time elapsed
		System.out.println("Program parallel in \t\t" + timeElapsed + " ms (average)");

	}
	
	private static void runTestCaseSequentially(String pathTestcase, int maxIterations) {
		// start timer
		double startTime = System.nanoTime();

		// Read test case
		ArrayList<Point> points = CsvParser.parseCSV(pathTestcase);
		// change the calls here
		int calls =100;
		//reset timer
		timer = 0;
		for(int i = calls; i > 0; i--) {
			PerceptronData test = PerceptronLogic.trainPerceptronSequentially(points, maxIterations);
//			if(i == 1) {
//				 Visualizer v = Visualizer.getInstance(points, test);
//				 v.setVisible(true);
//			}
		}
		
		// end timer, get time elapsed and convert from ns to ms
		double endTime = System.nanoTime();
		double timeElapsed = (endTime - startTime) / 1000000;
		timeElapsed /= calls;
		timer /=calls;
		
		// print algorithm time
		System.out.println("\nPerceptron sequentially in \t"+ timer + "ms (average)");

		// print time elapsed
		System.out.println("Program sequentially in \t" + timeElapsed + " ms (average)");
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
	private static void generateTestcaseInseparable(int count, String path) {
		//Generate Test case 
		ArrayList<Point> points= generateRandomInseparablePoints(count);	
		CsvParser.writeCSV(points, path);
	}
	
	/**
	 * Generate a set amount of lin. separable points for each type (testing purposes)
	 * And save them as CSV in the PATH_TESTCASE file defined above
	 * 
	 */ 
	private static void generateTestcaseSeparable(int count, String path) {
		//Generate Test case 
		ArrayList<Point> points= generateRandomSeparablePoints(count);	
		CsvParser.writeCSV(points, path);
	}
	public double getTimer() {
		return timer;
	}
	public void setTimer(double newtimer) {
		timer = newtimer;
	}
}
