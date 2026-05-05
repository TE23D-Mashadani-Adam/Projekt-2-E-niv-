package UserManagementPackage;

import java.util.ArrayList;
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
    private ArrayList<SuspendedUsers> allSuspendedUsers;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void SortUserByName() {
        System.out.println(ApiClient.getData("users"));
    }

    // Hittar en användare och skickar den tillbaka som en objekt
    public Users findUser(String email) {
        String jsonData = ApiClient.getData("users");
        Type userListType = new TypeToken<ArrayList<Users>>() {
        }.getType();
        ArrayList<Users> temporaryUserList = gson.fromJson(jsonData, userListType);

        allUsersMap.clear();
        if (temporaryUserList != null) {
            for (Users user : temporaryUserList) {
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
