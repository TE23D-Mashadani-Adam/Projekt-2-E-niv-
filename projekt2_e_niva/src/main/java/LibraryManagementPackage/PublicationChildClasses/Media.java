package LibraryManagementPackage.PublicationChildClasses;

import java.util.List;

import LibraryManagementPackage.Publication;

/**
 * Representerar ett mediaobjekt i bibliotekssystemet.
 * <p>
 * Klassen är en subklass till {@link Publication} och utökar basklassens 
 * funktionalitet med attribut specifika för digital eller fysisk media, 
 * såsom mediatyp, genre och en rekommenderad åldersgräns.
 * </p>
 *
 * @author Adam Mashadani
 * @version 1.0
 * @since 2026
 */
public class Media extends Publication {
    
    /** Typen av media (t.ex. "CD", "DVD", "Spel"). */
    private String type;
    
    /** Medietypens specifika genre (t.ex. "Action", "Dokumentär", "Pop"). */
    private String genre;
    
    /** Den rekommenderade eller lägsta åldersgränsen för mediet. */
    private int age;

    /**
     * Skapar en ny instans av ett Media-objekt med specificerade värden.
     *
     * @param title       mediets titel.
     * @param isAvailable tillgänglighetsstatus för utlåning (skickas till basklassen).
     * @param type        medietyp (t.ex. "DVD" eller "Spel").
     * @param genre       mediets genre.
     * @param age         mediets åldersgräns.
     * @param isAvailbe   en extra parameter för tillgänglighet (används ej explicit i konstruktorn).
     */
    public Media(String title, Boolean isAvailable, String type, String genre, int age,
            boolean isAvailbe) {
        super(title, isAvailable);
        this.type = type;
        this.genre = genre;
        this.age = age;
    }

    /**
     * Genererar och returnerar en sammanställning av mediets fullständiga information.
     *
     * @return en strängrepresentation som innehåller ID, typ, titel, genre, 
     *         åldersgräns och tillgänglighetsstatus.
     */
    @Override
    public String getInfo() {
        return "Media [id=" + this.getId() + ", type=" + type + ", title=" + this.getTitle() + ", genre=" + genre + ", age=" + age
                + ", isAvailbe=" + this.isAvailable() + "]";
    }

    /**
     * Hämtar mediets typ.
     *
     * @return sträng som representerar mediatypen.
     */
    public String getType() {
        return type;
    }

    /**
     * Hämtar mediets genre.
     *
     * @return sträng som representerar genren.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Hämtar den rekommenderade åldersgränsen för mediet.
     *
     * @return heltal för åldersgränsen.
     */
    public int getAge() {
        return age;
    }

   /**
     * Uppdaterar eller sätter mediets typ.
     *
     * @param type den nya mediatypen (får inte vara null eller en tom sträng).
     * @throws IllegalArgumentException om typen är null eller en tom sträng ("").
     */
    public void setType(String type) {
        if (type == null || type.equals("")) {
            throw new IllegalArgumentException("Medietyp kan inte vara null eller tom.");
        }
        this.type = type;
    }

    /**
     * Uppdaterar eller sätter mediets genre.
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
     * Uppdaterar eller sätter mediets åldersgräns.
     * <p>
     * Åldersgränsen måste vara ett giltigt tal (0 eller högre).
     * </p>
     *
     * @param age den nya åldersgränsen (måste vara 0 eller större).
     * @throws IllegalArgumentException om åldersgränsen är ett negativt tal.
     */
    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Åldersgränsen kan inte vara negativ.");
        }
        this.age = age;
    }

    /**
     * Hämtar namnet på mediatypen. 
     * Denna metod överskuggar basklassens metod för att returnera det interna {@code type}-attributet.
     *
     * @return sträng med mediatypen.
     */
    @Override
    public String getTypeByName() {
        return this.type;
    }

}