import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;



/**
 * @author PC
 *
 */
public class PerceptronLogic {

	private volatile static PerceptronData bestSeparator = new PerceptronData();
	
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
		PerceptronLogic.bestSeparator= new PerceptronData();
		double LEARNING_RATE =  0.1;
		int theta = 0;

		//declarations
		double[] weights = new double[3]; // 2 for input variables and one for bias
		int iteration;
		Double globalError = null;
		Future<Double> result;
		//initialisations
		weights[0] = randomDoubleNumber(0, 1);
		weights[1] = randomDoubleNumber(0, 1);
		weights[2] = randomDoubleNumber(0, 1);

		
		ReadWriteLock lock = new ReentrantReadWriteLock();
		
		//Setup Threads
		ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		System.out.println("Running on "+ Runtime.getRuntime().availableProcessors()+ " cores");
		//start timer
		double startTime = System.nanoTime();
		
		//TODO vvvvvvvvvvvvvvvvvvvvvvvvvvvvvv  adjust to work in parallel  vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
		//bestSeparator, iteration shared
		
		iteration = 0;
		
		do {
			iteration++;
				
			result =exec.submit(new Callable<Double>(){
				private double localError, globalError;
				private int output;
				private ArrayList<Point> misclassifiedPoints = new ArrayList<Point>();
				
				//@Override
				public Double call() {
					globalError = 0;
					// loop through all instances (complete one epoch) to get an error measure for this weightset
					for (Point point : points) {
						
						lock.readLock().lock();
						try {
							// calculate predicted class
							output = calculateOutput(theta, weights, point.getX_value(), point.getY_Value());
						} finally {
							lock.readLock().unlock();
						}
						
						// difference between predicted and actual class values(-1, 0 or 1)
						localError = point.getType() - output;
						globalError += (localError * localError);
						
						//gather misclassified points to use them later for updated weights
						if(localError !=0) misclassifiedPoints.add(point);
					}
					
					lock.writeLock().lock();
					try {
						//  save if better than weightset in pocket
						if (globalError < PerceptronLogic.bestSeparator.getError()) {
							PerceptronLogic.bestSeparator = new PerceptronData(weights, globalError);
							}
					} finally {
						lock.writeLock().unlock();
					}
					
					//if points were misclassified, update weightset
					if (misclassifiedPoints.size() != 0) {
						//Get a random misclassified point and its local error
						Point point = misclassifiedPoints.get(randomIntegerNumber(0, misclassifiedPoints.size() - 1));
						
						lock.readLock().lock();
						try {
							localError = point.getType()
									- calculateOutput(theta, weights, point.getX_value(), point.getY_Value());
						} finally {
							lock.readLock().unlock();
						}
						
						lock.writeLock().lock();
						try {
							// update weights
							weights[0] += LEARNING_RATE * localError * point.getX_value();
							weights[1] += LEARNING_RATE * localError * point.getY_Value();
							// update bias
							weights[2] += LEARNING_RATE * localError;
						} finally {
							lock.writeLock().unlock();
						}
					}
					
					misclassifiedPoints.clear();
					System.out.println(globalError);
					return Double.valueOf(globalError);

				}
			});
			

		} while (iteration < maxIterations);
		
		//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ toto end ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		
		
		
		//stop timer and calc time elapsed incl. conversion from ns to ms
		double endTime= System.nanoTime();
		double timeElapsed = (endTime-startTime)/1000000;
		
		try {
			globalError= result.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("Global Error "+ globalError);
		
		exec.shutdown();
		try {
			exec.awaitTermination(2, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		/* Print data */
		System.out.println("Perceptron training finished after " + iteration+" Iterations" + 
				" \nBest Separator has an error of "+bestSeparator.getError()+".\n" +
				"Perceptron was trained parallel in "+ timeElapsed + "ms");
		
		
		
		return PerceptronLogic.bestSeparator;		
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
