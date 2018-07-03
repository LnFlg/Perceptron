import java.util.ArrayList;


/**
 * @author PC
 *Dataclass for a perceptron,	
 *if used in cases with 3 or more dimensions, this will not work as intended (due to accessing arrays with fixed indices)
 *
 */
public class PerceptronData {

	//members
	private ArrayList<Double> weights;
	private Double bias, error;
	
	/**
	 * Constructor
	 */
	public PerceptronData(double[] weights2, Double error) {
		
		this.error=error;
		this.weights = new ArrayList<>();
		this.weights.add(weights2[0]);
		this.weights.add(weights2[1]);
		bias=weights2[2];
	}

	/**
	 * Empty Constructor
	 */
	public PerceptronData() {
		super();
		bias    = null;
		error   = Double.MAX_VALUE;
		weights = new ArrayList<>();
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
