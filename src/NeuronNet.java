import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ben on 7/15/15.
 */
public class NeuronNet {
    private static ArrayList<ArrayList<Neuron>>  neuronLayers = new ArrayList<>();
    private static ArrayList<Neuron> inputNeurons;
    private static ArrayList<Neuron> outputNeurons;
    private static ArrayList<float[]> trainingInput = new ArrayList<>();
    private static ArrayList<float[]> trainingOutput = new ArrayList<>();
    private static double sumError;

    public static double LEARN_RATE = .9;
    public static double ERROR_TOLERANCE = .01;

    private static Random rand = new Random(System.currentTimeMillis());

    public static void main(String args[])
    {

        initLayout(args[0]);

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


        inputNeurons.get(0).setInput(1);
        inputNeurons.get(1).setInput(1);
        inputNeurons.get(2).setInput(1);

        for (Neuron outputNeuron : outputNeurons) {
            System.out.println("Result: " + outputNeuron.getOutput());
        }


        return;
    }

    public static void initLayout(String layoutFileAddress)
    {

        BufferedReader layoutReader;
        String line;
        ArrayList<Integer> layerSizes = new ArrayList<>();

        try {
            layoutReader = new BufferedReader(new FileReader(new File(layoutFileAddress)));
            while ((line = layoutReader.readLine()) != null) {
                layerSizes.add(Integer.parseInt(line));  //read whole layout file
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //for each line
        for (int i = 0; i < layerSizes.size(); i++)
        {
            short layerType;
            if (i == 0)
            {
                layerType = Neuron.INPUT;
            }
            else if (i == layerSizes.size() - 1)
            {
                layerType = Neuron.OUTPUT;
            }
            else
            {
                layerType = Neuron.HIDDEN;
            }
            //init layer
            ArrayList<Neuron> newLayer = new ArrayList<>();
            for (int j = 0; j < layerSizes.get(i); j++) newLayer.add(new Neuron(layerType));
            if (i != layerSizes.size() - 1) newLayer.add(new Neuron(Neuron.BIAS));  //add bias neuron to layer
            //add layer
            neuronLayers.add(newLayer);

        }
        //connect layers
        for (int i = 0; i < neuronLayers.size() - 1; i++)
        {
            connectLayers(neuronLayers.get(i), neuronLayers.get(i + 1));
        }

        inputNeurons = neuronLayers.get(0);
        outputNeurons = neuronLayers.get(neuronLayers.size() - 1);


    }

    public static void initTrainingData()
    {
        trainingInput.add(new float[]{(float) 1, (float) 1, (float) 1});
        trainingOutput.add(new float[] {0, 1});
        trainingInput.add(new float[]{(float) 0, (float) 0, (float) 0});
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
