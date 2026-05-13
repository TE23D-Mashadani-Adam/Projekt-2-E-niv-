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

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import adam.mashadani.ApiClient;
import kong.unirest.UnirestException;

public class LoanManager {

    private String suspendedUserPath = "suspended";
    private Type suspendedUserListType = new TypeToken<ArrayList<SuspendedUsers>>() {
    }.getType();
    private String loansFilePath = "active_loans.txt";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String checkUserStatus(Users user) {
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

    public void registerLoan(Users user, Publications item, String path) {
        if (item != null) {
            if (!item.isAvailabe()) {
                System.out.println(item.getTypeByName() + " är redan utlånad!");
                return;
            }

            String item_id = item.getId();
            item.setAvailable(false);
            String putRequestMessage = ApiClient.putRequest(path, item_id, item);
            if (putRequestMessage.equals("Data uppdaterad")) {

                if (checkUserStatus(user).equals("active")) {
                    String loanBrief = user.getName() + " har lånat " + item.getTitle() + "\n";
                    try {
                        Path fil_sökväg = Paths.get(loansFilePath);
                        Files.writeString(fil_sökväg, loanBrief, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        System.out.println("Kunde inte spara i filen: " + e.getLocalizedMessage());
                    }
                } else {
                    System.out.println(user.getName() + " är avstängd och kan inte låna tyvärr!");
                    return;
                }
            } else {
                System.out.println("Kunde inte uppdatera servern, vänligen försök senare");
                return;
            }
            System.out.println("Lånet har registrerats!");
        }

    }

    public void endLoan(Users user, Publications item, String path){
        if (item == null || user == null) return;

        try{
            Path fil_sökväg = Paths.get(loansFilePath);

            if (Files.exists(fil_sökväg)) {
                List<String> alla_lån = Files.readAllLines(fil_sökväg);
                String sökSträng = user.getName() + " har lånat " + item.getTitle();

                boolean strängBorttagen = alla_lån.removeIf(rad -> rad.contains(sökSträng));

                if (strängBorttagen) {
                    Files.write(fil_sökväg, alla_lån);
                    item.setAvailable(true);
                    ApiClient.putRequest(path, item.getId(), item);
                }

                System.out.println("Lånet har avslutats och tagits bort från servern");
            }else{
                System.out.println("Kunde inte hitta lånet i filen");
            }
        }catch(IOException e){
            System.out.println("Fel vid upphämtning av filen: " + e.getLocalizedMessage());
        }
    }
}
