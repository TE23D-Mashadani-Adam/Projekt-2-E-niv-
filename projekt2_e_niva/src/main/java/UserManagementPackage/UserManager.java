package UserManagementPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import LibraryManagementPackage.Books;
import adam.mashadani.ApiClient;
import kong.unirest.UnirestException;

public class UserManager {
    private Map<String, Users> allUsersMap = new HashMap<>();
    private ArrayList<Users> allUserArrayList = new ArrayList<>();
    private ArrayList<SuspendedUsers> allSuspendedUsers;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Type userListType = new TypeToken<ArrayList<Users>>() {
        }.getType();

    // Hjälpmetod, hämtar data från server och lagrar i arraylistan i java format
    public void getUsers(String path, Type t, ArrayList list){
        String jsonData = ApiClient.getData(path);
        list.clear(); // Säger till att den blir tom igen
        list = gson.fromJson(jsonData, t);
    }

    public void ShowUsersSorted() {
        getUsers("users", userListType, allUserArrayList);
        Collections.sort(allUserArrayList);
        for (Users user : allUserArrayList) {
            System.out.println(user.getName());
        }
    }

    public void ShowSuspendedUsers(){
        Type suspendedListType = new TypeToken<ArrayList<SuspendedUsers>>() {
        }.getType();
        getUsers("suspended", suspendedListType, allSuspendedUsers);
        for (SuspendedUsers sUser : allSuspendedUsers) {
            System.out.println("User ID: " + sUser.getUserId() + " Reason: " + sUser.getReason());
        }
    }

    // Hittar en användare och skickar den tillbaka som en objekt
    public Users findUser(String email) {
        getUsers("users", userListType, allUserArrayList);
        if (allUserArrayList != null) {
            for (Users user : allUserArrayList) {
                allUsersMap.put(user.getEmail(), user);
            }
        }

        if (allUsersMap.containsKey(email)) {
            return allUsersMap.get(email);
        } else {
            return null;
        }

    }

    public String addUser(String name, String email) {
        Users user = new Users(name, email);
        return ApiClient.postData("users", user);
    }

    public String suspendUser(String email, String reason) {
        Users user = findUser(email);
        if (user != null) {
            SuspendedUsers supendedUser = new SuspendedUsers(user.getId(), reason);
            return ApiClient.postData("suspended", supendedUser);
        }else{
            return "Användaren hittades inte, kontrollera att du skrev rätt email!";
        }

    }
}
