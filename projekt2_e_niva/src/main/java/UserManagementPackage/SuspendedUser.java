package UserManagementPackage;

/**
 * Representerar en avstängd eller spärrad användare i systemet.
 * <p>
 * Klassen kopplar samman ett unikt avstängnings-ID med användarens specifika ID 
 * samt en motivering till varför användaren har blivit avstängd. Den implementerar
 * {@link Comparable} för att möjliggöra sortering eller jämförelser av avstängningsposter.
 * </p>
 *
 * @author Adam Mashadani
 * @version 1.0
 * @since 2026
 */
public class SuspendedUser implements Comparable {
    
    /** Det unika ID-numret för själva avstängningsposten på servern. */
    private String id;
    
    /** Det unika ID-numret för den användare som är avstängd. */
    private String userId;
    
    /** Orsaken eller motiveringen till avstängningen. */
    private String reason;

    /**
     * Skapar en ny instans av en avstängd användare med angivet användar-ID och orsak.
     *
     * @param userId det unika ID-numret för den användare som ska stängas av.
     * @param reason anledningen till avstängningen.
     */
    public SuspendedUser(String userId, String reason) {
        this.userId = userId;
        this.reason = reason;
    }

    /**
     * Hämtar det unika ID:t för denna specifika avstängningspost.
     *
     * @return en sträng med avstängningspostens ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Hämtar ID:t för den användare som är avstängd.
     *
     * @return en sträng med användarens ID.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Hämtar orsaken eller motiveringen till avstängningen.
     *
     * @return en sträng med orsaken.
     */
    public String getReason() {
        return reason;
    }

    /**
     * Uppdaterar eller sätter ID:t för denna specifika avstängningspost.
     *
     * @param id det nya unika ID-numret för posten (får inte vara null eller tomt).
     * @throws IllegalArgumentException om id är null eller en tom sträng ("").
     */
    public void setId(String id) {
        if (id == null || id.equals("")) {
            throw new IllegalArgumentException("Avstängningspostens ID kan inte vara null eller tomt.");
        }
        this.id = id;
    }

    /**
     * Uppdaterar eller sätter ID:t för den avstängda användaren.
     *
     * @param userId det nya unika ID-numret för användaren (får inte vara null eller tomt).
     * @throws IllegalArgumentException om userId är null eller en tom sträng ("").
     */
    public void setUserId(String userId) {
        if (userId == null || userId.equals("")) {
            throw new IllegalArgumentException("Användar-ID kan inte vara null eller tomt.");
        }
        this.userId = userId;
    }

    /**
     * Uppdaterar eller sätter orsaken till avstängningen.
     *
     * @param reason den nya motiveringen (får inte vara null eller en tom sträng).
     * @throws IllegalArgumentException om anledningen är null eller en tom sträng ("").
     */
    public void setReason(String reason) {
        if (reason == null || reason.equals("")) {
            throw new IllegalArgumentException("Orsaken till avstängningen kan inte vara null eller tom.");
        }
        this.reason = reason;
    }

    /**
     * Jämför denna avstängningspost med ett annat objekt.
     *
     * @param arg0 det objekt som ska jämföras med denna instans.
     * @return ett värde som indikerar ordningen (för närvarande alltid 0).
     */
    @Override
    public int compareTo(Object arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
}