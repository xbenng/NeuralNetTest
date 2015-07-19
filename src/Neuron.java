import java.util.ArrayList;

/**
 * Created by ben on 7/15/15.
 */
public class Neuron {

    public static final short INPUT = 1;
    public static final short OUTPUT = 2;
    public static final short HIDDEN = 3;
    public static final short BIAS = 4;


    private float input = 0;
    private float inputInput = 0;
    public float output;
    private float delta;
    private boolean deltaComputed = false;
    public float target;
    private ArrayList<Neuron> inputNeurons = new ArrayList<>();
    private ArrayList<Float> inputWeights = new ArrayList<>();
    private ArrayList<Neuron> outputNeurons = new ArrayList<>();
    private short neuronType;


    Neuron (short neuronType)
    {
        this.neuronType = neuronType;
    }

    public void addInput(Neuron inputNeuron, float weight)
    {
        inputNeurons.add(inputNeuron);  //add input neuron
        inputWeights.add(weight);
        inputNeuron.addOutput(this);  //add this neuron as an output of the input neuron
    }

    public void addOutput(Neuron outputNeuron)
    {
        outputNeurons.add(outputNeuron);
    }

    public void setInput(float input) // only use for input neurons
    {
        this.inputInput = input;
    }

    public void setTarget(float target)
    {
        this.target = target;
    }


    public float getOutput()
    {
        if (neuronType == Neuron.INPUT)
        {
            output = 1 / (float) (1 + Math.pow(Math.E, -inputInput));  //sigmoid activation
        }
        else if (neuronType == Neuron.BIAS)
        {
                        output = 1 / (float) (1 + Math.pow(Math.E, -inputInput));  //sigmoid activation
             //output = 1;
        }
        else
        {
            input = 0; // reset inputs
            //sum inputs
            for (int i = 0; i < inputNeurons.size(); i++) {
                input += inputNeurons.get(i).getOutput() * inputWeights.get(i);
            }
            output = 1 / (float) (1 + Math.pow(Math.E, -input));  //sigmoid activation

        }
        return output;
    }

    public float getError()
    {
        return output - target;
    }


    private void computeDelta()
    {
        if (neuronType == Neuron.OUTPUT)
        {
            delta = (float) output * (1 - output) * getError();
        }
        else if (neuronType == Neuron.HIDDEN)
        {
            float sumDeltaOut = 0;
            for (Neuron outputNeuron : outputNeurons)
            {
                sumDeltaOut += outputNeuron.getDelta() * outputNeuron.getWeight(this);
            }
            delta = (float) output * (1 - output) * sumDeltaOut;
        }
        //deltaComputed = true;
    }

    public void adjustWeight()
    {
        for (int i = 0; i < inputWeights.size(); i++)
        {

            inputWeights.set(i, inputWeights.get(i) + (float) - NeuronNet.LEARN_RATE * getDelta() * inputNeurons.get(i).getOutput());
        }
        //deltaComputed = false;
    }


    public float getWeight(Neuron n)
    {
        return inputWeights.get(inputNeurons.indexOf(n));  //get weight of n on this
    }

    public float getDelta()
    {
        if (!deltaComputed)
        {
            computeDelta();
        }
        return delta;


    }


}
