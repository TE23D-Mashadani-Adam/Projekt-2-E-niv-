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

/**
 * Hanterar bibliotekets katalog och samlingar av olika publikationer.
 * <p>
 * Denna klass ansvarar för att kommunicera med databasen/servern via {@link ApiClient}
 * för att lägga till, hämta, söka efter och radera böcker, tidningar och övrig media.
 * </p>
 *
 * @author Adam Mashadani
 * @version 1.0
 */
public class LibraryManager {

    /** Central lista som lagrar alla typer av publikationer för sortering och visning. */
    private ArrayList<Publication> publicationsList = new ArrayList<Publication>();
    
    /** Temporär lista som lagrar bokobjekt vid sökning och synkronisering. */
    private ArrayList<Books> bookArrayList = new ArrayList<>();
    
    /** Temporär lista som lagrar mediaobjekt vid filtrering och strömhantering. */
    private ArrayList<Media> mediaArrayList = new ArrayList<>();
    
    /** Temporär lista som lagrar tidningsobjekt vid sökning och synkronisering. */
    private ArrayList<Magazines> magazinesArrayLisy = new ArrayList<>();
    
    /** Alternativ temporär lista som används vid sökning av specifika mediaobjekt efter namn. */
    private ArrayList<Media> mediaArrayListType = new ArrayList<>();

    /** Snabbregister (Map) som mappar en boks titel till dess motsvarande {@link Books}-objekt. */
    private Map<String, Books> bookMapList = new HashMap<>();
    
    /** Snabbregister (Map) som mappar en tidnings titel till dess motsvarande {@link Magazines}-objekt. */
    private Map<String, Magazines> magazineMapList = new HashMap<>();
    
    /** Snabbregister (Map) som mappar ett medies titel till dess motsvarande {@link Media}-objekt. */
    private Map<String, Media> mediaMapList = new HashMap<>();

    /** Gson-instans konfigurerad med snygg utskrift (pretty printing) för JSON-hantering. */
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /** Typdefinition för deserialisering av en lista med {@link Books} via Gson. */
    private Type booksListType = new TypeToken<ArrayList<Books>>() {}.getType();
    
    /** Typdefinition för deserialisering av en lista med {@link Magazines} via Gson. */
    private Type magazineListType = new TypeToken<ArrayList<Magazines>>() {}.getType();
    
    /** Typdefinition för deserialisering av en lista med {@link Media} via Gson. */
    private Type mediaListType = new TypeToken<ArrayList<Media>>() {}.getType();

    /** API-sökväg (endpoint) för resurser av typen böcker. */
    private final String bookPath = "books";
    
    /** API-sökväg (endpoint) för resurser av typen tidningar. */
    private final String magazinePath = "magazines";
    
    /** API-sökväg (endpoint) för resurser av typen media. */
    private final String mediaPath = "media";

    /**
     * Standardkonstruktor för LibraryManager.
     */
    public LibraryManager() {
        // Standardkonstruktor
    }

    /**
     * Lägger till en ny bok i bibliotekssystemet och skickar datan till servern.
     *
     * @param title       bokens titel.
     * @param isAvailable bokens tillgänglighetsstatus (true om den går att låna).
     * @param author      bokens författare.
     * @param genre       bokens genre.
     * @param pages       antal sidor i boken.
     * @return ett statusmeddelande som beskriver om operationen lyckades eller misslyckades.
     */
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

    /**
     * Lägger till en ny tidning i bibliotekssystemet och skickar datan till servern.
     *
     * @param title       tidningens titel.
     * @param isAvailable tidningens tillgänglighetsstatus.
     * @param issueNumber utgåvonummer/nummer i ordningen.
     * @param catergory   tidningens kategori.
     * @param publishYear tidningens utgivningsår.
     * @return ett statusmeddelande som beskriver om operationen lyckades eller misslyckades.
     */
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

    /**
     * Hämtar alla böcker från servern, sorterar dem i bokstavsordning och visar deras titlar i konsolen.
     */
    public void showBooks() {

        String jsonData = ApiClient.getData(bookPath);
        if (jsonData != null) {

            ArrayList<Books> books = gson.fromJson(jsonData, booksListType);

            publicationsList.clear();
            publicationsList.addAll(books);
            Collections.sort(publicationsList);
            for (Publication p : publicationsList) {
                if (p instanceof Books) {
                    Books b = (Books) p;
                    System.out.println(b.getTitle());
                }
            }
        }
    }

    /**
     * Hämtar all media från servern, filtrerar utbudet baserat på typ och åldersgräns med hjälp 
     * av Java Streams, samt sorterar och visar resultatet för användaren.
     *
     * @param type den mediatyp som ska matchas (t.ex. "Spel").
     * @param age  den minsta åldersgränsen som mediet ska ha (>=).
     */
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

    /**
     * En generisk hjälpmetod som hämtar resurser från en angiven API-sökväg, mappar dem 
     * i ett register baserat på titel, och returnerar det matchande objektet om det hittas.
     *
     * @param <T>   den specifika subklassen till {@link Publication} som hanteras.
     * @param path  API-endpointen (t.ex. "books", "magazines" eller "media").
     * @param t     typdefinitionen för klassen (Type) som används vid JSON-konverteringen.
     * @param list  den interna listan där objekten tillfälligt ska sparas.
     * @param map   det register (Map) där objekten struktureras upp med titeln som nyckel.
     * @param title titeln på den publikation som söks.
     * @return objektet av typen T om det påträffas, annars {@code null}.
     */
    public <T extends Publication> T findByName(String path, Type t, ArrayList<T> list, Map<String, T> map,
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

    /**
     * Söker efter en bok baserat på dess titel och returnerar dess fullständiga informationssträng.
     *
     * @param title titeln på boken som ska sökas upp.
     * @return en sträng med bokens detaljer om den hittas, annars meddelas att boken inte hittades.
     */
    public String showBookByName(String title) {
        Books book = findByName(bookPath, booksListType, bookArrayList, bookMapList, title);
        if (book != null) {
            return book.getInfo();
        } else {
            return "Bok hittade ej!";
        }

    }

    /**
     * Söker efter en bok via dess titel och returnerar själva bokobjektet.
     * Prints ett felmeddelande i konsolen om objektet saknas.
     *
     * @param title titeln på boken som eftersöks.
     * @return det matchande {@link Books}-objektet, eller {@code null} om det ej påträffas.
     */
    public Books getBookByName(String title) {
        Books book = findByName(bookPath, booksListType, bookArrayList, bookMapList, title);
        if (book != null) {
            return book;
        } else {
            System.out.println("Boken hittades ej, kontrollera att du skrev in rätt titel!");
            return null;
        }
    }

    /**
     * Söker efter en tidning via dess titel och returnerar tidningsobjektet.
     *
     * @param title titeln på tidningen som eftersöks.
     * @return det matchande {@link Magazines}-objektet, eller {@code null} om det ej påträffas.
     */
    public Magazines getMagazineByName(String title) {
        Magazines magazine = findByName("magazines", magazineListType, magazinesArrayLisy, magazineMapList, title);
        return magazine;
    }

    /**
     * Söker efter ett mediaobjekt via dess titel och returnerar objektet.
     *
     * @param title titeln på mediet som eftersöks.
     * @return det matchande {@link Media}-objektet, eller {@code null} om det ej påträffas.
     */
    public Media getMediaByName(String title) {
        Media media = findByName("media", mediaListType, mediaArrayListType, mediaMapList, title);
        return media;
    }

    /**
     * Söker efter en tidning baserat på dess titel och returnerar dess fullständiga informationssträng.
     *
     * @param title titeln på tidningen som ska sökas upp.
     * @return en sträng med tidningens detaljer, annars ett felmeddelande.
     */
    public String showMagazineByName(String title) {
        Magazines magazine = findByName(magazinePath, magazineListType, magazinesArrayLisy, magazineMapList, title);
        if (magazine != null) {
            return magazine.getInfo();
        } else {
            return "Tidning hittades ej!";
        }
    }

    /**
     * Hämtar alla tidningar från servern, sorterar dem i bokstavsordning och visar deras titlar i konsolen.
     */
    public void showMagazines() {
        String jsonData = ApiClient.getData(magazinePath);
        ArrayList<Magazines> magazines = gson.fromJson(jsonData, magazineListType);
        publicationsList.clear();
        publicationsList.addAll(magazines);
        Collections.sort(publicationsList);
        for (Publication p : publicationsList) {
            if (p instanceof Magazines) {
                Magazines m = (Magazines) p;
                System.out.println(m.getTitle());
            }
        }
    }

    /**
     * Raderar en bok permanent från databasen/servern med hjälp av bokens unika ID.
     *
     * @param id det unika ID-numret på boken som ska raderas.
     */
    public void deleteBook(String id) {
        String responseMessage = ApiClient.deleteData(bookPath, id);
        if (responseMessage == "Data deleted") {
            System.out.println("Bok borttagen!");
        } else {
            System.out.println(responseMessage);
        }
    }

    /**
     * Raderar en tidning permanent från databasen/servern med hjälp av tidningens unika ID.
     *
     * @param id det unika ID-numret på tidningen som ska raderas.
     */
    public void deleteMagazine(String id) {
        String responseMessage = ApiClient.deleteData(magazinePath, id);
        if (responseMessage == "Data deleted") {
            System.out.println("Tidning borttagen!");
        } else {
            System.out.println(responseMessage);
        }
    }
}