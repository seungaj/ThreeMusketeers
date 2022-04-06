package assignment1.register;

import java.util.ArrayList;
import java.util.List;

public class UserObserver {

    private final List<Observer> observers = new ArrayList<>();

    public boolean verifyLogin(String email, String password){
        for (Observer observer:observers){
            if (observer.verifyLogin(email,password)) return true;
        }
        return false;
    }


    public void addObserver(Observer observer){
        observers.add(observer);
    }
}
