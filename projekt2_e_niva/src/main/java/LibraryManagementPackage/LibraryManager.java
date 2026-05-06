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

    private final String bookPath = "books";
    private final String magazinePath = "magazines";

    // Lägger till en bok i systemet, returnerar hur det gick
    public String AddBook(String title, Boolean isAvailable, String author,
            String genre, int pages) {
        Books newBook = new Books(title, isAvailable, author, genre, pages);
        publicationsList.add(newBook);
        return ApiClient.postData(bookPath, newBook);
    }

    // Lägger till en tidning i systemet, returnerar hur det gick
    public String AddMagazines(String title, Boolean isAvailable, int issueNumber,
            String catergory, int publishYear) {
        Magazines newMagazine = new Magazines(title, isAvailable, issueNumber, catergory, publishYear);
        publicationsList.add(newMagazine);
        return ApiClient.postData(magazinePath, newMagazine);
    }

    public void showBooks() {

        String jsonData = ApiClient.getData(bookPath);
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

    public <T extends Publications> T findByName(String path, Type t, ArrayList<T> list, Map<String, T> map,
            String title) {
        ApiClient.convertToJavaFormat(path, t, list);
        if (list != null && map.containsKey(title)) {
            for (T item : list) {
                map.put(item.getTitle(), item);
            }
        }else if(!map.containsKey(title) && list != null){
            System.out.println("Titeln hittades inte, kontrollera att du skrev rätt titel");
        }else if(map.containsKey(title) && list == null){
            System.out.println("Hittade inga böcker på servern");
        }

        if (map.containsKey(title)) {
            return map.get(title);
        } else {
            return null;
        }
    }

    public String showBookByName(String title) {
        Books book = findByName(bookPath, booksListType, bookArrayList, bookMapList, title);
        if (book != null) {
            return book.getInfo();
        }else{
            return "Bok hittade ej!";
        }

    }

    public String showMagazineByName(String title) {
        Magazines magazine = findByName(magazinePath, magazineListType, magazinesArrayLisy, magazineMapList, title);
        if (magazine != null) {
            return magazine.getInfo();
        }else{
            return "Tidning hittades ej!";
        }
    }

    public void showMagazines() {
        String jsonData = ApiClient.getData(magazinePath);
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

    public void deleteBook(String id) {
        String responseMessage = ApiClient.deleteData(bookPath, id);
        if (responseMessage == "") {
            System.out.println("Bok borttagen!");
        } else {
            System.out.println(responseMessage);
        }
    }

    public void deleteMagazine(String id) {
        String responseMessage = ApiClient.deleteData(magazinePath, id);
        if (responseMessage == "") {
            System.out.println("Tidning borttagen!");
        } else {
            System.out.println(responseMessage);
        }
    }
}
