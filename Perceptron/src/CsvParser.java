
/*
 * Read and Parse CSV File in Java

 */
// ich muss eine Array list erstellen mit in der For Schleife ein  Point contructor aufrufen  von point und das was in Array 


import java.util.*;
import java.nio.file.*;

public class CsvParser{

	public ArrayList<Point> parsecsv() throws NumberFormatException{
		
		try {
			
			List<String> lines =Files.readAllLines(Paths.get("src\\DArrayList<E>in.csv"));
			ArrayList<Point> pointList = new ArrayList<Point>();
			for(String line : lines) {
				
				String[] result = line.split("\n");
				for (String s: result) {
					String[] pointarrayList = s.split(",");
					Point point = new Point(Double.parseDouble(pointarrayList[0]), Double.parseDouble(pointarrayList[1]), Integer.parseInt(pointarrayList[2]));
					pointList.add(point);
					System.out.print(s + " - ");
					System.out.println();
				}	
				
			}
			return pointList;
		}
	catch (Exception e) {
		System.out.println(e.getMessage());
	}
		return null;
  }
}

