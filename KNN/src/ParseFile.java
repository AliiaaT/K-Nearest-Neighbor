import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ParseFile {

    // Parse file with raw data and return ArrayList of Iris type
    public static ArrayList<Iris> parse(String path) {
        // init array that will be filled by data from file
        ArrayList<Iris> irises = new ArrayList<>();
        // try open reader file
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            // if has next line
            while (null != (line = br.readLine())) {
                // array with value divided by ',' in the parsed line
                String[] strings = line.split(",");
                // init double array, which will be filled by iris parameters
                double[] irisParameters = new double[strings.length - 1];
                // convert strings to double
                for (int i = 0; i < irisParameters.length; i++) {
                    irisParameters[i] = Double.parseDouble(strings[i].trim());
                }
                String irisName = strings[strings.length - 1];

                irises.add(new Iris(irisName, irisParameters));
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return irises;
    }
}