import java.util.ArrayList;
import java.util.Calendar;

public class Events {
    String[] A;
    int n;

    public Events(int capacity) {
        A = new String[capacity];   
        n = 0;
    }

    public Events(String[] A){
        this.A = A;
    }


    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public String getMin() {
        return A[1];
    }

    public void insert(String k) {
        A[n+1] =  k;
        n++;
        int cIndex = n;
        int pIndex = parentIndex(n);
        //while child is not root and child is smaller than parent
        while (pIndex >= 1 && before(A[cIndex], A[pIndex])) {
            swap(cIndex,pIndex);
            cIndex = pIndex;
            pIndex = parentIndex(cIndex);
        }
    }

    public String extractMin() {
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
                if (before(A[lCIndex], A[rCIndex])) {
                    swap(pIndex,lCIndex);
                    pIndex = lCIndex;
                }
                else {
                    swap(pIndex,rCIndex);
                    pIndex = rCIndex;
                }
            }
        }
        //Actually delete the value
        String minValue = A[n+1];
        A[n+1] = null;

        return minValue;
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
        String temp = A[cIndex];
        A[cIndex] = A[pIndex];
        A[pIndex] = temp;
    }

    private boolean hasSmallerChild(int pIndex) {
        int rCIndex = rightChildIndex(pIndex);
        int lCIndex = leftChildIndex(pIndex);
        if (rCIndex <= n && before(A[rCIndex], A[pIndex]))
            return true;
        else if (lCIndex <= n && before(A[lCIndex], A[pIndex]))
            return true;
        else
            return false;
    }

    private boolean before(String e1,String e2){

        Calendar cal1 = changeToDate(e1);
        Calendar cal2 = changeToDate(e2);

        return cal1.before(cal2);
    }

    private boolean after(String e1,String e2){
        Calendar cal1 = changeToDate(e1);
        Calendar cal2 = changeToDate(e2);

        return cal1.after(cal2);
    }

    public Calendar changeToDate(String e){
        String[] date = e.split(" ");
        Calendar cal = Calendar.getInstance();
        int month = Integer.parseInt(date[0]);
        int day = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);
        int hour = Integer.parseInt(date[3]);
        int min = Integer.parseInt(date[4]);
        String event = date[5];
        cal.set(2015, 12, 22,22,3);
        cal.set(year,month, day, hour, min);
        return cal;
    }

    public void printAllEvents(){
        for (int i=0; i<n; i++){
            if (A[i] !=null){
                System.out.println(i+1 + ") " + A[i]);
                i++;
            }
        }
    }

    public void printAllEventsI(){
        int i =0;
        for (String event: A){
            if (event !=null){
                System.out.println(i+1 + ") " + event);
                i++;
            }
        }
    }

    public void removePastEvents(){

        for (int i=0; i<n; i++){
            if (A[i]!=null){
                Calendar ourEvent = changeToDate(A[i]);
                Calendar currentTime = Calendar.getInstance();
                System.out.println(currentTime.getTime());
                if (ourEvent.getTime().after(currentTime.getTime())){
                    break;
                }else{
                    extractMin();
                }
            }

        }
    }

}


