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

import LibraryManagementPackage.PublicationChildClasses.Book;
import LibraryManagementPackage.PublicationChildClasses.Magazine;
import LibraryManagementPackage.Publication;
import UserManagementPackage.SuspendedUser;
import UserManagementPackage.User;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import adam.mashadani.ApiClient;
import kong.unirest.UnirestException;

/**
 * Hanterar registrering, återlämning och spårning av bibliotekets utlåningar.
 * <p>
 * Denna klass ansvarar för att kontrollera användarnas avstängningsstatus via API:et,
 * uppdatera publikationers tillgänglighet på servern, samt att spara och ladda aktiva 
 * lån lokalt i en textfil i JSON-format.
 * </p>
 *
 * @author Adam Mashadani
 * @version 1.0
 * @since 2026
 */
public class LoanManager {

    /** API-sökväg (endpoint) för att hämta eller hantera avstängda användare. */
    private String suspendedUserPath = "suspended";
    
    /** Typdefinition för deserialisering av en lista med {@link SuspendedUser} via Gson. */
    private Type suspendedUserListType = new TypeToken<ArrayList<SuspendedUser>>() {}.getType();
    
    /** Filnamnet på den lokala textfil där aktiva lån sparas persistence-mässigt. */
    private String loansFilePath = "active_loans.txt";
    
    /** Gson-instans konfigurerad med snygg utskrift (pretty printing) för JSON-hantering. */
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /** Den interna listan över aktuella aktiva lån som är inlästa i systemet. */
    private ArrayList<Loan> loans = new ArrayList<>();
    
    /** Typdefinition för deserialisering av en lista med {@link Loan}-objekt via Gson. */
    private Type loanListType = new TypeToken<ArrayList<Loan>>() {}.getType();

    /**
     * Standardkonstruktor för LoanManager.
     */
    public LoanManager() {
        // Standardkonstruktor
    }

    /**
     * Läser in sparade lån från den lokala textfilen vid programstart.
     * <p>
     * Om filen existerar och inte är tom, deserialiseras JSON-innehållet till listan över lån.
     * Vid fel eller om filen saknas initieras en tom lista för att förhindra NullPointerException.
     * </p>
     */
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

    /**
     * Kontrollerar om en specifik användare är avstängd genom att hämta spärrlistan från servern.
     *
     * @param user den användare {@link User} vars status ska kontrolleras.
     * @return en sträng som representerar användarens status: {@code "suspended"} om användaren 
     *         finns i avstängningsregistret, annars {@code "active"}.
     */
    private String checkUserStatus(User user) {
        String jsonData = ApiClient.getData(suspendedUserPath);
        ArrayList<SuspendedUser> suspendedUsersList = new ArrayList<>();
        ApiClient.convertToJavaFormat(jsonData, suspendedUserListType, suspendedUsersList);
        String userStatus = "active";

        for (SuspendedUser suspendedUser : suspendedUsersList) {
            if (suspendedUser.getUserId().equals(user.getId())) {
                userStatus = "suspended";
            }
        }

        return userStatus;
    }

    /**
     * Registrerar ett nytt lån för en användare om både användaren och publikationen är giltiga.
     * <p>
     * Metoden kontrollerar först att mediet är tillgängligt, sätter det till utlånat på servern, 
     * verifierar att användaren inte är avstängd, och sparar slutligen det nya låneobjektet 
     * i den lokala textfilen.
     * </p>
     *
     * @param user den användare som vill låna.
     * @param item den publikation {@link Publication} som ska lånas ut (t.ex. en bok eller tidning).
     * @param path API-sökvägen för publikationstypen (t.ex. "books" eller "magazines") som ska uppdateras.
     */
    public void registerLoan(User user, Publication item, String path) {
        if (item != null && user != null) {
            if (!item.isAvailable()) {
                System.out.println(item.getTitle() + " är redan utlånad!");
                return;
            }

            String item_id = item.getId();
            item.setAvailable(false);
            String putRequestMessage = ApiClient.putRequest(path, item_id, item);
            if (putRequestMessage.equals("Data uppdaterad")) {

                if (checkUserStatus(user).equals("active")) {
                    Loan newLoan = new Loan(item.getTitle(), user);
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

    /**
     * Avslutar och återlämnar ett aktivt lån från systemet.
     * <p>
     * Metoden läser in den aktuella lånefilen, letar upp matchningen mellan användarens ID och 
     * publikationens titel, raderar lånet från listan samt skriver tillbaka förändringen till filen. 
     * Slutligen markeras publikationen återigen som tillgänglig (true) på servern.
     * </p>
     *
     * @param user den användare som återlämnar publikationen.
     * @param item den publikation {@link Publication} som lämnas tillbaka.
     * @param path API-sökvägen för publikationstypen som ska uppdateras på servern.
     */
    public void endLoan(User user, Publication item, String path) {
        if (item == null || user == null)
            return;

        try {
            Path fil_sökväg = Paths.get(loansFilePath);

            if (Files.exists(fil_sökväg)) {

                String json_innehåll = Files.readString(fil_sökväg);
                loans = gson.fromJson(json_innehåll, loanListType);
                int previousLoansSize = loans.size();
                loans.removeIf(loan -> loan.getUser().getId().equals(user.getId()) &&
                        loan.getBorrowedPublicationName().equals(item.getTitle()));

                if (loans.size() < previousLoansSize) {
                    String updatedLoanList = gson.toJson(loans);
                    Files.writeString(fil_sökväg, updatedLoanList);
                    item.setAvailable(true);
                    ApiClient.putRequest(path, item.getId(), item);

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

    /**
     * Läser och skriver ut det råa innehållet (JSON-strukturen) från den lokala 
     * lånefilen direkt till konsolen för administrativ översikt.
     */
    public void showActiveLoans() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(loansFilePath));
            System.out.println("Aktiva lån:");
            for (String line : lines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Kunde inte hämta filen, fel: " + e.getStackTrace());
        }
    }

}