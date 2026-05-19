package LibraryManagementPackage.LoanFromLibraryManagement;

/**
 * Definerar kontraktet för utlåningsbara objekt i bibliotekssystemet.
 * <p>
 * Alla publikationer eller resurser som ska kunna lånas ut av en användare 
 * måste implementera detta interface för att säkerställa enhetlig hantering 
 * av tillgänglighetsstatus samt unika identifierare.
 * </p>
 *
 * @author Adam Mashadani
 * @version 1.0
 */
public interface Borrowable {
    
    /**
     * Kontrollerar om publikationen är tillgänglig för utlåning.
     *
     * @return {@code true} om publikationen är tillgänglig, annars {@code false}.
     */
    //Returnerar ifall publiceringen är tillgänglig eller inte
    boolean isAvailable();
    
    /**
     * Sätter eller uppdaterar tillgänglighetsstatusen för publikationen.
     *
     * @param available {@code true} om objektet ska göras tillgängligt för utlåning, 
     *                  eller {@code false} om det har blivit utlånat.
     */
    //Sätter värdet för tillgänlighet
    void setAvailable(boolean available);
    
    /**
     * Hämtar publikationens unika identifierare (ID) från databasen/servern.
     *
     * @return en sträng som representerar objektets unika ID.
     */
    String getId();
    
    /**
     * Hämtar publikationens titel eller namn.
     *
     * @return en sträng med publikationens titel.
     */
    String getTitle();
}