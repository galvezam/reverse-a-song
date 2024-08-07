// File name: GuitarStringTest.java
// Author: Matthew Galvez
// VUnetid: galvezam
// Email: alex.m.galvez@vanderbilt.edu
// Class: CS2201
// Assignment Number: Project 06
// Honor statement: By submitting this homework under my personal Brightspace account, I attest
// that I have neither given nor received unauthorized aid concerning this homework. I further
// acknowledge the instructors are the copyright owners of this HW. Any posting/uploading of HW
// questions for distribution (e.g., GitHub, Chegg) will be considered an honor code violation
// (even after finishing this class) and submitted to the honor council.
// Description: Tests the GuitarString class
// Last Changed: 3/25/24

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GuitarStringTest {


    @Test
    public void testConstructor() {
        double testFreq = 1000;
        GuitarString myString = new GuitarString(testFreq);

        assertEquals(testFreq, myString.getFrequency(), 0.00001);

    }

    @Test
    public void testPluck() {
        double testFreq = 1000;
        GuitarString myString = new GuitarString(testFreq);
        myString.pluck();

        // initialized random to a distinct seed in GuitarString class to compare plucks
        Random r = new Random(1);

        double r1 = r.nextDouble(0.0, 1.0);
        double rand = 0.5 - r1;
        assertEquals(myString.sample(), rand);

    }

    @Test
    public void testTic() {
        double testFreq = 1000;
        GuitarString myString = new GuitarString(testFreq);

        myString.pluck();
        Random r = new Random(1);

        int n = (int) Math.round(GuitarString.SAMPLE_RATE / myString.getFrequency());

        double r1 = r.nextDouble(0.0, 1.0);
        double rand = 0.5 - r1;

        double r2 = r.nextDouble(0.0, 1.0);
        double rand1 = 0.5 - r2;

        double d = (rand + rand1) / 2.0 *  GuitarString.DECAY_FACTOR;

        for (int i = 0; i < n; ++i) {
            myString.tic();
        }

        assertEquals(44, myString.getTime());
        assertEquals(d, myString.sample());

        for (int i = 0; i < 100000; ++i) {
            myString.tic();
        }
        assertEquals(myString.sample(), 0.0, 0.00001);

    }

    @Test
    public void testClone() {
        double testFreq = 1000;
        GuitarString myString = new GuitarString(testFreq);

        myString.pluck();
        myString.tic();
        myString.tic();

        GuitarString g = myString.clone();

        assertEquals(2, g.getTime());
        assertEquals(1000, g.getFrequency());
        assertTrue(g.equals(myString));
    }

}
