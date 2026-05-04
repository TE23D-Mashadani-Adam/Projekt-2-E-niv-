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
// Importera Fil hantering
import java.nio.file.*;
import java.io.IOException;
// ArrayList för att lagra objekt
import java.util.ArrayList;

public class ApiClient {
    private static final String base_url = "http://10.151.168.5:3101/";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String postData(String path, Object data) {
        String returnMessage = "";
        try {
            String jsonBody = gson.toJson(data);

            HttpResponse<String> response = Unirest.post(base_url + path)
                    .header("Content-Type", "application/json")
                    .body(jsonBody)
                    .asString();

            int status = response.getStatus();

            if (status != 200 && status != 201) {
                returnMessage = "Fel från servern, statuskod: " + status;
            }
        } catch (UnirestException e) {
            returnMessage = "Kunde inte nå servern, felkod: " + e.getLocalizedMessage();
        }

        return returnMessage;
    }

    public static String getData(String path) {
        HttpResponse<String> response = null;
        try {
            response = Unirest.get(base_url + path).asString();
        } catch (UnirestException e) {
            System.out.println("Kunde inte nå servern, felkod: " + e.getLocalizedMessage()); 
        }
        
        return response.getBody();
    }

    public static String deleteData(String path, String id){
        String returnMessage = "";
        try{
            int responseStatus = Unirest.delete(base_url + path + "/" + id).asEmpty().getStatus();
        }catch(UnirestException e){
            returnMessage = "Error: " + e.getLocalizedMessage();
        }

        return returnMessage;
        
    }

}
