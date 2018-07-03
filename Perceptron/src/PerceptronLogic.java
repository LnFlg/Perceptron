import java.util.ArrayList;

/**
 * 
 */

/**
 * @author PC
 *
 */
public class PerceptronLogic {


	
	
	//===================================================================================================================
	// from here on, adjustments are to be done 
	// original code explained at:
	// https://www.youtube.com/watch?v=4aksMtJHWEQ 



	public static PerceptronData trainPerceptron(ArrayList<Point> points, int maxIterations) {
		PerceptronData bestSeparator = new PerceptronData(); 
		double LEARNING_RATE = 0.001;
		int theta = 0;

		//declarations
		double[] weights = new double[3]; // 2 for input variables and one for bias
		double localError, globalError;
		int iteration, output;
		ArrayList<Point> misclassifiedPoints = new ArrayList<Point>();

		//initialisations
		weights[0] = randomDoubleNumber(0, 1);
		weights[1] = randomDoubleNumber(0, 1);
		weights[2] = randomDoubleNumber(0, 1);

		
		iteration = 0;
		do {
			iteration++;
			globalError = 0;
			
			// loop through all instances (complete one epoch) to get an error measure for this weightset
			for (Point point : points) {
				// calculate predicted class
				output = calculateOutput(theta, weights, point.getX_value(), point.getY_Value());
				// difference between predicted and actual class values(-1, 0 or 1)
				localError = point.getType() - output;
				globalError += (localError * localError);
				
				//gather misclassified points to use them later for updated weights
				if(localError !=0) misclassifiedPoints.add(point);
			}
			
			/* Root Mean Squared Error */
			System.out.println("Iteration " + iteration + " : RMSE = " + Math.sqrt(globalError / points.size()));
			
			//TODO save if better than weightset in pocket
			if(globalError < bestSeparator.getError()) bestSeparator= new PerceptronData(weights, globalError );
			
			//Get a random misclassified point and its local error
			Point point = misclassifiedPoints.get(randomIntegerNumber(0, misclassifiedPoints.size()));
			localError = calculateOutput(theta, weights, point.getX_value(), point.getY_Value());
			
			// update weights
			weights[0] += LEARNING_RATE * localError * point.getX_value();
			weights[1] += LEARNING_RATE * localError * point.getY_Value();

			// update bias
			weights[2] += LEARNING_RATE * localError;
			
		} while (globalError != 0 && iteration < maxIterations);

		System.out.println("\n========\nDecision boundary equation:");
		System.out.println(weights[0] + "*x " + weights[1] + "*y + " + weights[2] + " = 0");
		

		return bestSeparator;		
	}

	
	
	/**
	 * returns a random double value within a given range
	 * 
	 */
	public static double randomDoubleNumber(double min, double max) {
		double d = min + Math.random() * (max - min);
		return d;
	}
	
	/**
	 * returns a random int value within a given range
	 * 
	 */
	public static int randomIntegerNumber(int min, int max) {
		int i = min + (int)(Math.random() * ((max - min) + 1));
		return i;
	}

	/**
	 * returns either 1 or 0 using a threshold function
	 * 
	 */
	static int calculateOutput(int theta, double weights[], double x, double y) {
		double sum = x * weights[0] + y * weights[1] + weights[2];
		return sum >= theta ? 1 : 0;
	}

}
