import java.util.*;

public class Perceptron {
    private ArrayList<Iris> testArray, trainingArray;
    private static HashMap<String, Integer> irisTypeMapper = new HashMap<>();
    private double[] weights;
    private double threshold, learningRate;
    private int parametersAmount;

    public Perceptron(double learningRate, ArrayList<Iris> trainingArray, ArrayList<Iris> testArray) {
        this.trainingArray = trainingArray;
        this.testArray = testArray;
        this.learningRate = learningRate;
        this.parametersAmount = testArray.get(0).getValues().length;

        // find iris types
        for (Iris trainingIris : trainingArray) {
            irisTypeMapper.put(trainingIris.getName(), 0);
        }

        int id = 0;
        // assign numeric values to iris types
        // {Iris-versicolor=0, Iris-virginica=1}
        for (Map.Entry<String, Integer> entry : irisTypeMapper.entrySet()) {
            entry.setValue(id++);
        }
        //System.out.println(irisTypeMapper);
        randomizeWeightsAndLearningError();
    }

    private void randomizeWeightsAndLearningError() {
        threshold = (Math.random()*2)-1;
        //System.out.println("Learning error: " + threshold);

        //adjusting weights to dataset
        weights = new double[parametersAmount];
        for (int i = 0; i < weights.length; i++)
            weights[i] = (Math.random()*2)-1;
        //System.out.println("Weights: " + Arrays.toString(weights));
    }


    private int evaluate(Iris iris) {

        //System.out.println("Start evaluate: " + iris);
        //summa
        double net = 0;
        for (int i = 0; i < iris.getValues().length; i++)
            net += iris.getValues()[i] * weights[i];

        //System.out.println("net: " + net);
        //int result = net >= threshold? 1:0;
        /*System.out.println("Result: " + result);
        
        return result;*/
        return net >= threshold? 1:0;
    }


    public void learn() {

        int iterations = 1000;
        for (int i = 0; i < iterations; i++) {

            for (Iris iris : trainingArray) {
                int calculatedType = evaluate(iris);
                int actualType = irisTypeMapper.get(iris.getName());


                int error = actualType - calculatedType;

                if (error != 0) {
                    // System.out.println("Error, start delta rule");
                    // delta rule
                    for (int j = 0; j < parametersAmount; j++) {
                        weights[j] += learningRate * error * iris.getValues()[j];
                    }
                }

//                System.out.println("Learning error during lear: " + threshold);
//                System.out.println("Weights during lear: " + Arrays.toString(weights));
            }
        }
    }


    public void run() {
        int actualType;
        double total = 0, found = 0;
        for (Iris iris : testArray) {
            actualType = evaluate(iris);

            String actualName = null;
            for (Map.Entry<String, Integer> entry : irisTypeMapper.entrySet())
                if (actualType == entry.getValue())
                    actualName = entry.getKey();

            if (iris.getName().equals(actualName)) {
                found++;
            }
            total++;

            System.out.println((iris.getName()) + Arrays.toString(iris.getValues()) + " --- Correct Name: " + actualName);
        }
        System.out.println("Found: " + found);
        System.out.println("Total: " + total);
        System.out.println("Accuracy: " + found/total);
    }

}