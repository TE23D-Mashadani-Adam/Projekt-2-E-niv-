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
