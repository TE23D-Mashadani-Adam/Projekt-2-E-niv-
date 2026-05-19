package LibraryManagementPackage;

import LibraryManagementPackage.LoanFromLibraryManagement.Borrowable;

/**
 * En abstrakt basklass för alla typer av utlåningsbara publikationer i biblioteket.
 * <p>
 * Klassen implementerar {@link Comparable} för sortering i bokstavsordning baserat på titel,
 * samt {@link Borrowable} för att hantera utlåningsstatus. Den innehåller strikta
 * valideringar i mutator-metoderna (setters) för att förhindra felaktig data.
 * </p>
 *
 * @author Adam Mashadani
 * @version 1.1
 * @since 2026
 */
public abstract class Publication implements Comparable<Publication>, Borrowable {
    
    /** Publikationens unika ID (genereras eller hämtas oftast från servern). */
    private String id;
    
    /** Publikationens titel eller namn. */
    private String title;
    
    /** Anger om publikationen för tillfället är tillgänglig för utlåning. */
    private Boolean isAvailable;

    /**
     * Skapar en ny instans av en publikation.
     *
     * @param title       publikationens titel.
     * @param isAvailable tillgänglighetsstatus (true om den går att låna).
     */
    public Publication(String title, Boolean isAvailable) {
        this.title = title;
        this.isAvailable = isAvailable;
    }

    /**
     * Hämtar en specifik informationssträng med alla unika detaljer för publikationen.
     * Implementeras av respektive subklass.
     *
     * @return en sträng med detaljerad information.
     */
    public abstract String getInfo();

    /**
     * Hämtar publikationens unika ID.
     *
     * @return sträng med ID:t.
     */
    public String getId() {
        return id;
    }

    /**
     * Hämtar publikationens titel.
     *
     * @return sträng med titeln.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Hämtar tillgänglighetsstatusen för publikationen.
     *
     * @return {@code true} om tillgänglig, {@code false} om utlånad, eller {@code null} om ej satt.
     */
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    /**
     * Sätter publikationens unika ID.
     *
     * @param id det unika ID-numret (får inte vara null eller tomt).
     * @throws IllegalArgumentException om id är null, tomt eller bara innehåller mellanslag.
     */
    public void setId(String id) {
        if (id == null || id.equals("")) {
            throw new IllegalArgumentException("ID kan inte vara null eller tomt.");
        }
        this.id = id;
    }

    /**
     * Sätter publikationens titel.
     *
     * @param title publikationens nya titel (får inte vara null eller tom).
     * @throws IllegalArgumentException om titeln är null, tom eller bara innehåller mellanslag.
     */
    public void setTitle(String title) {
        if (title == null || title.equals("")) {
            throw new IllegalArgumentException("Titeln kan inte vara null eller tom.");
        }
        this.title = title;
    }

    /**
     * Sätter tillgänglighetsstatusen via objekt-wrapper (Boolean).
     *
     * @param isAvailable {@code true} om tillgänglig, annars {@code false} (får inte vara null).
     * @throws IllegalArgumentException om parametern är null.
     */
    public void setIsAvailable(Boolean isAvailable) {
        if (isAvailable == null) {
            throw new IllegalArgumentException("Tillgänglighetsstatus kan inte vara null.");
        }
        this.isAvailable = isAvailable;
    }

    /**
     * Jämför denna publikation med en annan publikation baserat på titel (skiftlägesokonstruktivt).
     * Sorterar i alfabetisk ordning (A-Ö).
     *
     * @param other den andra publikationen som ska jämföras.
     * @return ett negativt heltal, noll, eller ett positivt heltal om denna titeln 
     *         är mindre än, lika med, eller större än den andra.
     */
    @Override
    public int compareTo(Publication other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    /**
     * Kontrollerar om publikationen är tillgänglig för utlåning (från {@link Borrowable}).
     *
     * @return {@code true} om den är tillgänglig, annars {@code false}.
     */
    @Override
    public boolean isAvailable() {
        return this.isAvailable;
    }

    /**
     * Sätter eller ändrar tillgänglighetsstatusen (från interfacer {@link Borrowable}).
     *
     * @param available {@code true} om tillgänglig, {@code false} om utlånad.
     */
    @Override
    public void setAvailable(boolean available) {
        setIsAvailable(available);
    }

    /**
     * Hämtar den språkliga typdefinitionen för den specifika subklassen i klartext.
     *
     * @return en sträng som beskriver klassens typ (t.ex. "Boken" eller "Tidningen").
     */
    public abstract String getTypeByName();

}