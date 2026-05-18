package LibraryManagementPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import LibraryManagementPackage.PublicationChildClasses.Books;
import LibraryManagementPackage.PublicationChildClasses.Magazines;
import LibraryManagementPackage.PublicationChildClasses.Media;
import adam.mashadani.ApiClient;

import java.lang.reflect.Type;

public class LibraryManager {
    private ArrayList<PublicationsName> publicationsList = new ArrayList<PublicationsName>();
    private ArrayList<Books> bookArrayList = new ArrayList<>();
    private ArrayList<Media> mediaArrayList = new ArrayList<>();
    private ArrayList<Magazines> magazinesArrayLisy = new ArrayList<>();
    private ArrayList<Media> mediaArrayListType = new ArrayList<>();

    private Map<String, Books> bookMapList = new HashMap<>();
    private Map<String, Magazines> magazineMapList = new HashMap<>();
    private Map<String, Media> mediaMapList = new HashMap<>();

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private Type booksListType = new TypeToken<ArrayList<Books>>() {
    }.getType();
    private Type magazineListType = new TypeToken<ArrayList<Magazines>>() {
    }.getType();
    private Type mediaListType = new TypeToken<ArrayList<Media>>() {
    }.getType();

    private final String bookPath = "books";
    private final String magazinePath = "magazines";
    private final String mediaPath = "media";

    // Lägger till en bok i systemet, returnerar hur det gick
    public String AddBook(String title, Boolean isAvailable, String author,
            String genre, int pages) {
        Books newBook = new Books(title, isAvailable, author, genre, pages);
        publicationsList.add(newBook);
        String responseMessage = ApiClient.postData(bookPath, newBook);
        if (responseMessage == "Data skickad") {
            return "Bok tillagd!";
        } else {
            return responseMessage;
        }
    }

    // Lägger till en tidning i systemet, returnerar hur det gick
    public String AddMagazines(String title, Boolean isAvailable, int issueNumber,
            String catergory, int publishYear) {
        Magazines newMagazine = new Magazines(title, isAvailable, issueNumber, catergory, publishYear);
        publicationsList.add(newMagazine);
        String responseMessage = ApiClient.postData(magazinePath, newMagazine);
        if (responseMessage == "Data skickad") {
            return "Tdining tillagd!";
        } else {
            return responseMessage;
        }
    }

    // Visar alla böcker som finns i biblioteket
    public void showBooks() {

        String jsonData = ApiClient.getData(bookPath);
        if (jsonData != null) {

            ArrayList<Books> books = gson.fromJson(jsonData, booksListType);

            publicationsList.clear();
            publicationsList.addAll(books);
            Collections.sort(publicationsList);
            for (PublicationsName p : publicationsList) {
                if (p instanceof Books) {
                    Books b = (Books) p;
                    System.out.println(b.getTitle());
                }
            }
        }
    }

    // Visar all media som finns i biblioteket
    public void showMedia(String type, int age) {
        String jsonData = ApiClient.getData(mediaPath);
        ApiClient.convertToJavaFormat(jsonData, mediaListType, mediaArrayList);
        List<Media> medias = mediaArrayList.stream()
                .filter(media -> media.getType().equals(type) && media.getAge() >= age)
                .sorted()
                .distinct()
                .collect(Collectors.toList());

        if (!medias.isEmpty()) {
            for (Media media : medias) {
                System.out.println(media.getInfo());
            }
        } else {
            System.out.println("Inga media hittades enligt din sökfilter, testa ändra filter och försök igen");
        }
    }

    // Hittar publikationen genom att ange dens namn och returnerar den som objekt
    public <T extends PublicationsName> T findByName(String path, Type t, ArrayList<T> list, Map<String, T> map,
            String title) {
        String jsonData = ApiClient.getData(path);
        ApiClient.convertToJavaFormat(jsonData, t, list);
        if (list != null) {
            for (T item : list) {
                map.put(item.getTitle(), item);
            }

            if (map.containsKey(title) && list != null) {
                return map.get(title);
            } else if (map.containsKey(title) && list == null) {
                System.out.println("Servern är tom");
                return null;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    // Hittar boken genom namn och returnerar information om boken som sträng
    public String showBookByName(String title) {
        Books book = findByName(bookPath, booksListType, bookArrayList, bookMapList, title);
        if (book != null) {
            return book.getInfo();
        } else {
            return "Bok hittade ej!";
        }

    }

    // Hittar boken genom namn och returnerar själva boken som objekt
    public Books getBookByName(String title) {
        Books book = findByName(bookPath, booksListType, bookArrayList, bookMapList, title);
        if (book != null) {
            return book;
        } else {
            System.out.println("Boken hittades ej, kontrollera att du skrev in rätt titel!");
            return null;
        }
    }

    // Hittar tidningen genom namn och returnerar själva tidningen som objekt
    public Magazines getMagazineByName(String title) {
        Magazines magazine = findByName("magazines", magazineListType, magazinesArrayLisy, magazineMapList, title);
        return magazine;
    }

    // Hittar media genom namn och returnerar själva mediat som objekt
    public Media getMediaByName(String title) {
        Media media = findByName("media", mediaListType, mediaArrayListType, mediaMapList, title);
        return media;
    }

    public String showMagazineByName(String title) {
        Magazines magazine = findByName(magazinePath, magazineListType, magazinesArrayLisy, magazineMapList, title);
        if (magazine != null) {
            return magazine.getInfo();
        } else {
            return "Tidning hittades ej!";
        }
    }

    public void showMagazines() {
        String jsonData = ApiClient.getData(magazinePath);
        ArrayList<Magazines> magazines = gson.fromJson(jsonData, magazineListType);
        publicationsList.clear();
        publicationsList.addAll(magazines);
        Collections.sort(publicationsList);
        for (PublicationsName p : publicationsList) {
            if (p instanceof Magazines) {
                Magazines m = (Magazines) p;
                System.out.println(m.getTitle());
            }
        }
    }

    public void deleteBook(String id) {
        String responseMessage = ApiClient.deleteData(bookPath, id);
        if (responseMessage == "Data deleted") {
            System.out.println("Bok borttagen!");
        } else {
            System.out.println(responseMessage);
        }
    }

    public void deleteMagazine(String id) {
        String responseMessage = ApiClient.deleteData(magazinePath, id);
        if (responseMessage == "Data deleted") {
            System.out.println("Tidning borttagen!");
        } else {
            System.out.println(responseMessage);
        }
    }
}
