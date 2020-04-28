import java.util.*;
import java.io.*;

public class mainClass {


    public static MySocialProfile readFromFile(){
        //If there is a loaded profile else error
        MySocialProfile oldProfile;
        try {
            Scanner lineScanner = new Scanner(new FileInputStream("mysocialprofile.txt"));

            while (lineScanner.hasNext()) { //while more of the input file is still available for reading
                String name = lineScanner.nextLine();  //reads an entire line of input
                String email = lineScanner.nextLine();
                String pass = lineScanner.nextLine();
                int year = Integer.parseInt(lineScanner.nextLine());

                String eventLine = lineScanner.nextLine();
                String[] event = eventLine.split(", ");
                Events newEvent = new Events(40);
                for (String evnt: event){
                    newEvent.insert(evnt);
                }
//                for (int i=0; i<event.length; i++){
//                    if (event[i] !=null){
//                        newEvent.insert(event[i]);;
//                        i++;
//                    }
//                }

                String wallMsgs = lineScanner.nextLine();
                String[] timeL = wallMsgs.split(", ");
                Deque<String> timeLine = new ArrayDeque<>();
                for (String msg: timeL){
                    timeLine.push(msg);
                }

                String friendLine = lineScanner.nextLine();
                String[] friend = friendLine.split(", ");
                ArrayList<String> friends = new ArrayList<>();
                for (String frnd: friend){
                    friends.add(frnd);
                }

                oldProfile = new MySocialProfile(name, email, pass, year, newEvent, timeLine, friends);
                return oldProfile;
            }

        } catch(FileNotFoundException ex) {
            System.out.println("File not Found");
            System.exit(0);
        }


        return null;
    }


    public static void saveToFile(MySocialProfile profile){


        Events upcomingEvents = profile.getUpcomingEvents();
        Deque<String> timelinePosts = profile.getTimelinePosts();

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
            for (i=0; i< profile.upcomingEvents.size(); i++){
                if (profile.upcomingEvents.A[i] != null){
                    bufWriter.write(profile.upcomingEvents.A[i] + ", ");
                }
            }
            bufWriter.write(profile.upcomingEvents.A[i]);
            bufWriter.newLine();

            int j;
            for (j=timelinePosts.size(); j>1; j--){
                bufWriter.write(profile.timelinePosts.pollLast() + ", ");
            }
            bufWriter.write(timelinePosts.pollLast());
            bufWriter.newLine();

            int k;
            for (k=0; k<profile.friends.size()-1; k++){
                bufWriter.write(profile.friends.get(k)+ ", ");
            }
            bufWriter.write(profile.friends.get(k));


            bufWriter.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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


    public static void homeScreen(MySocialProfile profile, Scanner in){

        while(true){
            System.out.println();
            System.out.println("+++++++++++++++++++++++++++++++++++++++");
            System.out.println();


            Events upcomingEvents = profile.getUpcomingEvents();
            Deque<String> timelinePosts = profile.getTimelinePosts();

//            System.out.println("Before remove past *********");
//            upcomingEvents.printAllEvents();
//            upcomingEvents.printAllEventsI();
//
//            if (!upcomingEvents.isEmpty()){
//                upcomingEvents.removePastEvents();
//            }
//            System.out.println("After remove past *********");
//            upcomingEvents.printAllEvents();
//            upcomingEvents.printAllEventsI();

            //Show the timeline and events
            if (upcomingEvents.isEmpty()){
                System.out.println("You have no upcoming Event");
//                System.out.println("Inside empty statement *********");
//                upcomingEvents.printAllEvents();
//                System.out.println(upcomingEvents.getMin());
            }else{
                //**Print out the next event
                System.out.print("Your next events is: ");
//                System.out.println("Inside not empty statement *********");
//                upcomingEvents.printAllEvents();
                System.out.println(upcomingEvents.getMin());
            }


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

            if (!upcomingEvents.isEmpty()){
                System.out.println("The rest of your events are: ");
                upcomingEvents.printAllEvents();
            }
            System.out.println("+++++++++++++++++++++++++++++++++++++++");
            System.out.println();


            //Secondary list of options
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
                if (profile.friends.isEmpty()){
                    System.out.println("You have no friend for now");
                }else{
                    System.out.print("Your friends are: ");
                    for (String friendName : profile.friends) {
                        System.out.print(friendName+ "/ ");
                    }
                }
            }else if(s.equals("4")){
                System.out.print("Input the email of your friend: ");
                //**Check the validity of the email
                String friendEmail = in.nextLine();
                boolean found = false;
                for (String emails : profile.friends) {
                    if (friendEmail.equals(emails)){
                        profile.friends.remove(emails);
                        found = true;
                        break;
                    }
                }
                if (!found){
                    profile.friends.add(friendEmail);
                }
            }else if(s.equals("5")){
                //Save the Profile to the txt file
                saveToFile(profile);
                System.out.println("Thank you for using our App");

//                for (int i=0; i<timelinePosts.size()+1; i++){
//                    System.out.println(timelinePosts.poll());
//                }

                System.exit(0);
            }else{
                System.out.print("Input not recognized. Please enter your input again: ");
                continue;
            }
        }


    }

    public static void newEvent(MySocialProfile profile, Scanner in){
        /*create calendar object to store user inputted time*/
        Calendar userCal = Calendar.getInstance();

        /*get user inputted date and time*/
        int month, day, year, hour, min;
        System.out.print("Please enter the month MM: ");
        month = Integer.parseInt(in.nextLine());
        System.out.print("Please enter the day DD: ");
        day = Integer.parseInt(in.nextLine());
        System.out.print("Please enter the year YYYY: ");
        year = Integer.parseInt(in.nextLine());
        System.out.print("Please enter the hour of the day (0-23): ");
        hour = Integer.parseInt(in.nextLine());
        System.out.print("Please enter the minute of the hour (00-59): ");
        min = Integer.parseInt(in.nextLine());
        System.out.print("Please enter the event: ");
        String action = in.nextLine();

        System.out.println("\nYou entered: " + month + "/" + day + "/" + year + " at " + hour + ":" + min);

        String event = (month + " " + day + " " + year + " " +  hour + " " +  min + " " +  action);
        profile.upcomingEvents.insert(event);

        homeScreen(profile, in);
    }



    public static void main(String[] args) {

        //Read the profile from txt if it exists
        MySocialProfile savedProfile = readFromFile();

        //Prompt the user for choices
        System.out.println("Press 1 to Create a new account ");
        System.out.println("Press 2 to Load an existing profile ");
        System.out.println("Press 3 to Exit the  program ");

        Scanner in = new Scanner(System.in);
        System.out.print("Enter your input here: ");

        while(true){
            String s = in.nextLine();
            if (s.equals("1")){
                //Prompt the user to create a new Account
                createNewProfile(in);
            }else if(s.equals("2")){
                //**If profile exists load it else error
                homeScreen(savedProfile, in);
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
