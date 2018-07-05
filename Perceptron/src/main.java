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
		double weights[] = {1,1,1};
		PerceptronData test = new PerceptronData(weights, Double.valueOf(0));
		ArrayList<Point> points= generateRandomSeparablePoints(10);
		Visualizer v = Visualizer.getInstance();
		v.showData(points, test);
		v.setVisible(true);
		
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
