import java.util.*;
import java.io.*;

// Main Class
public class mainClass {



    /* Method to read the data from file.
     * To retriev an old file.
     * The method takes no parameters.
     * @return oldProfile.
     */
    public static MySocialProfile readFromFile(){
        //Only reds if there is a profile, else returns error.
        MySocialProfile oldProfile;
        try {
            // Creates a scanner object of the file.
            Scanner lineScanner = new Scanner(new FileInputStream("mysocialprofile.txt"));
            while (lineScanner.hasNext()) { //while more of the input file is still available for reading
                String name = lineScanner.nextLine();  //reads an entire line of input and store it in a variable
                String email = lineScanner.nextLine(); // Reads the line after and stores it as email
                String pass = lineScanner.nextLine();  // Reads the line after then stores is as password
                int year = Integer.parseInt(lineScanner.nextLine());

                //For the next three lines. Get the line, parse it and store it in a variable
                String eventLine = lineScanner.nextLine();
                ArrayPriorityQueue events = new ArrayPriorityQueue(40);
                if (eventLine.length() != 0){
                    // Put all events into an array of events.
                    String[] event = eventLine.split(", ");
                    for (int i=0; i<event.length; i++){
                        // Change all data into events format
                        events.insert(ChangetoEvents(event[i]));
                    }
                }

                // Read wall messages an assign it into variable.
                String wallMsgs = lineScanner.nextLine();
                // Create Deque array for timeline.
                Deque<String> timeLine = new ArrayDeque<>();
                if (wallMsgs.length() != 0){
                    // separate all messages and put them into an array spearately.
                    String[] timeL = wallMsgs.split(", ");
                    for (String msg: timeL){
                        timeLine.push(msg);
                    }
                }

                // Follows a process, similar to creating wall messages, to create friend lists.
                String friendLine = lineScanner.nextLine();
                ArrayList<String> friends = new ArrayList<>();
                if (friendLine.length()!=0){
                    String[] friend = friendLine.split(", ");
                    for (String frnd: friend){
                        friends.add(frnd);
                    }
                }

                //Creates a file object of the old profile.
                oldProfile = new MySocialProfile(name, email, pass, year, events, timeLine, friends);
                return oldProfile;
            }
            // Handles errors
        } catch(FileNotFoundException ex) {
            System.out.println("File not Found");
            System.exit(0);
        }

        return null;
    }

    /*Method to change string value of a data to a date object
     * Does that by splitting the line based on special characters.
     *@param eventstring striing with all event data
     *@return newEvent object
     */
    private static Events ChangetoEvents(String eventString){

        // Splits data, puts them into event format
        // Then adds them inot an array of events.
        Calendar eventDate = Calendar.getInstance();
        String[] eventArray = eventString.split(" ");
        String[] date = eventArray[0].split("/");
        String[] time = eventArray[1].split(":");
        String event = "";
        for (int i=2; i<eventArray.length; i++){
            event += eventArray[i] + " ";
        }

        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]) - 1; //-1 becasue for some reason java kept increasing the month by one everytime I ran the code
        int day = Integer.parseInt(date[2]);
        int hour = Integer.parseInt(time[0]);
        int min = Integer.parseInt(time[1]);

        // Returns an event obect.
        eventDate.set(year, month, day, hour, min);
        Events newEvent = new Events(event, eventDate);
        return newEvent;

    }


    /* Method to save wthe profile saved on memory to file.
     * Takes the profile it will save as an attribute
     * @param profile
     * @return void
     */
    public static void saveToFile(MySocialProfile profile){
        // Simple getter methods
        ArrayPriorityQueue upcomingEvents = profile.getUpcomingEvents();
        Deque<String> timelinePosts = profile.getTimelinePosts();
        ArrayList<String> friends = profile.getFriends();

        try {
            // Craete a file object of the file in whihc the data will be saved.
            FileWriter fileOut = new FileWriter("mysocialprofile.txt");
            // Create a bufferwriter object form that file.
            BufferedWriter bufWriter = new BufferedWriter(fileOut);

            // Write the data to file.
            // Write different parts on spearate lines.
            bufWriter.write(profile.name);
            bufWriter.newLine();
            bufWriter.write(profile.email);
            bufWriter.newLine();
            bufWriter.write(profile.password);
            bufWriter.newLine();
            bufWriter.write(String.valueOf(profile.classYear));
            bufWriter.newLine();

            // Check firt of there are upcoming events.
            // If yes, convert them to string and write them to file.
            int i;
            if (!upcomingEvents.isEmpty()){
                for (i=1; i< profile.upcomingEvents.size(); i++) {
                    if (profile.upcomingEvents.A[i] != null) {
                        bufWriter.write(profile.upcomingEvents.A[i] + ", ");
                    }
                }
                bufWriter.write(upcomingEvents.A[i].toString());
                bufWriter.newLine();
                // Otherwise just write a new line.
            }else{
                bufWriter.newLine();
            }

            // Check firt of there are timeline posts.
            // If yes, convert them to string and write them to file.
            int j;
            if (!timelinePosts.isEmpty()){
                for (j=timelinePosts.size(); j>1; j--){
                    bufWriter.write(profile.timelinePosts.pollLast() + ", ");
                }
                bufWriter.write(timelinePosts.pollLast());
                bufWriter.newLine();
                // Otherwise just write a new line.
            }else{
                bufWriter.newLine();
            }

            // Check firt of there are friends.
            // If yes, convert them to string and write them to file.
            int k;
            if (!friends.isEmpty()){
                for (k=0; k<profile.friends.size()-1; k++){
                    bufWriter.write(profile.friends.get(k)+ ", ");
                }
                bufWriter.write(profile.friends.get(k));
                // Otherwise just write a new line.
            }else{
                bufWriter.newLine();
            }


            bufWriter.close();
            fileOut.close();
            // Handles errors
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Method to create a new MySocialProfile
     * It prompts the user and gathers infomration.
     * @param in - scanner object
     * @return void
     */
    public static void createNewProfile(Scanner in){

        System.out.println();
        System.out.println("+++++++++++++++++++++++++++++++++++++++");
        System.out.println();
        System.out.println("Please enter the following information to create a new profile");
        //Scanner in = new Scanner(System.in);
        // Prompts user for name
        System.out.print("Enter your Name: ");
        String name = in.nextLine();
        // Prompts user for email
        System.out.print("Enter your Email: ");
        String email = in.nextLine();
        // Prompts user for password
        System.out.print("Enter your Password: ");
        String password = in.nextLine();
        // Prompts user for class year
        System.out.print("Enter your Class Year: ");
        int classYear = Integer.parseInt(in.nextLine());

        // Creates a newProfile object form data created.
        MySocialProfile newProfile = new MySocialProfile(name, email, password, classYear);

        homeScreen(newProfile, in);
    }

    /* Method that shows the options in the homescreen and runs all the others methods accordingly
     * It prompts the user and gathers infomration.
     * @param profile, in - user profile and scanner object.
     * @return void
     */
    public static void homeScreen(MySocialProfile profile, Scanner in){

        //Print Headings
        while(true){
            System.out.println();
            System.out.println("+++++++++++++++++++++++++++++++++++++++");
            System.out.println();

            //Remove all the events that are outdated
            if (!profile.upcomingEvents.isEmpty()){
                profile.upcomingEvents.removePastEvents();
            }

            // Getter methods
            ArrayPriorityQueue upcomingEvents = profile.getUpcomingEvents();
            Deque<String> timelinePosts = profile.getTimelinePosts();
            ArrayList<String> friends = profile.getFriends();

            // Show the next event
            // Or a message if there are no upcoming ones.
            if (upcomingEvents.isEmpty()){
                System.out.println("You have no upcoming Event");
            }else{
                //Print out the next event
                System.out.print("Your next events is: ");
                System.out.println(upcomingEvents.getMin().toString());
            }

            //Show all the timeline posts
            if (timelinePosts.isEmpty()){
                System.out.println("You have nothing on your timeline");
            }else {
                int i = 0;
                System.out.println("Your timeline is: ");
                for (String post: timelinePosts){
                    if (i < 3) {
                        System.out.println(i+1 + ") "+ post);
                        i++;
                    }
                }
                System.out.println("");
            }

            //Show the rest of the events
            if (!upcomingEvents.isEmpty()){
                System.out.println("The rest of your events are: ");
                upcomingEvents.printAllEvents();
            }
            System.out.println("+++++++++++++++++++++++++++++++++++++++");
            System.out.println();


            //Secondary list of options.
            System.out.println("Press 1 to post on your timeline");
            System.out.println("Press 2 to add an event you want to attend");
            System.out.println("Press 3 to view list of your friend");
            System.out.println("Press 4 to add or remove a friend");
            System.out.println("Press 5 to logout");
            System.out.print("Enter your choice here: ");

            // Prompts are descriptive of the functionality of options
            String s = in.nextLine();

            // Adds psot to timeline
            if (s.equals("1")){
                System.out.println("Input text that will become the latest item in their timeline: ");
                String newTimeline = in.nextLine();
                profile.timelinePosts.push(newTimeline);
                // Creates an event object with dta provided form user.
            }else if(s.equals("2")){
                System.out.println("Input the event info");
                newEvent(profile, in);
                // Display friend list
            }else if(s.equals("3")){
                if (friends.isEmpty()){
                    System.out.println("You have no friend for now");
                }else{
                    System.out.print("Your friends are: ");
                    for (String friendName : friends) {
                        System.out.print(friendName+ "/ ");
                    }
                }
                // If email found, remove friend
                // Else add frind's email
            }else if(s.equals("4")){
                //Maybe create a helper method for these codes
                System.out.print("Input the email of your friend: ");
                String friendEmail = in.nextLine();
                boolean found = false;
                for (String emails : friends) {
                    if (friendEmail.equals(emails)){
                        friends.remove(emails);
                        found = true;
                        break;
                    }
                }
                if (!found){
                    friends.add(friendEmail);
                }
                // Save profile and log out.
            }else if(s.equals("5")){
                //Save the Profile to the txt file
                saveToFile(profile);
                System.out.println("Thank you for using our App");
                System.exit(0);
            }else{
                System.out.print("Input not recognized. Please enter your input again: ");
                continue;
            }
        }


    }

    /* Method to create a new event. Takes the profile it will store the event to as an attribute
     * It prompts the user and gathers infomration.
     * @param profile, in - user profile and scanner object.
     * @return void
     */
    public static void newEvent(MySocialProfile profile, Scanner in){

        /*get user inputted date and time*/
        int month, day, year, hour, min;
        System.out.print("Please enter the month MM: ");
        month = Integer.parseInt(in.nextLine()) - 1;
        System.out.print("Please enter the day DD: ");
        day = Integer.parseInt(in.nextLine());
        System.out.print("Please enter the year YYYY: ");
        year = Integer.parseInt(in.nextLine());
        System.out.print("Please enter the hour of the day (0-23): ");
        hour = Integer.parseInt(in.nextLine());
        System.out.print("Please enter the minute of the hour (00-59): ");
        min = Integer.parseInt(in.nextLine());
        System.out.print("Please enter the event: ");
        String event = in.nextLine();

        System.out.println("\nYou entered: " + month + "/" + day + "/" + year + " at " + hour + ":" + min);

        // Add event object to user's profile.
        Calendar eventDate = Calendar.getInstance();
        eventDate.set(year, month, day, hour, min);
        String eventString = (month + " " + day + " " + year + " " +  hour + " " +  min + " " +  event);
        Events newEvent = new Events(event, eventDate);
        profile.upcomingEvents.insert(newEvent);

        homeScreen(profile, in);
    }


    // Main method that runs the program.
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        while(true){
            //Prompt the user for choices
            System.out.println("+++++++++++++++++++++++++++++++++++++++");
            System.out.println("Press 1 to Create a new account ");
            System.out.println("Press 2 to Load an existing profile ");
            System.out.println("Press 3 to Exit the  program ");
            System.out.print("Enter your input here: ");
            String s = in.nextLine();

            if (s.equals("1")){
                //Prompt the user to create a new Account
                createNewProfile(in);
            }else if(s.equals("2")){
                //Read the profile from txt if it exists
                MySocialProfile savedProfile = readFromFile();

                if (savedProfile!=null){
                    homeScreen(savedProfile, in);
                }else{
                    System.out.println("+++++++++++++++++++++++++++++++++++++++");
                    System.out.println("There is no stored profile");
                    continue;
                }
            }else if(s.equals("3")){
                System.out.println("Thank you for using our App");
                break;
            }else{
                System.out.print("Input not recognized. Please enter your input again: ");
                continue;
            }
        }

        in.close();

    }
}
