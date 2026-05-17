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

    private ArrayList<Loan> loans = new ArrayList<>();
    private Type loanListType = new TypeToken<ArrayList<Loan>>() {
    }.getType();

    //Laddar upp alla låner från filen
    public void loadLoansFromFile() {
        try {
            Path fil_sökväg = Paths.get(loansFilePath);
            if (Files.exists(fil_sökväg) && Files.size(fil_sökväg) > 0) {
                String json_innehåll = Files.readString(fil_sökväg);
                this.loans = gson.fromJson(json_innehåll, loanListType);
            } else {
                this.loans = new ArrayList<>();
            }
        } catch (IOException e) {
            System.out.println("Kunde inte läsa in lån vid start: " + e.getMessage());
            this.loans = new ArrayList<>(); // Säkra att listan inte är null
        }
    }

    public String checkUserStatus(Users user) {
        String jsonData = ApiClient.getData(suspendedUserPath);
        ArrayList<SuspendedUsers> suspendedUsersList = new ArrayList<>();
        ApiClient.convertToJavaFormat(jsonData, suspendedUserListType, suspendedUsersList);
        String userStatus = "active";

        for (SuspendedUsers suspendedUser : suspendedUsersList) {
            if (suspendedUser.getUserId().equals(user.getId())) {
                userStatus = "suspended";
            }
        }

        return userStatus;
    }

    public void registerLoan(Users user, Publications item, String path) {
        if (item != null && user != null) {
            if (!item.isAvailabe()) {
                System.out.println(item.getTitle() + " är redan utlånad!");
                return;
            }

            String item_id = item.getId();
            item.setAvailable(false);
            String putRequestMessage = ApiClient.putRequest(path, item_id, item);
            if (putRequestMessage.equals("Data uppdaterad")) {

                if (checkUserStatus(user).equals("active")) {
                    Loan newLoan = new Loan(item, user);
                    loans.add(newLoan);
                    String loansInJson = gson.toJson(loans);

                    try {
                        Path fil_sökväg = Paths.get(loansFilePath);
                        Files.writeString(fil_sökväg, loansInJson);
                    } catch (IOException e) {
                        System.out.println("Kunde inte spara i filen: " + e.getStackTrace());
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

    public void endLoan(Users user, Publications item, String path) {
        if (item == null || user == null)
            return;

        try {
            Path fil_sökväg = Paths.get(loansFilePath);

            if (Files.exists(fil_sökväg)) {

                String json_innehåll = Files.readString(fil_sökväg);
                loans = gson.fromJson(json_innehåll, loanListType);
                int previousLoansSize = loans.size();
                loans.removeIf(loan -> loan.getUser().getId().equals(user.getId()) &&
                        loan.getBorrowedPublication().getId().equals(item.getId()));

                if (loans.size() < previousLoansSize) {
                    String updatedLoanList = gson.toJson(loans);
                    Files.writeString(fil_sökväg, updatedLoanList);

                    System.out.println("Lånet har avslutats!");
                } else {
                    System.out.println("Lånet hittades inte, kontrollera att du skrev rätt namn på användare");
                }

            } else {
                System.out.println("Kunde inte hitta lånefilen, vänligen försök senare");
            }
        } catch (IOException e) {
            System.out.println("Fel vid upphämtning av filen: " + e.getStackTrace());
        }
    }

    public void showActiveLoans() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(loansFilePath));
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Kunde inte hämta filen, fel: " + e.getStackTrace());
        }
    }

}
