package LibraryManagementPackage.LoanFromLibraryManagement;

import UserManagementPackage.Users;

/**
 * Representerar ett enskilt lån i bibliotekssystemet.
 * <p>
 * Klassen kopplar samman namnet på en utlånad publikation med den specifika
 * användare {@link Users} som har lånat den. Den innehåller valideringar för att
 * säkerställa att inga felaktiga eller tomma värden registreras på lånet.
 * </p>
 *
 * @author Adam Mashadani
 * @version 1.1
 */
public class Loan {
    
    /** Namnet eller titeln på den publikation som har lånats ut. */
    private String borrowedPublicationName;
    
    /** Den användare (låntagare) som är registrerad på lånet. */
    private Users user;

    /**
     * Skapar en ny instans av ett Loan-objekt med angiven publikation och användare.
     * Validerar att parametrarna inte är null eller felaktigt formaterade.
     *
     * @param borrowedPublication namnet eller titeln på publikationen som lånas.
     * @param user                det {@link Users}-objekt som representerar låntagaren.
     * @throws IllegalArgumentException om borrowedPublication är null/tom, eller om user är null.
     */
    public Loan(String borrowedPublication, Users user) {
        setBorrowedPublicationName(borrowedPublication);
        setUser(user);
    }

    /**
     * Hämtar namnet/titeln på den utlånade publikationen.
     *
     * @return en sträng med publikationens namn.
     */
    public String getBorrowedPublicationName() {
        return borrowedPublicationName;
    }

    /**
     * Hämtar den användare som har registrerat detta lån.
     *
     * @return ett {@link Users}-objekt som representerar låntagaren.
     */
    public Users getUser() {
        return user;
    }

    /**
     * Uppdaterar och validerar namnet på den utlånade publikationen.
     *
     * @param borrowedPublication det nya namnet på publikationen (får inte vara null eller tom).
     * @throws IllegalArgumentException om det angivna namnet är null, tomt eller bara innehåller mellanslag.
     */
    public void setBorrowedPublicationName(String borrowedPublication) {
        if (borrowedPublication == null || borrowedPublication.equals("")) {
            throw new IllegalArgumentException("Publikationens namn kan inte vara null eller tomt.");
        }
        this.borrowedPublicationName = borrowedPublication;
    }

    /**
     * Uppdaterar och validerar användaren för detta lån.
     *
     * @param user det nya {@link Users}-objektet som ska kopplas till lånet (får inte vara null).
     * @throws IllegalArgumentException om den angivna användaren är null.
     */
    public void setUser(Users user) {
        if (user == null) {
            throw new IllegalArgumentException("Användaren kan inte vara null.");
        }
        this.user = user;
    }

}