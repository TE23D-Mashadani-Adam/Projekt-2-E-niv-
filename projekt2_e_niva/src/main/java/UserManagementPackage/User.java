package UserManagementPackage;

/**
 * Representerar en användare eller låntagare i bibliotekssystemet.
 * <p>
 * Klassen innehåller grundläggande information om användaren såsom unikt ID, 
 * namn och e-postadress. Den implementerar {@link Comparable} för att möjliggöra 
 * sortering av användare i bokstavsordning baserat på deras namn.
 * </p>
 *
 * @author Adam Mashadani
 * @version 1.0
 * @since 2026
 */
public class User implements Comparable {
    
    /** Användarens unika ID-nummer (genereras eller hämtas oftast från servern). */
    private String id;
    
    /** Användarens fullständiga namn. */
    private String name;
    
    /** Användarens unika e-postadress. */
    private String email;

    /**
     * Skapar en ny instans av en användare med angivet namn och e-postadress.
     *
     * @param name  användarens fullständiga namn.
     * @param email användarens e-postadress.
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Hämtar användarens unika ID.
     *
     * @return en sträng med användarens ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Hämtar användarens fullständiga namn.
     *
     * @return en sträng med användarens namn.
     */
    public String getName() {
        return name;
    }

    /**
     * Hämtar användarens e-postadress.
     *
     * @return en sträng med användarens e-postadress.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Uppdaterar eller sätter användarens unika ID.
     *
     * @param id det nya unika ID-numret (får inte vara null eller tomt).
     * @throws IllegalArgumentException om id är null eller en tom sträng ("").
     */
    public void setId(String id) {
        if (id == null || id.equals("")) {
            throw new IllegalArgumentException("Användar-ID kan inte vara null eller tomt.");
        }
        this.id = id;
    }

    /**
     * Uppdaterar eller sätter användarens fullständiga namn.
     *
     * @param name det nya namnet (får inte vara null eller tomt).
     * @throws IllegalArgumentException om namnet är null eller en tom sträng ("").
     */
    public void setName(String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Användarens namn kan inte vara null eller tomt.");
        }
        this.name = name;
    }

    /**
     * Uppdaterar eller sätter användarens e-postadress.
     *
     * @param email den nya e-postadressen (får inte vara null eller tom).
     * @throws IllegalArgumentException om e-postadressen är null eller en tom sträng ("").
     */
    public void setEmail(String email) {
        if (email == null || email.equals("")) {
            throw new IllegalArgumentException("E-postadressen kan inte vara null eller tom.");
        }
        this.email = email;
    }

    /**
     * Genererar en textrepresentation av användarobjektet med dess fältvärden.
     *
     * @return en sträng som visar användarens ID, namn och e-postadress.
     */
    @Override
    public String toString() {
        return "Users [id=" + id + ", name=" + name + ", email=" + email + "]";
    }

    /**
     * Jämför denna användare med en annan användare baserat på namn.
     * Används för att kunna sortera användare i alfabetisk ordning.
     *
     * @param o det objekt som ska jämföras med denna instans (förväntas vara av typen Users).
     * @return ett negativt heltal, noll, eller ett positivt heltal om denna användares namn 
     *         är lexikografiskt mindre än, lika med, eller större än det angivna objektets namn.
     */
    @Override
    public int compareTo(Object o) {
        User otherUser = (User) o;
        return this.name.compareTo(otherUser.getName());
    }
}