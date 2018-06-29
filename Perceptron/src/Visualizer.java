


import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Visualizer  extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Visualizer visualizerInstance;

	private static final int SIZE = 600;

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
	
	public void drawPoints(Graphics g, double x, double x2, Color color) {
		g.setColor(color);
		g.drawOval(berechneXPixel(x), berechneYPixel(f(x)), 10, 10);
		g.fillOval(berechneXPixel(x), berechneYPixel(f(x)), 10, 10);
	}

	public void paint(Graphics g) {
		zeicheKoordinatenSystem(g);
		for (double x = xMin; x < xMax; x += 0.5) {
			if(x < 0)
				drawPoints(g, x, x, Color.BLUE);
			else
				drawPoints(g, x, x, Color.RED);

		}
		//zeichnet Gerade
		drawMyLine(-10, 10, 10, -10, g);
	}
	private void drawMyLine(double x1, double y1, double x2, double y2, Graphics g) {
		g.setColor(Color.GREEN);
		g.drawLine(berechneXPixel(x1), berechneYPixel(y1), berechneXPixel(x2), berechneYPixel(y2));
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

	public static void main(String[] args) {
		new Visualizer().setVisible(true);
	}
}
