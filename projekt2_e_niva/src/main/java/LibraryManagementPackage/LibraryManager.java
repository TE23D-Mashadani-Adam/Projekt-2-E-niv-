package LibraryManagementPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import adam.mashadani.ApiClient;

import java.lang.reflect.Type;

public class LibraryManager {
    private ArrayList<Publications> publicationsList = new ArrayList<Publications>();
    private ArrayList<Books> bookArrayList = new ArrayList<>();
    private ArrayList<Magazines> magazinesArrayLisy = new ArrayList<>();

    private Map<String, Books> bookMapList = new HashMap<>();
    private Map<String, Magazines> magazineMapList = new HashMap<>();

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Type booksListType = new TypeToken<ArrayList<Books>>() {
        }.getType();
    private Type magazineListType = new TypeToken<ArrayList<Magazines>>() {
        }.getType();

    //Lägger till en bok i systemet, returnerar hur det gick
    public String AddBook(String title, Boolean isAvailable, String author,
            String genre, int pages) {
        Books newBook = new Books(title, isAvailable, author, genre, pages);
        publicationsList.add(newBook);
        return ApiClient.postData("books", newBook);
    }

    //Lägger till en tidning i systemet, returnerar hur det gick
    public String AddMagazines(String title, Boolean isAvailable, int issueNumber,
            String catergory, int publishYear) {
        Magazines newMagazine = new Magazines(title, isAvailable, issueNumber, catergory, publishYear);
        publicationsList.add(newMagazine);
        return ApiClient.postData("magazines", newMagazine);
    }

    public void showBooks() {
        
        String jsonData = ApiClient.getData("books");
        ArrayList<Books> books = gson.fromJson(jsonData, booksListType);
        publicationsList.clear();
        publicationsList.addAll(books);
        for (Publications p : publicationsList) {
            if (p instanceof Books) {
                Books b = (Books) p;
                System.out.println(b.getTitle());
            }
        }
    }

    public <T extends Publications> T findByName(String path, Type t, ArrayList<T> list, Map<String,T> map, 
        String title){
           ApiClient.convertToJavaFormat(path, t, list);
        if (list != null) {
            for (T item : list) {
                map.put(title, item);
            }
        }

        if (map.containsKey(title)) {
            return map.get(title);
        }else{
            return null;
        }
    }

    public Books showBookByName(String title){
        return findByName("books", booksListType, bookArrayList, bookMapList, title);

    }

    public Magazines showMagazineByName(String title){
        return findByName("magazines", magazineListType, magazinesArrayLisy, magazineMapList, title);
    }

    public void showMagazines() {
        String jsonData = ApiClient.getData("magazines");
        ArrayList<Magazines> magazines = gson.fromJson(jsonData, magazineListType);
        publicationsList.clear();
        publicationsList.addAll(magazines);
        for (Publications p : publicationsList) {
            if (p instanceof Magazines) {
                Magazines m = (Magazines) p;
                System.out.println(m.getTitle());
            }
        }
    }
}
