import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The class that glues room and users class together.
 * Got a has-a-relation with those classes because
 * it contains an array of them. Got the methods
 * that interacts with the users and controls the
 * csv files.
 * @author Yasir
 */
public class Hotel {
    /**
     * ArrayList of the rooms that the hotel contains.
     */
    private ArrayList<Room> rooms;

    /**
     * ArrayList of the users of the hotel management system.
     * Contains both users and the guests.
     */
    private ArrayList<AbstractUser> registeredUsers;

    /**
     * Keeps the user that is currently using the system.
     * On method calls acts based on this field.
     */
    private AbstractUser currentUser;

    /**
     * The file directory of the csv file that holds
     * the records about the rooms.
     */
    private String roomsDir;

    /**
     *  The file directory of the csv file that holds
     *  the record about the users.
     */
    private String usersDir;

    /**
     * if the program is running in testing mode or not.
     */
    private boolean isMainTest;

    /**
     * Main test input stream.
     */
    private ArrayList<String> commandsTest;

    /**
     * Which index is currently needs to be feed to
     * input reader.
     */
    private int commandsTestPos;

    /**
     * Constructor. Calls the readFromCSV method
     * based on the parameters.
     * @param roomsDirC file directory of rooms csv file
     * @param usersDirC file directory of users csv file
     */
    Hotel(String roomsDirC, String usersDirC){
        this.roomsDir = roomsDirC;
        this.usersDir = usersDirC;
        commandsTest = null;
        isMainTest = false;
        commandsTestPos = 0;
        readFromCSV();
    }

    /**
     * Main test constructor.
     * @param roomsDirC file directory of rooms csv file
     * @param usersDirC file directory of users csv file
     * @param inputDirC file directory of inputs txt file. WARNING: This file has to be valid and needs to end the program in natural ways, eventually.
     */
    Hotel(String roomsDirC, String usersDirC, String inputDirC){
        this.roomsDir = roomsDirC;
        this.usersDir = usersDirC;
        commandsTest = new ArrayList<String>();
        isMainTest = true;
        commandsTestPos = 0;
        BufferedReader inputsReader = null;
        String inputLine;
        try{
            inputsReader = new BufferedReader(new FileReader(inputDirC));
            while((inputLine = inputsReader.readLine()) != null){
                commandsTest.add(inputLine);
            }
        } catch (FileNotFoundException fnfe){
            System.out.println("no such file.");
        } catch (IOException ioe){
            ioe.printStackTrace();
        }finally {
            if(inputsReader != null){
                try {
                    inputsReader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        readFromCSV();
    }

    /**
     *  Based on the roomsDir and usersDir fields
     *  opens up 2 csv files and loads their
     *  information to the rooms and
     *  registeredUsers arrays. In case of an exception,
     *  prints out the stack trace.
     */
    private void readFromCSV(){
        rooms = new ArrayList<Room>();
        registeredUsers = new ArrayList<AbstractUser>();
        BufferedReader roomReader = null, userReader = null;
        String roomsLine, usersLine;

        try {
            userReader = new BufferedReader(new FileReader(usersDir));
            while ((usersLine = userReader.readLine()) != null) {
                String[] userData = usersLine.split(",");
                UserType t;
                if(userData[1].equals(new String("RECEPTIONIST")))
                    t = UserType.RECEPTIONIST;
                else t = UserType.GUEST;
                if(t == UserType.RECEPTIONIST){
                    registeredUsers.add(new Receptionist(Integer.parseInt(userData[0]), userData[2], userData[3], userData[4]));
                } else {
                    registeredUsers.add(new Guest(Integer.parseInt(userData[0]), userData[2], userData[3], userData[4]));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (userReader != null) {
                try {
                    userReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            roomReader = new BufferedReader(new FileReader(roomsDir));
            while ((roomsLine = roomReader.readLine()) != null) {
                String[] roomData = roomsLine.split(",");
                boolean isBookedFromFile = false;
                boolean isCheckedFromFile = false;
                AbstractUser ownerOfTheRoomFromFile = null;
                if(roomData[1].equals("1")){
                    isBookedFromFile = true;
                }
                if(roomData[2].equals("1")){
                    isCheckedFromFile = true;
                }
                for(int i=0; i<registeredUsers.size(); i++){
                    if(registeredUsers.get(i).getName().equals(roomData[3])){
                        ownerOfTheRoomFromFile = registeredUsers.get(i);
                    }
                }
                rooms.add(new Room(Integer.parseInt(roomData[0]), isBookedFromFile, isCheckedFromFile, ownerOfTheRoomFromFile));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (roomReader != null) {
                try {
                    roomReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Based on roomsDir and userDir fields,
     * takes the data from registeredUsers arrayList
     * and rooms arrayLists and puts their data into
     * that files at that directory.
     */
    private void writeToCSV(){
        PrintWriter roomWriter = null;
        PrintWriter userWriter = null;
        try {
            roomWriter = new PrintWriter(roomsDir, "UTF-8");
            for (int i=0; i<rooms.size(); i++){
                Integer isBookedInt = new Integer(0);
                Integer isCheckedInt = new Integer(0);
                if(rooms.get(i).getBooked() == true){
                    isBookedInt = 1;
                }
                if(rooms.get(i).getChecked() == true){
                    isCheckedInt = 1;
                }
                String s = new String("");
                s += rooms.get(i).getNumber();
                s += ",";
                s += isBookedInt.toString();
                s += ",";
                s += isCheckedInt.toString();
                s += ",";
                if(rooms.get(i).getOwner() == null){
                    s += "null";
                } else {
                    s += rooms.get(i).getOwner().getName();
                }
                if(i != rooms.size() - 1) {
                    roomWriter.println(s);
                } else {
                    roomWriter.print(s);
                }
            }
        } catch (Exception x){
            x.printStackTrace();
        } finally {
            if(roomWriter != null){
                try{
                    roomWriter.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        try {
            userWriter = new PrintWriter(usersDir, "UTF-8");
            for(int i=0; i<registeredUsers.size(); i++){
                String s = new String ("");
                Integer k = new Integer(0);
                k = registeredUsers.get(i).id;
                s += k.toString();
                s += ",";
                if(registeredUsers.get(i).getType() == UserType.GUEST){
                    s += "GUEST";
                } else {
                    s += "RECEPTIONIST";
                }
                s += ",";
                s += registeredUsers.get(i).getName();
                s += ",";
                s += registeredUsers.get(i).getMail();
                s += ",";
                s += registeredUsers.get(i).getPassword();
                if(i != registeredUsers.size() - 1){
                    userWriter.println(s);
                } else {
                    userWriter.print(s);
                }
            }
        } catch (Exception x){
            x.printStackTrace();
        } finally {
            if(userWriter != null){
                try {
                    userWriter.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Public method that calls the user login system
     * and starts the whole system.
     */
    public void start(){
        userLogin();
    }

    /**
     * Simple login system using users email address
     * and password information, welcomes them with
     * the polymorphic method, welcomeUser.
     */
    private void userLogin(){
        boolean isCorrectLogin = false;
        System.out.println("Please enter your email address and password");
        Scanner sc = new Scanner(System.in);
        String mailInput;
        String passwordInput;
        if(this.isMainTest == false) {
            mailInput = sc.nextLine();
            passwordInput = sc.nextLine();
        } else {
            mailInput = commandsTest.get(commandsTestPos);
            commandsTestPos++;
            passwordInput = commandsTest.get(commandsTestPos);
            commandsTestPos++;
            System.out.printf("%s\n%s\n", mailInput, passwordInput);
        }
        for (int i=0; i<registeredUsers.size(); i++){
            if(registeredUsers.get(i).getMail().equals(mailInput) && registeredUsers.get(i).getPassword().equals(passwordInput)){
                System.out.println(registeredUsers.get(i).welcomeUser());
                isCorrectLogin = true;
                currentUser = registeredUsers.get(i);
                actionsMenu();
            }
        }
        if(!isCorrectLogin){
            System.out.println("Invalid username/password");
            userLogin();
        }
    }

    /**
     * The menu that allows a user to choose
     * one of the four activities or quit.
     */
    private void actionsMenu(){
        System.out.printf("Enter in this screen:\nBOOK to book a room\nCANCELBOOK to cancel a booking" +
                "\nCHECKIN to check in a booked room\nCHECKOUT to check out of a checked in room." +
                "\nEXIT to quit the program.\n");
        Scanner sc = new Scanner(System.in);
        String actionInput;
        if(isMainTest == false) {
            actionInput = sc.nextLine();
        } else {
            actionInput = commandsTest.get(commandsTestPos);
            commandsTestPos++;
            System.out.println(actionInput);
        }
        if(actionInput.equals("BOOK")) {
            roomNumberInputMenu(ActionType.BOOK);
        } else if(actionInput.equals("CANCELBOOK")){
            roomNumberInputMenu(ActionType.CANCELBOOK);
        } else if(actionInput.equals("CHECKIN")){
            roomNumberInputMenu(ActionType.CHECKIN);
        } else if(actionInput.equals("CHECKOUT")) {
            roomNumberInputMenu(ActionType.CHECKOUT);
        } else if(actionInput.equals("EXIT")){
            goodBye();
        } else {
            System.out.println("Invalid Input, please try again.");
            actionsMenu();
        }
    }

    /**
     * The menu that allows a user to select room and
     * it calls a method of that user based on the selected
     * activity and room number. The effects of the activities
     * are coded in the users class.
     * @param at the type of activity selected from the previous menu
     */
    private void roomNumberInputMenu(ActionType at){
        boolean isValidRoom = false;
        System.out.printf("Please enter a room number to Book.\nRoom Numbers:\n");
        Scanner sc = new Scanner(System.in);
        for (int i=0; i<rooms.size(); i++)
            System.out.println(rooms.get(i).getNumber());
        String roomNumberInput;
        if(isMainTest == false) {
            roomNumberInput = sc.nextLine();
        } else {
            roomNumberInput = commandsTest.get(commandsTestPos);
            commandsTestPos++;
            System.out.println(roomNumberInput);
        }
        if(isInteger(roomNumberInput)){
            int roomNumberInputToInt = Integer.parseInt(roomNumberInput);
            for (int i=0; i<rooms.size(); i++){
                if(roomNumberInputToInt == rooms.get(i).getNumber()) {
                    isValidRoom = true;
                    if (at == ActionType.BOOK) {
                        if(currentUser.getType() == UserType.RECEPTIONIST){
                            System.out.println("List of users you can book a room for:");
                            for(int j=0; j<registeredUsers.size(); j++){
                                if(registeredUsers.get(j).getType() == UserType.GUEST) {
                                    System.out.printf("Name: %s\n", registeredUsers.get(j).getName());
                                }
                            }
                            System.out.println("Enter the name of a guest to register them.");
                            String selectedUserInput;
                            if(isMainTest == false) {
                                selectedUserInput = sc.nextLine();
                            } else {
                                selectedUserInput = commandsTest.get(commandsTestPos);
                                commandsTestPos++;
                                System.out.println(selectedUserInput);
                            }
                            boolean isSuchUser = false;
                            for(int j=0; j<registeredUsers.size(); j++){
                                if(registeredUsers.get(j).getName().equals(selectedUserInput)){
                                    currentUser.bookRoom(rooms.get(i), registeredUsers.get(j));
                                    writeToCSV();
                                    isSuchUser = true;
                                }
                            }
                            if(isSuchUser == false){
                                System.out.println("No such user, try again.");
                            }
                        } else {
                            currentUser.bookRoom(rooms.get(i), currentUser);
                            writeToCSV();
                        }
                    } else if (at == ActionType.CANCELBOOK) {
                        currentUser.cancelBook(rooms.get(i));
                        writeToCSV();
                    } else if (at == ActionType.CHECKIN) {
                        currentUser.checkIn(rooms.get(i));
                        writeToCSV();
                    } else if (at == ActionType.CHECKOUT) {
                        currentUser.checkOut(rooms.get(i));
                        writeToCSV();
                    }

                    // Doesn't break until a valid input comes in
                    boolean isNotValidInput = true;
                    while (isNotValidInput) {
                        System.out.printf("Enter:\nMENU to go back to the menu.\nLOGOUT to log out of the system\nEXIT to close the program.\n");
                        String backMenuInput;
                        if(isMainTest == false) {
                            backMenuInput = sc.nextLine();
                        } else {
                            backMenuInput = commandsTest.get(commandsTestPos);
                            commandsTestPos++;
                            System.out.println(backMenuInput);
                        }
                        if(backMenuInput.equals("MENU")){
                            actionsMenu();
                            isNotValidInput = false;
                        } else if(backMenuInput.equals("LOGOUT")){
                            userLogin();
                            isNotValidInput = false;
                        } else if(backMenuInput.equals("EXIT")){
                            goodBye();
                            isNotValidInput = false;
                        } else {
                            System.out.println("Invalid Input, try again.");
                        }
                    }
                }
            }
            if(isValidRoom == false) {
                System.out.println("No such room, please start from the beginning.");
                roomNumberInputMenu(at);
            }
        } else {
            System.out.println("Invalid room number, please start from the beginning.");
            roomNumberInputMenu(at);
        }
    }

    /**
     * Simple goodbye message, same for all of the users.
     */
    private void goodBye(){
        System.out.println("Thanks for using hotel automation system, have a nice day.");
    }

    /**
     * Utility method for checking a given input
     * or any string if it is numeric.
     * @param s string that needs to be checked
     * @return true, if the string is numeric, false otherwise
     */
    private boolean isInteger(String s){
        try{
            int d = Integer.parseInt(s);
        } catch (NumberFormatException x){
            return false;
        }
        return true;
    }
}
