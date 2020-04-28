import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Stack;

public class MySocialProfile {

    String name;
    String email;
    String password;
    int classYear;
    Events upcomingEvents;
    Deque<String> timelinePosts;
    ArrayList<String> friends;

    public String name() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getClassYear() {
        return classYear;
    }

    public Events getUpcomingEvents() {
        return upcomingEvents;
    }

    public Deque<String> getTimelinePosts() {
        return timelinePosts;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }



    public MySocialProfile(String name, String email, String password, int classYear){
        this.name = name;
        this.email = email;
        this.password = password;
        this.classYear = classYear;
        this.friends = new ArrayList<>(5);
        this.upcomingEvents = new Events(20);
        this.timelinePosts = new ArrayDeque<>();
    }

    public MySocialProfile(String name, String email, String password, int classYear, Events upcomingEvents, Deque<String> timelinePosts, ArrayList<String> friends){
        this.name = name;
        this.email = email;
        this.password = password;
        this.classYear = classYear;
        this.upcomingEvents = upcomingEvents;
        this.timelinePosts = timelinePosts;
        this.friends = friends;
    }

}
