package LibraryManagementPackage;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import adam.mashadani.ApiClient;

import java.lang.reflect.Type;

public class LibraryManager {
    private ArrayList<Publications> publicationsList = new ArrayList<Publications>();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void AddBook(String title, Boolean isAvailable, String author,
            String genre, int pages) {
        Books newBook = new Books(title, isAvailable, author, genre, pages);
        // Skriver ut ifall processen lyckades eller inte
        System.out.println(ApiClient.postData("books", newBook));
        publicationsList.add(newBook);
    }

    public void AddMagazines(String title, Boolean isAvailable, int issueNumber,
            String catergory, int publishYear) {
        Magazines newMagazine = new Magazines(title, isAvailable, issueNumber, catergory, publishYear);
        System.out.println(ApiClient.postData("magazines", newMagazine));
        publicationsList.add(newMagazine);
    }

    public void showBooks() {
        Type booksListType = new TypeToken<ArrayList<Books>>() {
        }.getType();
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

    public void showMagazines() {
        String jsonData = ApiClient.getData("magazines");
        Type magazineListType = new TypeToken<ArrayList<Magazines>>() {
        }.getType();
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
