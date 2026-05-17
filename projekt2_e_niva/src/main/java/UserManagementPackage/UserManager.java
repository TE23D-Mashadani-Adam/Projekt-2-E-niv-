package UserManagementPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import LibraryManagementPackage.PublicationChildClasses.Books;

import java.lang.reflect.Type;

import adam.mashadani.ApiClient;
import kong.unirest.UnirestException;

public class UserManager {
    private Map<String, Users> allUsersMap = new HashMap<>();
    private ArrayList<Users> allUserArrayList = new ArrayList<>();
    private ArrayList<SuspendedUsers> allSuspendedUsers = new ArrayList<>();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Type userListType = new TypeToken<ArrayList<Users>>() {
    }.getType();

    private final String userPath = "users";
    private final String suspendedPath = "suspended";

    public void ShowUsersSorted() {
        String jsonData = ApiClient.getData(userPath);
        ApiClient.convertToJavaFormat(jsonData, userListType, allUserArrayList);
        Collections.sort(allUserArrayList);
        for (Users user : allUserArrayList) {
            System.out.println(user.getName());
        }
    }

    public void ShowSuspendedUsers() {
        Type suspendedListType = new TypeToken<ArrayList<SuspendedUsers>>() {
        }.getType();
        String jsonData = ApiClient.getData(suspendedPath);
        ApiClient.convertToJavaFormat(jsonData, suspendedListType, allSuspendedUsers);
        for (SuspendedUsers sUser : allSuspendedUsers) {
            System.out.println("User ID: " + sUser.getUserId() + " Reason: " + sUser.getReason());
        }
    }

    // Hittar en användare och skickar den tillbaka som en objekt
    public Users findUser(String email) {
        String jsonData = ApiClient.getData(userPath);
        ApiClient.convertToJavaFormat(jsonData, userListType, allUserArrayList);
        if (allUserArrayList != null) {
            for (Users user : allUserArrayList) {
                allUsersMap.put(user.getEmail(), user);
            }
        } else {
            System.out.println("Hittade inga användare");
        }
        if (allUsersMap.containsKey(email) && allUserArrayList != null) {
            return allUsersMap.get(email);
        } else if (!allUsersMap.containsKey(email) && allUserArrayList != null && !allUserArrayList.isEmpty()) {
            System.out.println("Hittade inte skriven email på servern, kontrollera gärna stavning");
            return null;
        } else {
            return null;
        }
    }

    public String ShowUserByName(String email) {
        Users user = findUser(email);
        if (user != null) {
            return user.toString();
        } else {
            return "Ej hittad";
        }
    }

    public String addUser(String name, String email) {
        Users user = new Users(name, email);
        String responseMessage = ApiClient.postData("users", user);
        if (responseMessage == "Data skickad") {
            return "Användare skapad!";
        } else {
            return responseMessage;
        }
    }

    public String suspendUser(String email, String reason) {

        Users user = findUser(email);
        if (user != null) {
            SuspendedUsers supendedUser = new SuspendedUsers(user.getId(), reason);
            String responseMessage = ApiClient.postData("suspended", supendedUser);
            if (responseMessage == "Data skickad!") {
                return "Användare har blivit avstängd!";
            } else {
                return responseMessage;
            }
        } else {
            return "";
        }

    }

    public void activateSuspendedUser(String id) {
        String responseMessage = ApiClient.deleteData("suspended", id);
        if (responseMessage == "Data deleted") {
            System.out.println("Användare aktiverad");
        } else {
            System.out.println(responseMessage);
        }
    }

    public void deleteUser(String id) {
        String responseMessage = ApiClient.deleteData("users", id);
        if (responseMessage == "Data deleted") {
            System.out.println("Användare togs bort!");
        } else {
            System.out.println(responseMessage);
        }
    }
}
