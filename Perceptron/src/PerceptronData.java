import java.util.ArrayList;

/**
 * 
 */

/**
 * @author PC
 *
 */
public class PerceptronData {

	//members
	private ArrayList<Double> weights;
	private Double bias, error;
	
	/**
	 * Constructor
	 */
	public PerceptronData(ArrayList<Double> weights, Double bias, Double error) {
		this.bias=bias;
		this.error=error;
		this.weights=weights;
	}

	
	//Getters
	public ArrayList<Double> getWeights() {
		return weights;
	}

	public Double getBias() {
		return bias;
	}
	
	public Double getError() {
		return error;
	}

}
