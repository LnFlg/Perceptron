import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Visualizer  extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Visualizer visualizerInstance;
	private static final int SIZE = 600;
	private Graphics graphic;
	private static final double xMin = -10;
	private static final double xMax =10;
	private static final double yMin = -10;
	private static final double yMax = 10;

	private Visualizer() {

		this.setSize(SIZE, SIZE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}
	
	public static Visualizer getInstance() {
		if(visualizerInstance == null) {
			visualizerInstance = new Visualizer();
		}
		return visualizerInstance;
	}
	
	private void drawPoints(Graphics g, double x, double x2, Color color) {
		g.setColor(color);
		g.drawOval(berechneXPixel(x), berechneYPixel(f(x)), 10, 10);
		g.fillOval(berechneXPixel(x), berechneYPixel(f(x)), 10, 10);
	}

	public void paint(Graphics g) {
		zeicheKoordinatenSystem(g);
		graphic = g;
	}

	public void showData(ArrayList<Point> pointList, PerceptronData perceptronData) {
		for(Point point : pointList) {
			if(point.getType() == 1) {
				drawPoints(graphic,point.getX_value(),point.getY_Value(),Color.RED);
			}else if(point.getType() == 0) {
				drawPoints(graphic,point.getX_value(),point.getY_Value(),Color.BLUE);
			}
		}
		drawMyLine(perceptronData);
	}
	//w0 ist der bias/schwellwert 
	//w1 und w2 aus dem Gewichtsvektor
	private void drawMyLine(PerceptronData perceptronData) {
		graphic.setColor(Color.GREEN);
		double w0 = perceptronData.getBias();
		double w1 = perceptronData.getWeights().get(0);
		double w2 = perceptronData.getWeights().get(1);
		double x1= xMin;
		double y1= (-(w0/w2)/(w0/w1))*x1 - (w0/w2);
		double x2= xMax;
		double y2=(-(w0/w2)/(w0/w1))*x2 - (w0/w2);
		graphic.drawLine(berechneXPixel(x1), berechneYPixel(y1), berechneXPixel(x2), berechneYPixel(y2));
	}

	private void zeicheKoordinatenSystem(Graphics g) {
		zeichneXAchse(g);
		zeichneYAchse(g);
	}

	private void zeichneXAchse(Graphics g) {

		int startx = berechneXPixel(xMin);
		int endx = berechneXPixel(xMax);
		int starty = berechneYPixel(0);
		int endy = berechneYPixel(0);
		g.drawLine(startx, starty, endx, endy);

	}

	private void zeichneYAchse(Graphics g) {
		int startx = berechneXPixel(0);
		int endx = berechneXPixel(0);
		int starty = berechneYPixel(yMin);
		int endy = berechneYPixel(yMax);
		g.drawLine(startx, starty, endx, endy);

	}

	private int berechneXPixel(double x) {

		double pixelBreite = ((xMax - xMin) / (double) getWidth());
		return (int) ((x - xMin) / pixelBreite);
	}

	private int berechneYPixel(double y) {

		double pixelBreite = ((yMax - yMin) / (double) getHeight());
		return getHeight() - ((int) ((y - yMin) / pixelBreite));
	}

	private double f(double x) {
		return x * x;
	}

//	public static void main(String[] args) {
//		new Visualizer().setVisible(true);
//		
//	}
}
