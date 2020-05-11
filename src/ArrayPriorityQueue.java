import javafx.event.Event;

import java.util.Calendar;

public class ArrayPriorityQueue {

    //Stores an array of Events
    Events[] A;
    int n;

    public ArrayPriorityQueue(int capacity) {
        A = new Events[capacity];
        n = 0;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public Events getMin() {
        if (!isEmpty()) {
            return A[1];
        }
        return null;
    }

    public void insert(Events k) {
        A[n+1] = k;
        n++;
        int cIndex = n;
        int pIndex = parentIndex(n);
        //while child is not root and child is smaller than parent
        while (pIndex >= 1 && A[cIndex].before(A[pIndex])) {
            swap(cIndex,pIndex);
            cIndex = pIndex;
            pIndex = parentIndex(cIndex);
        }
    }

    public Events extractMin() {
        swap(1,n);
        n--;
        int pIndex = 1;
        int lCIndex;
        int rCIndex;
        while (hasSmallerChild(pIndex)) {
            lCIndex = rightChildIndex(pIndex);
            rCIndex = leftChildIndex(pIndex);
            //No right child, then swap parent with left child
            if (rCIndex > n) {
                swap(pIndex, lCIndex);
                pIndex = lCIndex;
            }
            else { //Two children, swap withe the smaller child
                if (A[lCIndex].before(A[rCIndex])) {
                    swap(pIndex,lCIndex);
                    pIndex = lCIndex;
                }
                else {
                    swap(pIndex,rCIndex);
                    pIndex = rCIndex;
                }
            }
        }
        return A[n+1];
    }

    private int parentIndex(int cIndex) {
        return cIndex/2;
    }

    private int leftChildIndex(int pIndex) {
        return pIndex * 2;
    }

    private int rightChildIndex(int pIndex) {
        return (pIndex * 2) + 1;
    }

    private void swap(int pIndex, int cIndex) {
        Events temp = A[cIndex];
        A[cIndex] = A[pIndex];
        A[pIndex] = temp;
    }

    private boolean hasSmallerChild(int pIndex) {
        int rCIndex = rightChildIndex(pIndex);
        int lCIndex = leftChildIndex(pIndex);
        if (rCIndex <= n && A[rCIndex].before(A[pIndex]))
            return true;
        else if (lCIndex <= n && A[lCIndex].before(A[pIndex]))
            return true;
        else
            return false;
    }


    /* Method to print all the events in the ArrayPriorityQueue
     * Creates a user profile based on parameters given.
     * @param name, email, password of type string
     * @param classYear of type int.
     */
    public void printAllEvents(){
        for (int i=0; i<n+1; i++){
            // print while end of array not reached.
            if (A[i] !=null){
                System.out.println(A[i].toString());
            }
        }
    }

    /* Method to remove the event in the array which has passed its time
     * Creates a user profile based on parameters given.
     * @param name, email, password of type string
     * @param classYear of type int.
     */
    public void removePastEvents(){

        for (int i=1; i<this.n+1; i++){
            // If array not empty
            if (A[i]!=null){
                // Get event time and date
                Calendar ourEvent = A[i].getEventDate();
                Calendar currentTime = Calendar.getInstance();
                // Check event against current time
                if (ourEvent.after(currentTime)){
                    //Stop once we get to an event that is after the current time
                    break;
                    // If it is before (i.e  a past event)
                    // Extract it
                }else{
                    this.extractMin();
                }
            }

        }
    }

}

