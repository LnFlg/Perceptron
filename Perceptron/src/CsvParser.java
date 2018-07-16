
/*
 * Read and Parse CSV File in Java

 */
// ich muss eine Array list erstellen mit in der For Schleife ein  Point contructor aufrufen  von point und das was in Array 

import java.util.*;
<<<<<<< HEAD
<<<<<<< HEAD
import java.nio.*;
=======
>>>>>>> af271feb77672ea9ad026d90c3692096cc8a21d2
=======

import java.nio.*;

=======
import java.nio.charset.Charset;
>>>>>>> f0682f9bc0738a9ff9f47491104ab4f5b6448687
>>>>>>> parent of b974b30... Revert "Merge branch 'master' of https://github.com/LnFlg/Perceptron"
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
<<<<<<< HEAD
				for (String s: result) {
					String[] pointarrayList = s.split(",");
<<<<<<< HEAD
					Point point = new Point(Double.parseDouble(pointarrayList[0]),Double.parseDouble(pointarrayList[1]), Integer.parseInt(pointarrayList[2]));
=======
					Point point = new Point(Double.parseDouble(pointarrayList[0]), Double.parseDouble(pointarrayList[1]), Integer.parseInt(pointarrayList[2]));
<<<<<<< HEAD
>>>>>>> af271feb77672ea9ad026d90c3692096cc8a21d2
=======
=======
				for (String s : result) {
					String[] pointAttributes = s.split(",");
					Point point = new Point(Double.parseDouble(pointAttributes[0]),
							Double.parseDouble(pointAttributes[1]), Integer.parseInt(pointAttributes[2]));
>>>>>>> f0682f9bc0738a9ff9f47491104ab4f5b6448687
>>>>>>> parent of b974b30... Revert "Merge branch 'master' of https://github.com/LnFlg/Perceptron"
					pointList.add(point);
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
