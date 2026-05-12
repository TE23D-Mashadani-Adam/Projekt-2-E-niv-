package LibraryManagementPackage.LoanFromLibraryManagement;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import LibraryManagementPackage.PublicationChildClasses.Books;
import LibraryManagementPackage.PublicationChildClasses.Magazines;
import LibraryManagementPackage.Publications;
import UserManagementPackage.SuspendedUsers;
import UserManagementPackage.Users;

import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import adam.mashadani.ApiClient;
import kong.unirest.UnirestException;


public class LoanManager {

    private String suspendedUserPath = "suspended";
    private Type suspendedUserListType = new TypeToken<ArrayList<SuspendedUsers>>(){}.getType();
    private String loansFilePath = "active_loans.txt";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

 
    public String checkUserStatus(Users user){
        String jsonData = ApiClient.getData(suspendedUserPath);
        ArrayList<SuspendedUsers> suspendedUsersList = new ArrayList<>();
        ApiClient.convertToJavaFormat(suspendedUserPath, suspendedUserListType, suspendedUsersList);
        String userStatus = "active";

        
        for (SuspendedUsers suspendedUser : suspendedUsersList) {
            if (suspendedUser.getUserId().equals(user.getId())) {
                userStatus = "suspended";
            }
        }

        return userStatus;
    }

    public void registerLoan(Users user, Publications item){
        if(item != null){
        if (!item.isAvailabe()) {
            System.out.println(item.getTypeByName() + " är redan utlånad!");
            return;
        }

        if (checkUserStatus(user).equals("active")) {
            String loanBrief = user.getName() + " har lånat " + item.getTitle();
            try {
                Path fil_sökväg = Paths.get(loansFilePath);
                Files.writeString(fil_sökväg, loanBrief);
            }catch(IOException e){
                System.out.println("Kunde inte spara filen: " + e.getLocalizedMessage());
            }
        }else{
            System.out.println(user.getName() + " är avstängd och kan inte låna tyvärr!");
            return;
        }
        item.setAvailable(false); //Ska ändra senare 
        System.out.println("Lånet har registrerats!");
    }

    }
}
