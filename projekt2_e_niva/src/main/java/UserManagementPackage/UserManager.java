package UserManagementPackage;

import java.util.ArrayList;

import adam.mashadani.ApiClient;
import kong.unirest.UnirestException;

public class UserManager {
    private ArrayList<Users> allUsers;
    private ArrayList<SuspendedUsers> allSuspendedUsers;

    public void SortUserByName(){
        System.out.println(ApiClient.getData("users"));
    }

    public void checkUser(String email){

    }

    public void addUser(String name, String email){

    }

    public void suspendUser(String email, String reason){

    }
}
