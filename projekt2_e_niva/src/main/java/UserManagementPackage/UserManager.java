package UserManagementPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import LibraryManagementPackage.PublicationChildClasses.Book;

import java.lang.reflect.Type;
import adam.mashadani.ApiClient;

import kong.unirest.UnirestException;

/**
 * Hanterar administrationen av bibliotekets användare och avstängningar.
 * <p>
 * Denna klass ansvarar för att kommunicera med databasen/servern via {@link ApiClient}
 * för att lägga till, hämta, söka efter, radera, spärra (avstänga) och aktivera användare.
 * </p>
 *
 * @author Adam Mashadani
 * @version 1.0
 * @since 2026
 */
public class UserManager {
    
    /** Snabbregister (Map) som mappar en användares e-postadress till dess motsvarande {@link User}-objekt. */
    private Map<String, User> allUsersMap = new HashMap<>();
    
    /** Temporär lista som lagrar alla aktiva användare vid synkronisering med servern. */
    private ArrayList<User> allUserArrayList = new ArrayList<>();
    
    /** Temporär lista som lagrar alla avstängda användare vid synkronisering med servern. */
    private ArrayList<SuspendedUser> allSuspendedUsers = new ArrayList<>();
    
    /** Gson-instans konfigurerad med snygg utskrift (pretty printing) för JSON-hantering. */
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    /** Typdefinition för deserialisering av en lista med {@link User} via Gson. */
    private Type userListType = new TypeToken<ArrayList<User>>() {}.getType();

    /** API-sökväg (endpoint) för resurser av typen användare. */
    private final String userPath = "users";
    
    /** API-sökväg (endpoint) för resurser av typen avstängda användare. */
    private final String suspendedPath = "suspended";

    /**
     * Standardkonstruktor för UserManager.
     */
    public UserManager() {
        // Standardkonstruktor
    }

    /**
     * Hämtar alla användare från servern, sorterar dem i bokstavsordning (enligt klassen Users interna jämförelselogik)
     * och skriver ut deras namn i konsolen.
     */
    public void ShowUsersSorted() {
        String jsonData = ApiClient.getData(userPath);
        ApiClient.convertToJavaFormat(jsonData, userListType, allUserArrayList);
        HashSet<User> uniqueUsers = new HashSet<>(allUserArrayList); //Rensar dubletter
        allUserArrayList.clear();
        allUserArrayList.addAll(uniqueUsers);
        Collections.sort(allUserArrayList);
        for (User user : allUserArrayList) {
            System.out.println(user.getName());
        }
    }

    /**
     * Hämtar listan över alla avstängda användare från servern och skriver ut deras 
     * användar-ID samt anledningen till avstängningen i konsolen.
     */
    public void ShowSuspendedUsers() {
        Type suspendedListType = new TypeToken<ArrayList<SuspendedUser>>() {}.getType();
        String jsonData = ApiClient.getData(suspendedPath);
        ApiClient.convertToJavaFormat(jsonData, suspendedListType, allSuspendedUsers);
        for (SuspendedUser sUser : allSuspendedUsers) {
            System.out.println("User ID: " + sUser.getUserId() + " Reason: " + sUser.getReason());
        }
    }

    /**
     * Söker efter en specifik användare på servern baserat på dennes e-postadress.
     * Metoden uppdaterar det interna registret med aktuella användare vid sökning.
     *
     * @param email e-postadressen på den användare som eftersöks.
     * @return det matchande {@link User}-objektet om det hittas, annars {@code null}.
     */
    // Hittar en användare och skickar den tillbaka som en objekt
    public User findUser(String email) {
        String jsonData = ApiClient.getData(userPath);
        ApiClient.convertToJavaFormat(jsonData, userListType, allUserArrayList);
        if (allUserArrayList != null) {
            for (User user : allUserArrayList) {
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

    /**
     * Söker efter en användare via e-postadress och returnerar användarens detaljer som en textsträng.
     *
     * @param email e-postadressen för den användare som ska visas.
     * @return en strängrepresentation av användaren om den hittas, annars "Ej hittad".
     */
    public String ShowUserByName(String email) {
        User user = findUser(email);
        if (user != null) {
            return user.toString();
        } else {
            return "Ej hittad";
        }
    }

    /**
     * Registrerar och skapar en ny användare i bibliotekssystemet samt skickar informationen till servern.
     *
     * @param name  användarens fullständiga namn.
     * @param email användarens unika e-postadress.
     * @return ett statusmeddelande ("Användare skapad!" vid framgång, annars felmeddelande från API:et).
     */
    public String addUser(String name, String email) {
        User user = new User(name, email);
        String responseMessage = ApiClient.postData("users", user);
        if (responseMessage == "Data skickad") {
            return "Användare skapad!";
        } else {
            return responseMessage;
        }
    }

    /**
     * Stänger av (spärrar) en användare från bibliotekssystemet genom att leta upp 
     * användaren via dess e-postadress och registrera en avstängningsorsak på servern.
     *
     * @param email  e-postadressen för den användare som ska stängas av.
     * @param reason anledningen till avstängningen (t.ex. obetalda avgifter eller sena återlämningar).
     * @return ett statusmeddelande som anger hur operationen gick, eller en tom sträng om användaren saknas.
     */
    public String suspendUser(String email, String reason) {

        User user = findUser(email);
        if (user != null) {
            SuspendedUser supendedUser = new SuspendedUser(user.getId(), reason);
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

    /**
     * Aktiverar en avstängd användare igen genom att ta bort avstängningsposten 
     * från servern baserat på det unika avstängnings-ID:t.
     *
     * @param id det unika ID-numret för avstängningsposten som ska tas bort.
     */
    public void activateSuspendedUser(String id) {
        String responseMessage = ApiClient.deleteData("suspended", id);
        if (responseMessage == "Data deleted") {
            System.out.println("Användare aktiverad");
        } else {
            System.out.println(responseMessage);
        }
    }

    /**
     * Raderar en användarprofil permanent från systemet baserat på användarens unika ID.
     *
     * @param id det unika ID-numret på den användare som ska tas bort.
     */
    public void deleteUser(String id) {
        String responseMessage = ApiClient.deleteData("users", id);
        if (responseMessage == "Data deleted") {
            System.out.println("Användare togs bort!");
        } else {
            System.out.println(responseMessage);
        }
    }
}