import java.util.*;

public class Main {
    public static void main(String[] args) {
        // parse arguments from console
        double learningRate = Double.parseDouble(args[0]);
        String trainingFilePath = args[1];
        String testFilePath = args[2];

        Perceptron perceptron = new Perceptron(learningRate,
                ParseFile.parse(trainingFilePath),
                ParseFile.parse(testFilePath)
        );

        perceptron.learn();
        perceptron.run();

        System.out.println("---");

        setDataFromConsole(trainingFilePath);
    }

    // Parse data from the console and check if guess was right
    public static void setDataFromConsole(String trainingFilePath) {
        final Scanner scanner = new Scanner(System.in);
        double learningRate;

        while (true) {
            // Parse all needed arguments for KNN
            System.out.println("Enter iris attributes 'atr1,atr2,atr3,a4tr,<IRIS_TYPE>,learningRate' or type 'exit': ");
            String line = scanner.nextLine();

            // end the program
            if (line.equals("exit")) {
                break;
            }
            String irisName = "";
            String[] attrsString = line.split(",");
            // save parsed iris parameters
            double[] irisParameters = new double[attrsString.length-2];

            try {
                for (int i = 0; i < irisParameters.length; i++) {
                    irisParameters[i] = Double.parseDouble(attrsString[i].trim());
                }
                irisName = attrsString[attrsString.length-2].trim();
                learningRate = Double.parseDouble(attrsString[attrsString.length-1].trim());
            } catch (Exception exception) {
                System.out.println("Entered data is wrong, try again!");
                System.out.println("---------------");
                continue;
            }

            ArrayList<Iris> irises = new ArrayList<>();
            irises.add(new Iris(irisName, irisParameters));

            // create instance of KNN with parsed irises data
            Perceptron perceptron = new Perceptron(learningRate,
                    ParseFile.parse(trainingFilePath),
                    irises
            );
            perceptron.learn();
            perceptron.run();

            System.out.println("---------------");
        }
    }
}