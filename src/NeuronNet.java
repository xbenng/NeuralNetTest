import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ben on 7/15/15.
 */
public class NeuronNet {
    private static ArrayList<ArrayList<Neuron>>  neuronLayers = new ArrayList<>();
    private static ArrayList<Neuron> inputNeurons = new ArrayList<>();
    private static ArrayList<Neuron> hiddenNeurons = new ArrayList<>();
    private static ArrayList<Neuron> outputNeurons = new ArrayList<>();
    private static ArrayList<float[]> trainingInput = new ArrayList<>();
    private static ArrayList<float[]> trainingOutput = new ArrayList<>();
    private static double sumError;

    public static double LEARN_RATE = .4;
    public static double ERROR_TOLERANCE = .01;

    private static Random rand = new Random(System.currentTimeMillis());

    public static void main(String args[])
    {

        for (int i = 0; i < 3; i++) inputNeurons.add(new Neuron(Neuron.INPUT));
        for (int i = 0; i < 3; i++) hiddenNeurons.add(new Neuron(Neuron.HIDDEN));
        for (int i = 0; i < 2; i++) outputNeurons.add(new Neuron(Neuron.OUTPUT));

        neuronLayers.add(inputNeurons);
        neuronLayers.add(hiddenNeurons);
        neuronLayers.add(outputNeurons);


        for (int i = 0; i < neuronLayers.size() - 1; i++)
        {
            connectLayers(neuronLayers.get(i), neuronLayers.get(i + 1));
        }

        initTrainingData();



        do {

            sumError = 0;

            //for each pattern
            for (int i = 0; i < trainingInput.size(); i++) {

                //set training data
                float[] in = trainingInput.get(i);
                float[] out = trainingOutput.get(i);
                for (int j = 0; j < in.length; j++)
                {
                    inputNeurons.get(j).setInput(in[j]);
                }
                for (int j = 0; j < out.length; j++)
                {
                    outputNeurons.get(j).setTarget(out[j]);
                }
                //get each output
                for (Neuron outputNeuron : outputNeurons) {
                    outputNeuron.getOutput();
                }

                //adjust each weight of every neuron in every layer
                for (int j = 0; j < neuronLayers.size(); j++) {
                    for (Neuron n : neuronLayers.get(j)) {
                        n.adjustWeight();
                    }
                }
                //add errors
                for (Neuron outputNeuron : outputNeurons) {
                    sumError = sumError + Math.pow(Math.abs(outputNeuron.getError()), 2);
//                    System.out.println(outputNeuron.toString() + " " + outputNeuron.output + " - " + outputNeuron.target + " = " + outputNeuron.getError());
                    //System.out.println("\t" + sumError);

                }
            }
            //System.out.println(sumError);

        } while (sumError > ERROR_TOLERANCE) ; //until error acceptable


        inputNeurons.get(0).setInput(0);
        inputNeurons.get(1).setInput(0);
        inputNeurons.get(2).setInput(1);

        for (Neuron outputNeuron : outputNeurons) {
            System.out.println("Result: " + outputNeuron.getOutput());
        }


        return;
    }

    public static void initTrainingData()
    {
        trainingInput.add(new float[]{(float) 1, (float) 1, (float) 1});
        trainingOutput.add(new float[] {0, 1});
        trainingInput.add(new float[]{(float) .01, (float) .01, (float) .01});
        trainingOutput.add(new float[] {1, 0});
    }


    public static void connectLayers(ArrayList<Neuron> layer1, ArrayList<Neuron> layer2)
    {
        for (Neuron neuronL1 : layer1)
        {
            for (Neuron neuronL2 : layer2)
            {
                neuronL2.addInput(neuronL1, rand(-1, 1));  //connect neurons with a random weight from -1 to 1
            }
        }
    }

    private static float rand(int a, int b)
    {
        return rand.nextFloat() * (b - a) - b;
    }
}
