
/*
 * Read and Parse CSV File in Java

 */
// ich muss eine Array list erstellen mit in der For Schleife ein  Point contructor aufrufen  von point und das was in Array 

import java.util.*;
import java.nio.charset.Charset;
import java.nio.file.*;

public class CsvParser {

	/**
	 * Read and Parse CSV File to an ArrayList of Point objects
	 * 
	 */
	public static ArrayList<Point> parseCSV(String pathString) throws NumberFormatException {

		try {

			List<String> lines = Files.readAllLines(Paths.get(pathString));
			ArrayList<Point> pointList = new ArrayList<Point>();
			for (String line : lines) {

				String[] result = line.split("\n");
				for (String s : result) {
					String[] pointAttributes = s.split(",");
					Point point = new Point(Double.parseDouble(pointAttributes[0]),
							Double.parseDouble(pointAttributes[1]), Integer.parseInt(pointAttributes[2]));
					pointList.add(point);
					System.out.print(s + " - ");
					System.out.println();
				}

			}
			return pointList;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Write an ArrayList of Points to a file
	 * 
	 */
	public static void writeCSV(ArrayList<Point> points, String pathString) {
		//TODO testing
		Path savePath = Paths.get(pathString); 
		ArrayList<String> lines = new ArrayList<>();
		String line;
		
		for (Point point: points) {
			line = String.valueOf(point.getX_value()) + "," + 
					String.valueOf(point.getY_Value()) + ","+
					String.valueOf(point.getType()) ;
			lines.add(line);
		}
		
		try {
			//write to file
			Files.write(savePath, lines, Charset.forName("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
