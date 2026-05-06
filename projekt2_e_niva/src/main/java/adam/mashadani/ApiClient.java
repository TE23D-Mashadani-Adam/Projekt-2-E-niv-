package adam.mashadani;

import java.util.ArrayList;
//GSON objekt som vi behöver
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
// Importera Type för att hjälpa json att omvandla data
import java.lang.reflect.Type;
//UniREST objekt som vi behöver
import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;

public class ApiClient {
    private static final String base_url = "http://10.151.168.5:3101/";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Skapar ny data objekt på servern och skickar tillbaka eventuella fel
    public static String postData(String path, Object data) {
        String returnMessage = "Data skickad!";
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

    // Hämtar data från servern
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
        }

        return response.getBody();
    }

    // Tar bort önskad data från servern
    public static String deleteData(String path, String id) {
        String returnMessage = "Data deleted";
        try {
            int responseStatus = Unirest.delete(base_url + path + "/" + id).asEmpty().getStatus();
        } catch (UnirestException e) {
            returnMessage = "Error: " + e.getLocalizedMessage();
        }

        return returnMessage;

    }

     public static void convertToJavaFormat(String path, Type t, ArrayList list){
        String jsonData = ApiClient.getData(path);

        if (jsonData != null) {
            ArrayList TemporaryList = gson.fromJson(jsonData, t);
            list.clear(); // Säger till att den blir tom igen
            if (TemporaryList != null) {
                list.addAll(TemporaryList);
            }
        }
        
    }

}
