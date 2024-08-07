// File name: GuitarHero.java
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
// Description: Create a dat file that simulates ..
// Last Changed: 3/25/24
//

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.io.PrintStream;

public class GuitarHero {
    public static final double CONCERT_A = 440.0;
    public static final int NUM_STRINGS = 37;
    public static final double STEP = (double)1/((double)GuitarString.SAMPLE_RATE);
    public static final int END_OF_SONG = -1;


    public static void main(String[] args) throws FileNotFoundException {

        // create the array of guitar strings (3 octaves)
        GuitarString[] strings = new GuitarString[NUM_STRINGS];
        createStrings(strings);

        //open data file
        Scanner keyboard = new Scanner(System.in);
        Scanner infile = openInputFile(keyboard);
        PrintStream outfile = openOutputFile(keyboard);

        //prime the output file
        String firstLine = "; Sample Rate " + GuitarString.SAMPLE_RATE;
        String secondLine = "; Channels 1";
        outfile.println(firstLine);
        outfile.println(secondLine);

        System.out.println("Reading the input file and generating a .dat file for sox");

        double clock = 0.0;
        double prev = 0.0;

        while (infile.hasNextDouble()) {

            double time = infile.nextDouble();
            double note = infile.nextDouble();
            verifyTime(time, prev);
            verifyNote(note);

            int stringNote = (int) note;

            while (clock <= time) {
                outfile.println(" " + clock + "\t" + strings[stringNote].sample());
                clock = clock + STEP;
                strings[stringNote].tic();
            }


            while (stringNote != END_OF_SONG) {
                prev = time;
                time = infile.nextDouble();
                verifyTime(time, prev);

                strings[stringNote].pluck();

                while (clock <= time) {
                    outfile.println(" " + clock + "\t" + strings[stringNote].sample());
                    clock = clock + STEP;
                    strings[stringNote].tic();
                }

                note = infile.nextDouble();
                verifyNote(note);

                stringNote = (int) note;
            }
        }

        // close the files
        infile.close();
        outfile.close();

        System.out.println("Done.");
    }


    /**
     * verifyTime -- verifies time is positive and in chronological order
     * post: throws runtime exception if invalid
     * @param time, prev
     */
    private static void verifyTime(double time, double prev) {
        if (time < 0) {
            throw new RuntimeException("Out of time range");
        }
        else if (prev > time) {
            throw new RuntimeException("Not in Chronological Order");
        }
    }

    /**
     * verifyNote -- verifies note to be in range of string keys
     * post: throws runtime exception if invalid
     * @param note
     */
    private static void verifyNote(double note) {
        if (note <-1 || note > 36) {
            throw new RuntimeException("Out of range");
        }
    }


    /**
     * createStrings -- create the guitar string objects
     * post: array of GuitarString objects have been created and initialized
     * @param strings
     */
    private static void createStrings(GuitarString[] strings) {
        for (int i = 0; i < strings.length; i++) {
            double factor = Math.pow(2.0, (i - 24) / 12.0);

            strings[i] = new GuitarString(CONCERT_A * factor);


        }
    }


    /**
     * openInputFile
     * pre: user is prepared to enter file name at the keyboard
     * post: file have been opened
     * @param keyboard -- opened Scanner object
     * @return Scanner object opened on input file
     * @throws FileNotFoundException
     */
    private static Scanner openInputFile (Scanner keyboard) throws FileNotFoundException {

        // open input data file
        String inFileName;
        System.out.print("Enter the name of the input file: ");
        inFileName = keyboard.nextLine();
        File f = new File(inFileName);
        while (!f.canRead()) {
            System.out.println("File not found. Try again.");
            System.out.print("Enter the name of the input file: ");
            inFileName = keyboard.nextLine();
            f = new File(inFileName);
        }
        Scanner infile = new Scanner(f);
        return infile;
    }


    /**
     * openOutputFile
     * pre: user is prepared to enter file name at the keyboard
     * post: file have been opened
     * @param keyboard -- opened Scanner object
     * @return PrintStream object opened on output file
     * @throws FileNotFoundException
     */
    private static PrintStream openOutputFile (Scanner keyboard) throws FileNotFoundException {

        // open output data file
        String outFileName;
        System.out.print("Enter the name of the output file: ");
        outFileName = keyboard.nextLine();
        File f2 = new File(outFileName);
        PrintStream outfile = new PrintStream(f2);
        return outfile;
    }
}
