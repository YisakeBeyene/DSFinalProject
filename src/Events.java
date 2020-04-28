import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Events {

    //Our two properties for an event
    String event;
    Calendar eventDate;

    //Constructor for our event class
    public Events(String event, Calendar eventDat) {
        this.event = event;
        this.eventDate = eventDat;
    }

//    All the getters and setters
    public String getEvent() {
        return event;
    }

    public Calendar getEventDate() {
        return eventDate;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setEventDate(Calendar eventDat) {
        this.eventDate = eventDat;
    }

    //Method to check if the day for this instance(e1) is before the other(e2)
    public boolean before(Events e2){

        Calendar e1Date = this.eventDate;
        Calendar e2Date = e2.getEventDate();

        return e1Date.before(e2Date);
    }

    //Method to change the calender attribute to string so we can print them or write them to file
    public String toString(){
        //Formatted this way to make parsing them easier when reading them from file
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        Date ourTime = this.eventDate.getTime();
        if (ourTime!=null){
            String date = format1.format(ourTime);
            return date + " " + this.event;
        }
        return " ";
    }
}


