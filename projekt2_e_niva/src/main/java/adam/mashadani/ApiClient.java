package adam.mashadani;

import java.util.ArrayList;
//GSON objekt som vi behöver
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
// Importera Type för att hjälpa json att omvandla data
import java.lang.reflect.Type;
import java.net.http.HttpRequest;

//UniREST objekt som vi behöver
import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;

/**
 * Klientklass för kommunikation med externt REST-API.
 * <p>
 * Denna klass tillhandahåller statiska metoder för att utföra standardmässiga
 * HTTP-anrop (GET, POST, PUT, DELETE) mot bibliotekssystemets server, 
 * samt för att serialisera och deserialisera data med hjälp av Google Gson.
 * </p>
 *
 * @author Adam Mashadani
 * @version 1.0
 */
public class ApiClient {
    
    /** Bas-URL till serverns API-endpoint. */
    private static final String base_url = "http://10.151.168.5:3101/";
    
    /** Gson-instans konfigurerad med snygg utskrift (pretty printing) för JSON-hantering. */
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Standardkonstruktor för ApiClient.
     */
    public ApiClient() {
        // Standardkonstruktor
    }

    /**
     * Skapar ett nytt dataobjekt på servern genom att skicka en HTTP POST-request.
     * Metoden konverterar inskickat Java-objekt till en JSON-sträng i bodyn.
     *
     * @param path sökvägen (endpoint) på servern dit datan ska skickas (t.ex. "books").
     * @param data det Java-objekt som ska serialiseras till JSON och sparas.
     * @return ett statusmeddelande ("Data skickad" vid framgång, annars felmeddelande).
     */
    public static String postData(String path, Object data) {
        String returnMessage = "Data skickad";
        try {
            String jsonBody = gson.toJson(data);

            HttpResponse<String> response = Unirest.post(base_url + path)
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .asString();

            int status = response.getStatus();

            //Får 500 statuskod från servern, men datan sparas ändå, därför struntar i den statuskoden
            if (status != 200 && status != 201 && status!=500) {
                returnMessage = "Fel från servern, statuskod: " + status
                + " Respons bodyn: " + response.getBody();
            }
        } catch (UnirestException e) {
            returnMessage = "Kunde inte nå servern, felkod: " + e.getLocalizedMessage();
        }

        return returnMessage;
    }

    /**
     * Hämtar data från servern via en HTTP GET-request baserat på angiven sökväg.
     *
     * @param path sökvägen (endpoint) som datan ska hämtas ifrån.
     * @return serverns svar som en rå JSON-sträng, eller {@code null} om servern inte kan nås.
     */
    public static String getData(String path) {
        HttpResponse<String> response = null;
        try {
            response = Unirest.get(base_url + path).asString();
            int status = response.getStatus();
            if (status != 200 && status != 201) {
                System.out.println("En fel uppstod, kunde inte hämta data, statuskod: " + status);
            } 
        } catch (UnirestException e) {
            System.out.println("Kunde inte nå servern, felkod: " + e.getLocalizedMessage());
            return null;
        }

        return response.getBody();
    }

    /**
     * Raderar data från servern via en HTTP DELETE-request baserat på sökväg och ett unikt ID.
     *
     * @param path sökvägen (endpoint) för den resurstyp som ska raderas.
     * @param id   det unika ID-numret för det specifika objekt som ska tas bort.
     * @return ett statusmeddelande ("Data deleted" vid framgång, annars felmeddelande).
     */
    public static String deleteData(String path, String id) {
        String returnMessage = "Data deleted";
        try {
            int responseStatus = Unirest.delete(base_url + path + "/" + id).asEmpty().getStatus();
        } catch (UnirestException e) {
            returnMessage = "Error: " + e.getLocalizedMessage();
        }

        return returnMessage;

    }

    /**
     * Konverterar en rå JSON-sträng till önskat Java-format (ArrayList) och 
     * synkroniserar innehållet med den inskickade listan.
     *
     * @param jsonData den råa JSON-strängen som hämtats från servern.
     * @param t        måltypen (Type) som Gson ska mappa JSON-datan till.
     * @param list     den mål-ArrayList som ska rensas och fyllas med den konverterade datan.
     */
     public static void convertToJavaFormat(String jsonData, Type t, ArrayList list){

        if (jsonData != null) {
            ArrayList TemporaryList = gson.fromJson(jsonData, t);
            list.clear(); // Säger till att den blir tom igen
            if (TemporaryList != null) {
                list.addAll(TemporaryList);
            }
        }
        
    }

    /**
     * Uppdaterar ett befintligt dataobjekt på servern genom en HTTP PUT-request.
     *
     * @param path sökvägen (endpoint) för den resurstyp som ska uppdateras.
     * @param id   det unika ID-numret för det objekt som ska uppdateras.
     * @param data det nya Java-objektet som ska serialiseras till JSON och ersätta det gamla.
     * @return ett statusmeddelande ("Data uppdaterad" vid framgång, annars felmeddelande).
     */
    public static String putRequest(String path, String id, Object data){
        String returnMessage = "Data uppdaterad";
        try{
            String jsonBody = gson.toJson(data);
            HttpResponse<String> response = Unirest.put(base_url + path + "/" + id)
            .header("Content-Type", "application/json")
            .body(jsonBody)
            .asString();

            int status = response.getStatus();

            if(status != 200 && status != 204 && status != 500){
                returnMessage = "Fel vid uppdatering, statuskod: " + status;
                System.out.println(status);
            }
        }catch(UnirestException e){
            returnMessage = "Kunde inte nå servern";
        }

        return returnMessage;
    }

}