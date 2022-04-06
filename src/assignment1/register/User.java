package assignment1.register;

import java.io.Serializable;

public class User implements Observer, Serializable {

    private int studentNumber;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private UserActivity userActivity;
    private boolean isAdmin;

    public User(int studentNumber, String email, String firstName, String lastName, String password) {
        this.studentNumber = studentNumber;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userActivity = new UserActivity();
    }

    public User() {
        studentNumber = 0;
        email  = "NAN";
        firstName = "NAN";
        lastName = "NAN";
        password = "NAN";
        userActivity = new UserActivity();
    }


    public boolean verifyLogin(String email,String password){
        return email.equals(this.email) & password.equals(this.password);
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public UserActivity getUserActivity() {
        return userActivity;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
