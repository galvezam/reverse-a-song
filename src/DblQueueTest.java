// File name: DblQueueTest.java
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
// Description: Tests the DblQueue class
// Last Changed: 3/25/24

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DblQueueTest {

    @Test
    public void testDefaultConstructor() {
        DblQueue d = new DblQueue();
        assertEquals(0, d.size());
        assertTrue(d.isEmpty());
        assertThrows(RuntimeException.class, () -> d.front());
    }

    @Test
    public void testEnqueue() {
        DblQueue d = new DblQueue();
        d.enqueue(1.1);
        assertEquals(1, d.size());
        assertEquals(1.1, d.front());

        for (int i = 0; i < 50; ++i) {
            d.enqueue(1.1);
        }
        d.enqueue(5.0);
        assertEquals(1.1, d.front());
        assertEquals(52, d.size());
    }

    @Test
    public void testDequeue() {
        DblQueue d = new DblQueue();
        assertThrows(RuntimeException.class, () -> d.dequeue());

        d.enqueue(1.1);
        d.enqueue(5.0);
        d.dequeue();


        assertEquals(1, d.size());
        assertEquals(5.0,  d.front());

        d.dequeue();
        assertEquals(0, d.size());
        assertThrows(RuntimeException.class, () -> d.front());

        for (int i = 0; i < 50; ++i) {
            d.enqueue(1.1);
        }
        d.enqueue(1.5);
        for (int i = 0; i < 50; ++i) {
            d.dequeue();
        }
        assertEquals(1.5, d.front());


    }

    @Test
    public void testClone() {
        DblQueue d = new DblQueue();

        d.enqueue(1.1);
        DblQueue c = d.clone();
        assertEquals(1.1, c.front());
        assertEquals(1, c.size());

        for (int i = 0; i < 50; ++i) {
            d.enqueue(1.1);
        }
        DblQueue c1 = d.clone();

        assertEquals(51, c1.size());
        assertEquals(1.1, c1.front());

        c1.enqueue(1.5);
        assertEquals(51, d.size());

    }


    @Test
    public void testEquals() {
        DblQueue d = new DblQueue();
        DblQueue d1 = new DblQueue();
        d.enqueue(1.1);

        assertFalse(d.equals(d1));

        d1.enqueue(1.1);

        assertTrue(d.equals(d1));

        d.enqueue(1.2);
        d1.enqueue(1.3);
        d.enqueue(1.3);
        assertFalse(d.equals(d1));

        d.dequeue();
        d.dequeue();
        d1.dequeue();

        assertTrue(d.equals(d1));
    }
}
