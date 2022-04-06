package assignment1.register;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Register {

    private final Scanner scanner;
    private final UserObserver userObserver;
    private final List<User> users;

    public Register(){
        scanner = new Scanner(System.in);
        userObserver = new UserObserver();
        users = new ArrayList<>();
        loadUsers();
    }

    public void login(){
        String email = readInput("Please enter email");

        String password = readInput("Please Enter password");

        if (userObserver.verifyLogin(email,password)){
            for (User user:users){
                if (user.getEmail().equals(email)){
                    if (user.isAdmin()){
//                        Admin admin = new Admin(user.getFirstName(),users);
                        break;
                    }
                }
            }
        }else{
            System.out.println("Invalid Login");
            System.out.println("Would you like to create a new Account(Yes | No)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) register();
            else {
                System.out.println("Exiting");
                System.exit(0);
            }
        }
    }
    public void register(){
        int studentNumber  = Integer.parseInt(readInput("Please Enter student Number"));
        String email = readInput("Please Enter email");
        String firstName = readInput("Please Enter first name");
        String lastName = readInput("Please Enter last name");
        String password = readInput("Please Enter password");
        User user = new User(studentNumber,email,firstName,lastName,password);
        users.add(user);
        userObserver.addObserver(user);
        saveUsers();
    }

    private String readInput(String message){
        String input = "";
        while (input.isEmpty()){
            System.out.println(message);
            input = scanner.nextLine();
        }
        return input;
    }

    public void saveUsers(){
        File file = new File("users.txt");
        try {
            if (!file.exists()) file.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            for (User user: users){
                oos.writeObject(user);
            }
            oos.writeObject(null);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers(){
        File file = new File("users.txt");
        try {
            if (!file.exists()){
                file.createNewFile();
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(null);
                oos.flush();
                oos.close();
                return;
            }
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Object read;
            while ((read = ois.readObject()) != null){
                User user = (User) read;
                users.add(user);
                userObserver.addObserver(user);
            }
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (User user:users){
            System.out.println(user.getEmail() +" "+user.getPassword());
        }
    }
}
//observervable and observer relations set
