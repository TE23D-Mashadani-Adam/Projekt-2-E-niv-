package LibraryManagementPackage.PublicationChildClasses;

import LibraryManagementPackage.Publication;

/**
 * Representerar en tidskrift eller magasin i bibliotekssystemet.
 * <p>
 * Klassen är en subklass till {@link Publication} och utökar basklassens
 * funktionalitet med egenskaper specifika för tidningar, såsom utgåvonummer, 
 * kategori och utgivningsår.
 * </p>
 *
 * @author Adam Mashadani
 * @version 1.0
 * @since 2026
 */
public class Magazine extends Publication{
    
    /** Tidningens specifika utgåvonummer (nummer i ordningen). */
    private int issueNumber;
    
    /** Tidningens kategori (t.ex. "Vetenskap", "Mode", "Teknik"). */
    private String category;
    
    /** Året då denna specifika tidningsutgåva publicerades. */
    private int publishYear;

    /**
     * Skapar en ny instans av ett Magazines-objekt med angivna värden.
     *
     * @param title       tidningens titel eller namn.
     * @param isAvailable tillgänglighetsstatus för utlåning (skickas vidare till basklassen).
     * @param issueNumber utgåvonummer för tidningen.
     * @param category    tidningens ämneskategori.
     * @param publishYear tidningens utgivningsår.
     */
    public Magazine(String title, Boolean isAvailable, int issueNumber, String category, int publishYear) {
        super(title, isAvailable);
        this.issueNumber = issueNumber;
        this.category = category;
        this.publishYear = publishYear;
    }

    /**
     * Genererar och returnerar en sammanställning av tidningens specifika information.
     *
     * @return en strängrepresentation som innehåller utgåvonummer, kategori och utgivningsår.
     */
    @Override
    public String getInfo() {
        return "Magazines [issueNumber=" + issueNumber + ", category=" + category + ", publishYear=" + publishYear
                + "]";
    }

    /**
     * Hämtar tidningens utgåvonummer.
     *
     * @return heltal för utgåvonumret.
     */
    public int getIssueNumber() {
        return issueNumber;
    }

    /**
     * Hämtar tidningens kategori.
     *
     * @return sträng som representerar kategorin.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Hämtar utgivningsåret för tidningen.
     *
     * @return heltal för utgivningsåret.
     */
    public int getPublishYear() {
        return publishYear;
    }

   /**
     * Uppdaterar eller sätter tidningens utgåvonummer.
     * <p>
     * Utgåvonumret måste vara ett giltigt positivt tal (1 eller högre).
     * </p>
     *
     * @param issueNumber det nya utgåvonumret (måste vara större än 0).
     * @throws IllegalArgumentException om utgåvonumret är 0 eller negativt.
     */
    public void setIssueNumber(int issueNumber) {
        if (issueNumber <= 0) {
            throw new IllegalArgumentException("Utgåvonumret måste vara ett positivt tal större än 0.");
        }
        this.issueNumber = issueNumber;
    }

    /**
     * Uppdaterar eller sätter tidningens kategori.
     *
     * @param category den nya kategorin (får inte vara null eller en tom sträng).
     * @throws IllegalArgumentException om kategorin är null eller en tom sträng ("").
     */
    public void setCategory(String category) {
        if (category == null || category.equals("")) {
            throw new IllegalArgumentException("Kategori kan inte vara null eller tom.");
        }
        this.category = category;
    }

    /**
     * Uppdaterar eller sätter tidningens utgivningsår.
     * <p>
     * Året valideras så att det inte är ett negativt tal eller ett år i framtiden.
     * </p>
     *
     * @param publishYear det nya utgivningsåret (måste vara 0 eller större, och inte i framtiden).
     * @throws IllegalArgumentException om utgivningsåret är negativt eller ligger i framtiden (efter 2026).
     */
    public void setPublishYear(int publishYear) {
        if (publishYear < 0) {
            throw new IllegalArgumentException("Utgivningsåret kan inte vara negativt.");
        }
        if (publishYear > 2026) {
            throw new IllegalArgumentException("Utgivningsåret kan inte ligga i framtiden.");
        }
        this.publishYear = publishYear;
    }
    /**
     * Hämtar den språkliga typdefinitionen för denna publikationsklass.
     *
     * @return strängen "Tidningen".
     */
    @Override
    public String getTypeByName() {
        return "Tidningen";
    }
}