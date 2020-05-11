import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

// Creates a social profile for each user.
public class MySocialProfile {

    // Declaring Instance Varibales in Java.
    String name;
    String email;
    String password;
    int classYear;
    ArrayPriorityQueue upcomingEvents;
    Deque<String> timelinePosts;
    ArrayList<String> friends;

    //Getters for the profile properties
    public String getName() {
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

    public ArrayPriorityQueue getUpcomingEvents() {
        return upcomingEvents;
    }

    public Deque<String> getTimelinePosts() {
        return timelinePosts;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    /* Constructur without upcoming event, timelineposts and friends
     * Creates a user profile based on parameters given.
     * @param name, email, password of type string
     * @param classYear of type int.
     */
    public MySocialProfile(String name, String email, String password, int classYear){
        this.name = name;
        this.email = email;
        this.password = password;
        this.classYear = classYear;
        this.friends = new ArrayList<>(5);
        this.upcomingEvents = new ArrayPriorityQueue(20);
        this.timelinePosts = new ArrayDeque<>();
    }


    /* Constructure with all attributes
     * Creates a user profile based on parameters given.
     * @param name, email, password of type string - classYear of type integer
     * @param upcomingEvents of type ArrayPriorityQueue - timelinePosts of type Deque<String>
     * @param friends of type ArrayList<String>
     * @param classYear of type int.
     */
    public MySocialProfile(String name, String email, String password, int classYear, ArrayPriorityQueue upcomingEvents, Deque<String> timelinePosts, ArrayList<String> friends){
        this.name = name;
        this.email = email;
        this.password = password;
        this.classYear = classYear;
        this.upcomingEvents = upcomingEvents;
        this.timelinePosts = timelinePosts;
        this.friends = friends;
    }

}
