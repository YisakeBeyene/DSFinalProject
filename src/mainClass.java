import java.util.*;
import java.io.*;

public class mainClass {



    //Method to read the data from file
    public static MySocialProfile readFromFile(){
        //If there is a loaded profile else error
        MySocialProfile oldProfile;
        try {
            Scanner lineScanner = new Scanner(new FileInputStream("mysocialprofile.txt"));

            while (lineScanner.hasNext()) { //while more of the input file is still available for reading
                String name = lineScanner.nextLine();  //reads an entire line of input and store it in a variable
                String email = lineScanner.nextLine();
                String pass = lineScanner.nextLine();
                int year = Integer.parseInt(lineScanner.nextLine());

                //For the next three lines. Get the line, parse it and store it in a variable
                String eventLine = lineScanner.nextLine();
                ArrayPriorityQueue events = new ArrayPriorityQueue(40);
                if (eventLine.length() != 0){
                    String[] event = eventLine.split(", ");
                    for (int i=0; i<event.length; i++){
                        events.insert(ChangetoEvents(event[i]));
                    }
                }


                String wallMsgs = lineScanner.nextLine();
                Deque<String> timeLine = new ArrayDeque<>();
                if (wallMsgs.length() != 0){
                    String[] timeL = wallMsgs.split(", ");
                    for (String msg: timeL){
                        timeLine.push(msg);
                    }
                }

                String friendLine = lineScanner.nextLine();
                ArrayList<String> friends = new ArrayList<>();
                if (friendLine.length()!=0){
                    String[] friend = friendLine.split(", ");
                    for (String frnd: friend){
                        friends.add(frnd);
                    }
                }

                //Create a profile instance variable
                oldProfile = new MySocialProfile(name, email, pass, year, events, timeLine, friends);
                return oldProfile;
            }

        } catch(FileNotFoundException ex) {
            System.out.println("File not Found");
            System.exit(0);
        }


        return null;
    }

    //Method to change string value of a data to a date object

    private static Events ChangetoEvents(String eventString){

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

            eventDate.set(year, month, day, hour, min);
            Events newEvent = new Events(event, eventDate);
            return newEvent;

    }


    //Method to save what is saved on memory to file. Takes the profile it will save as an attribute
    public static void saveToFile(MySocialProfile profile){


        ArrayPriorityQueue upcomingEvents = profile.getUpcomingEvents();
        Deque<String> timelinePosts = profile.getTimelinePosts();
        ArrayList<String> friends = profile.getFriends();

        try {
            FileWriter fileOut = new FileWriter("mysocialprofile.txt");
            BufferedWriter bufWriter = new BufferedWriter(fileOut);


            bufWriter.write(profile.name);
            bufWriter.newLine();
            bufWriter.write(profile.email);
            bufWriter.newLine();
            bufWriter.write(profile.password);
            bufWriter.newLine();
            bufWriter.write(String.valueOf(profile.classYear));
            bufWriter.newLine();

            //**Write the others info's to
            int i;
            if (!upcomingEvents.isEmpty()){
                for (i=1; i< profile.upcomingEvents.size(); i++) {
                    if (profile.upcomingEvents.A[i] != null) {
                        bufWriter.write(profile.upcomingEvents.A[i] + ", ");
                    }
                }
                bufWriter.write(upcomingEvents.A[i].toString());
                bufWriter.newLine();

            }else{
                bufWriter.newLine();
            }

            int j;
            if (!timelinePosts.isEmpty()){
                for (j=timelinePosts.size(); j>1; j--){
                    bufWriter.write(profile.timelinePosts.pollLast() + ", ");
                }
                bufWriter.write(timelinePosts.pollLast());
                bufWriter.newLine();
            }else{
                bufWriter.newLine();
            }


            int k;
            if (!friends.isEmpty()){
                for (k=0; k<profile.friends.size()-1; k++){
                    bufWriter.write(profile.friends.get(k)+ ", ");
                }
                bufWriter.write(profile.friends.get(k));
            }else{
                bufWriter.newLine();
            }



            bufWriter.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Method to create a new MySocialProfile
    public static void createNewProfile(Scanner in){

        System.out.println();
        System.out.println("+++++++++++++++++++++++++++++++++++++++");
        System.out.println();
        System.out.println("Please enter the following information to create a new profile");
        //Scanner in = new Scanner(System.in);
        System.out.print("Enter your Name: ");
        String name = in.nextLine();
        System.out.print("Enter your Email: ");
        String email = in.nextLine();
        System.out.print("Enter your Password: ");
        String password = in.nextLine();
        System.out.print("Enter your Class Year: ");
        int classYear = Integer.parseInt(in.nextLine());

        MySocialProfile newProfile = new MySocialProfile(name, email, password, classYear);

        homeScreen(newProfile, in);
    }


    //Method that shows the options in the homescreen and runs all the others methods accordingly
    public static void homeScreen(MySocialProfile profile, Scanner in){

        while(true){
            System.out.println();
            System.out.println("+++++++++++++++++++++++++++++++++++++++");
            System.out.println();

            //Remove all the events that are outdated
            if (!profile.upcomingEvents.isEmpty()){
                profile.upcomingEvents.removePastEvents();
            }

            ArrayPriorityQueue upcomingEvents = profile.getUpcomingEvents();
            Deque<String> timelinePosts = profile.getTimelinePosts();
            ArrayList<String> friends = profile.getFriends();

            //Show the next event
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

            String s = in.nextLine();
            if (s.equals("1")){
                System.out.println("Input text that will become the latest item in their timeline: ");
                String newTimeline = in.nextLine();
                profile.timelinePosts.push(newTimeline);
            }else if(s.equals("2")){
                System.out.println("Input the event info");
                newEvent(profile, in);
            }else if(s.equals("3")){
                if (friends.isEmpty()){
                    System.out.println("You have no friend for now");
                }else{
                    System.out.print("Your friends are: ");
                    for (String friendName : friends) {
                        System.out.print(friendName+ "/ ");
                    }
                }
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

    //Method to create a new event. Takes the profile it will store the event to as an attribute
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

        Calendar eventDate = Calendar.getInstance();
        eventDate.set(year, month, day, hour, min);
        String eventString = (month + " " + day + " " + year + " " +  hour + " " +  min + " " +  event);
        Events newEvent = new Events(event, eventDate);
        profile.upcomingEvents.insert(newEvent);

        homeScreen(profile, in);
    }



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
