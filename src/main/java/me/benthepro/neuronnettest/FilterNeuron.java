package me.benthepro.neuronnettest;

/**
 * Created by ben on 7/21/15.
 */
public class FilterNeuron {
    private int filterSizeX;
    private int filterSizeY;
    private int depth;
    private Neuron[][][] inputNeurons;
    private Neuron outputNeuron = new Neuron(Neuron.HIDDEN);


    FilterNeuron(int x, int y, int depth)
    {
        this.filterSizeX = x;
        this.filterSizeY = y;
        this.depth = depth;
        inputNeurons = new Neuron[filterSizeX][filterSizeY][depth];  //convolutional layer 1, nxnxrgb
        for (x = 0; x < filterSizeX; x++) {
            for (y = 0; y < filterSizeY; y++) {
                for (int d = 0; d < d; d++) {
                    inputNeurons[x][y][d] = new Neuron(Neuron.INPUT);
                    outputNeuron.addInputNeuron(inputNeurons[x][y][d], Utils.rand(-1, 1));
                }
            }
        }
    }

    public void setInput(float[][][] input)
    {
        for (int x = 0; x < filterSizeX; x++) {
            for (int y = 0; y < filterSizeY; y++) {
                for (int d = 0; d < d; d++) {
                    inputNeurons[x][y][d].setInput(input[x][y][d]);
                }
            }
        }
    }


    public Neuron getOutputNeuron()
    {
        return outputNeuron;
    }
}
