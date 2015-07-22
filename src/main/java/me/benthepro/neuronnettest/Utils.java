package me.benthepro.neuronnettest;

import java.util.Random;

/**
 * Created by ben on 7/21/15.
 */
public class Utils {
    private static Random rand = new Random(NeuronNet.startTime);

    public static float rand(int a, int b)
    {
        return rand.nextFloat() * (b - a) - b;
    }

}
