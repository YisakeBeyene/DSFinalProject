import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class LearnCalendar {


    public static void main(String[] args) {
        //Learn calender
//        Calendar cal = Calendar.getInstance();
//        cal.set(1998, 2, 18, 12, 12);
//        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd hh:mm");
//
//        String date = format1.format(cal.getTime());
//        System.out.println(date);
////
//        System.out.println(cal.getTime());
//        cal.set(2012, 11, 2, 21, 44, 12);
//        System.out.println(cal.getTime());
//
//        Calendar cal2 = Calendar.getInstance();
//        System.out.println(cal.after(cal2));
//

        //Lern Delimeter
//        String something = "The guy is very werid";
//        String[] somethingWord = something.split(" ");
//        System.out.println(somethingWord[2]);

        //Learn toString
//        String[] something = {"sf", "sdf", "sdf"};
//        System.out.println(Arrays.toString(something));


        //Learn Event
//        Events ev = new Events(12);
//        ev.insert("alskdfj");
//        ev.printAllEvents();

        //CHECK DELETE PROBLEM
//        String[] someArray = {"asdf", "sadf", "saf"};
//        if (someArray!=null){
//            System.out.println(Arrays.toString(someArray));
//        }

        //Compate calander
//        Calendar myCal = Calendar.getInstance();
//        myCal.set(2012, 11, 12, 12,23);
//        Calendar currentTime = Calendar.getInstance();
//        if (myCal.getTime().before(currentTime.getTime())){
//            System.out.println("I dont know what is happening then");
//        }


        //Learn Arraylist
//        ArrayList<String> arr1= new ArrayList<>(5);
//        arr1.add(1, "asdf");
        //arr1.add(0, "asdfg");
//        System.out.println(arr1);


        //Compare calender
        Calendar currentTime = Calendar.getInstance();
        Calendar someTime = Calendar.getInstance();
        someTime.set(2021, 11, 2, 12,12);
        if (someTime.getTime().after(currentTime.getTime())){
            System.out.println("Yessss");
        }else{
            System.out.println("No");
        }
    }
}
