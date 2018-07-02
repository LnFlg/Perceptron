import java.util.InputMismatchException;

/*
 * Parallel Computing SS 18
 * Perceptron Learning Algorithmen
 * 
 * 
 *  
 */

public class Point {
	private double x_value;
	private double y_value;
	private int type;
		
	
	public Point(double x_value, double y_value, int type) {
		
		if(type != 1 && type != 0) throw new InputMismatchException("Type can only be 0 or 1 (integer)");
		
		this.x_value = x_value;
		this.y_value = y_value;
		this.type = type;
	}

	public double getX_value() {
		return x_value;
	}
	
	public double getY_Value() {
		return y_value;
	}
	
	public int getType() {
		return type;
	}
	
	
	
}
