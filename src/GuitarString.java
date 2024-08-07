// File name: GuitarString.java
// Author: Matthew Galvez
// userid: galvezam
// Email: alex.m.galvez@vanderbilt.edu
// Class: CS2201
// Assignment Number: Project 06
// Honor statement: By submitting this homework under my personal Brightspace account, I attest
// that I have neither given nor received unauthorized aid concerning this homework. I further
// acknowledge the instructors are the copyright owners of this HW. Any posting/uploading of HW
// questions for distribution (e.g., GitHub, Chegg) will be considered an honor code violation
// (even after finishing this class) and submitted to the honor council.
// Description: Creates a queue that simulates a guitar string.
// Last Changed: 3/25/24
//

import java.util.Random;

public class GuitarString {
    // Do not change or delete these two public constants
    public static final int SAMPLE_RATE = 44100;
    public static final double DECAY_FACTOR = 0.996;


    private DblQueue q;
    private int numOfTics;
    private double freq;
    private Random r;
    private int numOfPlucks;

    public GuitarString(double frequency) throws IllegalArgumentException{
        if (frequency <= 0) {
            throw new IllegalArgumentException("Frequency must be positive");
        }
        else {
            this.q = new DblQueue();
            this.numOfTics = 0;
            this.numOfPlucks = 0;
            this.freq = frequency;
            int rBuffer = (int) Math.round(SAMPLE_RATE / freq);

            for (int i = 0; i < rBuffer; ++i) {
                q.enqueue(0);
            }

            r = new Random(1);
        }
    }

    public void pluck() {

        for (int i = 0; i < q.size(); ++i) {
            double r1 = r.nextDouble(0.0, 1.0);
            q.enqueue((0.5 - r1));
            q.dequeue();
        }
        ++numOfPlucks;
    }

    public void tic() {
        ++numOfTics;
        double n  = q.front();
        q.dequeue();
        double n1 = q.front();
        q.enqueue(((n + n1) / 2.0 * DECAY_FACTOR));
    }

    public double sample() {
        return q.front();
    }

    public int getTime() {
        return numOfTics;
    }

    public double getFrequency() {
        return freq;
    }

    public GuitarString clone() {
        GuitarString g = new GuitarString(freq);
        g.r = this.r;

        for (int i = 0; i < this.numOfTics; ++i) {
            g.tic();
        }
        for (int j = 0; j < this.numOfPlucks; ++j) {
            g.pluck();
        }

        g.freq = this.getFrequency();
        return g;
    }

    public boolean equals(Object other) {
        if (other instanceof GuitarString) {
            GuitarString c = (GuitarString) other;
            if (c.getFrequency() == this.getFrequency()) {
                return c.getTime() == this.getTime();
            }
            return false;
        }
        return false;
    }
}
