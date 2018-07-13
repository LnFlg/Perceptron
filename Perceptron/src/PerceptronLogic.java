import java.util.ArrayList;



/**
 * @author PC
 *
 */
public class PerceptronLogic {

	/**
	 * 	 * This is the SEQUENTIAL approach*
	 * 
	 * Method to Train a Perceptron.
	 * Prints out iterations done and error when stop condition is met
	 * return an object of PerceptronData-type 
	 * 
	 */
	public static PerceptronData trainPerceptronSequentially(ArrayList<Point> points, int maxIterations) {
		PerceptronData bestSeparator = new PerceptronData(); 
		double LEARNING_RATE =  0.1;
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

		//start timer
		double startTime = System.nanoTime();
		
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
			
			//  save if better than weightset in pocket
			if(globalError < bestSeparator.getError()) bestSeparator= new PerceptronData(weights, globalError );
			

			//if points were misclassified, update weightset
			if (misclassifiedPoints.size() != 0) {
				//Get a random misclassified point and its local error
				Point point = misclassifiedPoints.get(randomIntegerNumber(0, misclassifiedPoints.size() - 1));
				localError = point.getType() - calculateOutput(theta, weights, point.getX_value(), point.getY_Value());
				// update weights
				weights[0] += LEARNING_RATE * localError * point.getX_value();
				weights[1] += LEARNING_RATE * localError * point.getY_Value();
				// update bias
				weights[2] += LEARNING_RATE * localError;
			}
			
			misclassifiedPoints.clear();
			
		} while (globalError != 0 && iteration < maxIterations);
		
		//stop timer and calc time elapsed incl. conversion from ns to ms
		double endTime= System.nanoTime();
		double timeElapsed = (endTime-startTime)/1000000;
		
		/* Print data */
		System.out.println("Perceptron training finished after " + iteration+" Iterations" + 
				" \n  Best Separator has an error of "+bestSeparator.getError()+".\n" +
				"Perceptron was trained sequentially in "+ timeElapsed + "ms");
		
		return bestSeparator;		
	}

	/**
	 * 	 * This is the PARALLEL approach*
	 * 
	 * Method to Train a Perceptron.
	 * Prints out iterations done and error when stop condition is met
	 * return an object of PerceptronData-type 
	 * 
	 */

	public static PerceptronData trainPerceptronParallel(ArrayList<Point> points, int maxIterations) {
		PerceptronData bestSeparator = new PerceptronData(); 
		double LEARNING_RATE =  0.1;
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

		//start timer
		double startTime = System.nanoTime();
		
		
		
		//TODO vvvvvvvvvvvvvvvvvvvvvvvvvvvvvv  adjust to work in parallel  vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
		
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
			
			//  save if better than weightset in pocket
			if(globalError < bestSeparator.getError()) bestSeparator= new PerceptronData(weights, globalError );
			

			//if points were misclassified, update weightset
			if (misclassifiedPoints.size() != 0) {
				//Get a random misclassified point and its local error
				Point point = misclassifiedPoints.get(randomIntegerNumber(0, misclassifiedPoints.size() - 1));
				localError = point.getType() - calculateOutput(theta, weights, point.getX_value(), point.getY_Value());
				// update weights
				weights[0] += LEARNING_RATE * localError * point.getX_value();
				weights[1] += LEARNING_RATE * localError * point.getY_Value();
				// update bias
				weights[2] += LEARNING_RATE * localError;
			}
			
			misclassifiedPoints.clear();
			
		} while (globalError != 0 && iteration < maxIterations);
		
		//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ toto end ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		
		
		
		//stop timer and calc time elapsed incl. conversion from ns to ms
		double endTime= System.nanoTime();
		double timeElapsed = (endTime-startTime)/1000000;
		
		/* Print data */
		System.out.println("Perceptron training finished after " + iteration+" Iterations" + 
				" \nBest Separator has an error of "+bestSeparator.getError()+".\n" +
				"Perceptron was trained parallel in "+ timeElapsed + "ms");
		
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
