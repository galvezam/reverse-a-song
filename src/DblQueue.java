// File name: DblQueue.java
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
// Description: Creates a queue ADT
// Last Changed: 3/25/24
//

public class DblQueue {

    public class qNode {
            public double value;
            public qNode next;
            public qNode() {
                this(-1, null);
            }
            public qNode(double value) {
                this(value, null);
            }
            public qNode(double value, qNode next) {
                this.value = value;
                this.next = next;
            }
    }
    private qNode head;
    private qNode tail;
    private int size;

    public DblQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue (double item) {
        if (head == null) {
            head = new qNode(item);
            tail = head.next;
        }
        else if (head.next == null) {
            head.next = new qNode(item);
            tail = head.next;
        }
        else {
            tail.next = new qNode(item);
            tail = tail.next;
        }
        ++size;
    }

    public void dequeue() throws RuntimeException{
        if (head == null) {
            throw new RuntimeException("Empty queue");
        }
        else {
            head = head.next;
            --size;
        }
    }

    public double front() throws RuntimeException{
        if (head == null) {
            throw new RuntimeException("Empty queue");
        }
        else {
            return head.value;
        }
    }

    public int size() {
        return this.size;
    }


    public DblQueue clone() {
        DblQueue c = new DblQueue();


        for (qNode c2 = head; c2 != null; c2 = c2.next) {
            c.enqueue(c2.value);
        }

        return c;
    }

    public boolean equals(Object other) {
        if (other instanceof DblQueue) {
            DblQueue s = (DblQueue) other;

            if (s.size() == size) {
                qNode c, c1;
                for (c = head, c1 = s.head; c!= null && c1 != null;
                     c = c.next, c1 = c1.next) {
                    if (c.value != c1.value) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
        return false;
    }


}

