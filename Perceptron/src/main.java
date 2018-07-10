import java.util.ArrayList;

/**
 * 
 */

/**
 * @author PC
 *
 */
public class main {

	/**
	 * 
	 */
	public static void main(String[] args) {
		//start timer
		long startTime= System.nanoTime();
		
		ArrayList<Point> points= generateRandomSeparablePoints(50);
		PerceptronData test = PerceptronLogic.trainPerceptronSequentially(points, 50);
		
		Visualizer v = Visualizer.getInstance(points, test);
		v.setVisible(true);
//		v.showData(points, test);
		
		//end timer, get time elapsed and convert from ns to ms
		long endTime= System.nanoTime();
		long timeElapsed= (endTime-startTime)/1000000; 
		
		//print time elapsed
		System.out.println("\nThe whole Program ran for a total of " +timeElapsed+" ms");
	}
	

	
	/**
	 * Generate a set amount of lin.separable points for each type (testing purposes)
	 * 
	 */ 
	public static ArrayList<Point> generateRandomSeparablePoints(int count) {
		ArrayList<Point> points = new ArrayList<>();
		
		//fifty random points of class 1
        for(int i=0;i<=count;++i){
            points.add(new Point(
            				PerceptronLogic.randomDoubleNumber(-1, 0),
            				PerceptronLogic.randomDoubleNumber(-1, 0),
            				1));
            }
        
        //fifty random points of class 0
        for(int i=0;i<=count;++i){
            points.add(new Point(
            				PerceptronLogic.randomDoubleNumber(0.01, 1),
            				PerceptronLogic.randomDoubleNumber(0.01, 1),
            				0));
        	}
		return points;
	}

}
