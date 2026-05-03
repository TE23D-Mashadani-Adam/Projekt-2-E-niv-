package adam.mashadani;

import java.util.ArrayList;

public class LibraryManager {
    private ArrayList<Publications> publicationsList;
    
    public void AddBook(String title, Boolean isAvailable, String author,
            String genre, int pages) {
        Books newBook = new Books(title, isAvailable, author, genre, pages);
        //Skriver ut ifall processen lyckades eller inte
        System.out.println(ApiClient.postData("books", newBook)); 
    }

    public void AddMagazines(String title, Boolean isAvailable, int issueNumber,
            int catergory) {
        Magazines newMagazine = new Magazines(title, isAvailable, issueNumber, title, catergory);
    }
}
