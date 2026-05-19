package LibraryManagementPackage.PublicationChildClasses;

import LibraryManagementPackage.Publication;

/**
 * Representerar en bok i bibliotekssystemet.
 * <p>
 * Klassen är en subklass till {@link Publication} och utökar den med
 * bokspecifika egenskaper såsom författare, genre och antal sidor.
 * </p>
 *
 * @author Adam Mashadani
 * @version 1.0
 */
public class Books extends Publication {
    
    /** Bokens författare. */
    private String author;
    
    /** Bokens genre (t.ex. "Fantasy", "Deckare", "Sci-Fi"). */
    private String genre;
    
    /** Antal sidor i boken. */
    private int pages;

    /**
     * Skapar en ny instans av en bok med specificerade värden.
     *
     * @param title       bokens titel.
     * @param isAvailable tillgänglighetsstatus för utlåning (skickas till basklassen).
     * @param author      bokens författare.
     * @param genre       bokens genre.
     * @param pages       antal sidor i boken.
     */
    public Books(String title, Boolean isAvailable, String author, String genre, int pages) {
        super(title, isAvailable);
        this.author = author;
        this.genre = genre;
        this.pages = pages;
    }

    /**
     * Genererar och returnerar en sammanställning av bokens specifika information.
     *
     * @return en strängrepresentation som innehåller författare, genre och antal sidor.
     */
    @Override
    public String getInfo() {
        return "Books [author=" + author + ", genre=" + genre + ", pages=" + pages + "]";
    }

    /**
     * Hämtar bokens författare.
     *
     * @return sträng som representerar författaren.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Hämtar bokens genre.
     *
     * @return sträng som representerar genren.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Hämtar antalet sidor i boken.
     *
     * @return heltal för antal sidor.
     */
    public int getPages() {
        return pages;
    }

   /**
     * Uppdaterar eller sätter bokens författare.
     *
     * @param author den nya författaren (får inte vara null eller en tom sträng).
     * @throws IllegalArgumentException om författaren är null eller en tom sträng ("").
     */
    public void setAuthor(String author) {
        if (author == null || author.equals("")) {
            throw new IllegalArgumentException("Författarens namn kan inte vara null eller tomt.");
        }
        this.author = author;
    }

    /**
     * Uppdaterar eller sätter bokens genre.
     *
     * @param genre den nya genren (får inte vara null eller en tom sträng).
     * @throws IllegalArgumentException om genren är null eller en tom sträng ("").
     */
    public void setGenre(String genre) {
        if (genre == null || genre.equals("")) {
            throw new IllegalArgumentException("Genre kan inte vara null eller tom.");
        }
        this.genre = genre;
    }

    /**
     * Uppdaterar eller sätter antalet sidor i boken.
     * <p>
     * Antalet sidor måste vara ett giltigt positivt tal (minst 1 sida).
     * </p>
     *
     * @param pages det nya antalet sidor (måste vara större än 0).
     * @throws IllegalArgumentException om antalet sidor är 0 eller negativt.
     */
    public void setPages(int pages) {
        if (pages <= 0) {
            throw new IllegalArgumentException("En bok måste ha minst 1 sida.");
        }
        this.pages = pages;
    }

    /**
     * Hämtar den språkliga typdefinitionen för denna publikationsklass.
     *
     * @return strängen "Boken".
     */
    @Override
    public String getTypeByName() {
        return "Boken";
    }

}