package adam.mashadani;

import UserManagementPackage.Users;

import java.util.Scanner;

import LibraryManagementPackage.LibraryManager;
import LibraryManagementPackage.LoanFromLibraryManagement.LoanManager;
import LibraryManagementPackage.PublicationChildClasses.Books;
import LibraryManagementPackage.PublicationChildClasses.Magazines;
import LibraryManagementPackage.PublicationChildClasses.Media;
import UserManagementPackage.UserManager;

/**
 * Huvudklassen för bibliotekshanteringssystemet (Library Management System).
 * 
 * Denna klass fungerar som applikationens startpunkt och ansvarar för att
 * initiera centrala komponenter, såsom {@link LoanManager}, hantera menyn, samt att 
 * förbereda systemets data vid programstart.
 *
 * @author Adam Mashadani
 * @version 1.0
 * @since 2026
 */
public class Main {
    public static void main(String[] args) {

        LibraryManager lm = new LibraryManager();
        UserManager um = new UserManager();
        LoanManager loanManager = new LoanManager();
        Scanner scanner = new Scanner(System.in);

        loanManager.loadLoansFromFile(); //Läser in filen när programmet startar

        while (true) {

            displayLibraryMenu();
            String answer = scanner.nextLine();
            switch (answer) {
                case "1":
                    lm.showBooks();
                    break;
                case "2":
                    lm.showMagazines();
                    break;
                case "3":
                    handleAddBook(lm, scanner);
                    break;
                case "4":
                    handleAddMagazines(lm, scanner);
                    break;
                case "5":
                    handleAddUser(um, scanner);
                    break;
                case "6":
                    um.ShowUsersSorted();
                    break;
                case "7":
                    um.ShowSuspendedUsers();
                    break;
                case "8":
                    handleSuspendUser(um, scanner);
                    break;
                case "9":
                    handleSearchBook(lm, scanner);
                    break;
                case "10":
                    handleSearchMagazine(lm, scanner);
                    break;
                case "11":
                    handleSearchUser(um, scanner);
                    break;
                case "12":
                    handleRemoveBook(lm, scanner);
                    break;
                case "13":
                    handleRemoveMagazine(lm, scanner);
                    break;
                case "14":
                    handleActivateUser(um, scanner);
                    break;
                case "15":
                    handleRemoveUser(um, scanner);
                    break;
                case "16":
                    handleLoanPublication(scanner, lm, um, loanManager);
                    break;
                case "17":
                    handleEndLoanPublication(scanner, lm, um, loanManager);
                    break;
                case "18":
                    loanManager.showActiveLoans();
                    break;
                case "19":
                    System.out.println("Välj vilken typ media du vill söka (Måste stava rätt)");
                    String typeInput = scanner.nextLine();
                    int ageInput = getIntInput("Ange minsta ålder du vill ha för mediat:", scanner);

                    lm.showMedia(typeInput, ageInput);
                    break;
                default:
                    return; // Avslutar programmet
            }
        }

    }

    /**
     * Läser in och validerar att användaren anger ett heltal via konsolen.
     * Fångar felaktiga inmatningar för att förhindra programkrasch.
     *
     * @param prompt  meddelandet eller instruktionen som visas för användaren.
     * @param scanner det {@link Scanner}-objekt som används för inläsning.
     * @return ett giltigt heltal angivet av användaren.
     */
    private static int getIntInput(String prompt, Scanner scanner) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Felaktig inmatning. Vänligen ange siffror.");
            }
        }
    }

    /**
     * Tar emot användarinput och registrerar en ny bok i systemet.
     *
     * @param lm      den {@link LibraryManager} som boken ska läggas till i.
     * @param scanner det {@link Scanner}-objekt som används för inläsning.
     */
    private static void handleAddBook(LibraryManager lm, Scanner scanner) {
        System.out.println("Välj titel");
        String title = scanner.nextLine();
        System.out.print("Ange författare: ");
        String author = scanner.nextLine();
        System.out.print("Ange genre: ");
        String genre = scanner.nextLine();
        int pages = getIntInput("Ange antal sidor: ", scanner);

        boolean isAvailable = true;
        System.out.println(lm.AddBook(title, isAvailable, author, genre, pages));
    }

    /**
     * Tar emot användarinput och registrerar en ny tidning i systemet.
     *
     * @param lm      den {@link LibraryManager} som tidningen ska läggas till i.
     * @param scanner det {@link Scanner}-objekt som används för inläsning.
     */
    private static void handleAddMagazines(LibraryManager lm, Scanner scanner) {
        System.out.print("Ange titel: ");
        String title2 = scanner.nextLine();
        System.out.print("Ange kategori: ");
        String category = scanner.nextLine();
        int issueNumber = getIntInput("Ange utgåvonummer: ", scanner);
        int publishYear = getIntInput("Ange utgivningsår: ", scanner);

        boolean isAvailable2 = true;
        System.out.println(lm.AddMagazines(title2, isAvailable2, issueNumber, category, publishYear));
    }

    /**
     * Tar emot användarinput och registrerar en ny biblioteksanvändare.
     *
     * @param um      den {@link UserManager} som användaren ska läggas till i.
     * @param scanner det {@link Scanner}-objekt som används för inläsning.
     */
    private static void handleAddUser(UserManager um, Scanner scanner) {
        System.out.print("Ange namn: ");
        String name = scanner.nextLine();
        System.out.print("Ange email: ");
        String email = scanner.nextLine();
        System.out.println(um.addUser(name, email));
    }

    /**
     * Spärrar/stänger av en användare baserat på angiven e-postadress och anledning.
     *
     * @param um      den {@link UserManager} som hanterar avstängningen.
     * @param scanner det {@link Scanner}-objekt som används för inläsning.
     */
    private static void handleSuspendUser(UserManager um, Scanner scanner) {
        System.out.print("Ange användarens email: ");
        String suspendedEmail = scanner.nextLine();
        System.out.print("Ange anledning: ");
        String suspendReason = scanner.nextLine();
        um.suspendUser(suspendedEmail, suspendReason);
    }

    /**
     * Söker efter en specifik bok i systemet via dess titel och visar resultatet.
     *
     * @param lm      den {@link LibraryManager} där sökningen ska utföras.
     * @param scanner det {@link Scanner}-objekt som används för inläsning.
     */
    private static void handleSearchBook(LibraryManager lm, Scanner scanner) {
        System.out.print("Ange bokens titel: ");
        String titleInput = scanner.nextLine();
        System.out.println("Bok: " + lm.showBookByName(titleInput));
    }

    /**
     * Söker efter en specifik tidning i systemet via dess titel och visar resultatet.
     *
     * @param lm      den {@link LibraryManager} där sökningen ska utföras.
     * @param scanner det {@link Scanner}-objekt som används för inläsning.
     */
    private static void handleSearchMagazine(LibraryManager lm, Scanner scanner) {
        System.out.print("Ange tidningens titel: ");
        String titleInput2 = scanner.nextLine();
        System.out.println("Tidning: " + lm.showMagazineByName(titleInput2));
    }

    /**
     * Söker efter en användare via e-postadress och visar användarens detaljer.
     *
     * @param um      den {@link UserManager} där sökningen ska utföras.
     * @param scanner det {@link Scanner}-objekt som används för inläsning.
     */
    private static void handleSearchUser(UserManager um, Scanner scanner) {
        System.out.print("Ange email: ");
        String emailInput = scanner.nextLine();
        System.out.println("Användare: " + um.ShowUserByName(emailInput));
    }

    /**
     * Tar bort en bok från bibliotekssystemet baserat på dess ID-nummer.
     *
     * @param lm      den {@link LibraryManager} som hanterar raderingen av boken.
     * @param scanner det {@link Scanner}-objekt som används för inläsning.
     */
    private static void handleRemoveBook(LibraryManager lm, Scanner scanner) {
        System.out.print("Ange bokens id: ");
        String idInputBook = scanner.nextLine();
        lm.deleteBook(idInputBook);
    }

    /**
     * Tar bort en tidning från bibliotekssystemet baserat på dess ID-nummer.
     *
     * @param lm      den {@link LibraryManager} som hanterar raderingen av tidningen.
     * @param scanner det {@link Scanner}-objekt som används för inläsning.
     */
    private static void handleRemoveMagazine(LibraryManager lm, Scanner scanner) {
        System.out.print("Ange tidningens id: ");
        String idInputMagazine = scanner.nextLine();
        lm.deleteMagazine(idInputMagazine);
    }

    /**
     * Aktiverar en tidigare spärrad användare i systemet med hjälp av avstängnings-ID.
     *
     * @param um      den {@link UserManager} som hanterar kontoaktiveringen.
     * @param scanner det {@link Scanner}-objekt som används för inläsning.
     */
    private static void handleActivateUser(UserManager um, Scanner scanner) {
        System.out.print("Ange avstängnings-id: ");
        String suspendedUseridInput = scanner.nextLine();
        um.activateSuspendedUser(suspendedUseridInput);
    }

    /**
     * Raderar en användarprofil permanent från systemet baserat på användar-ID.
     *
     * @param um      den {@link UserManager} som hanterar raderingen.
     * @param scanner det {@link Scanner}-objekt som används för inläsning.
     */
    private static void handleRemoveUser(UserManager um, Scanner scanner) {
        System.out.print("Ange användarens id: ");
        String userIdInput = scanner.nextLine();
        um.deleteUser(userIdInput);
    }

    /**
     * Hanterar flödet för att avsluta och återlämna en publikation (bok, tidning eller media).
     * Kontrollerar att användaren och publikationen existerar innan lånet avslutas via {@link LoanManager}.
     *
     * @param scanner     det {@link Scanner}-objekt som används för inläsning.
     * @param lm          den {@link LibraryManager} som innehåller publikationerna.
     * @param um          den {@link UserManager} som innehåller användarna.
     * @param loanManager den {@link LoanManager} som hanterar låneregistret.
     */
    private static void handleEndLoanPublication(Scanner scanner, LibraryManager lm, UserManager um,
            LoanManager loanManager) {
        int loanAnswer2 = getIntInput("Ange vilken typ du vill återlämna:"
                + "\n" + "1. Bok" + "\n" + "2. Tidning" + "\n" + "3. Media" + "\n", scanner);
        System.out.println("Ange din email:");
        String emailInput = scanner.nextLine();
        Users user = um.findUser(emailInput);

        if (user == null) {
            System.out.println("Användaren hittades inte, kontrollera din stavning");
        } else {
            if (loanAnswer2 == 1) {
                System.out.println("Ange bokens namn:");
                String title = scanner.nextLine();
                Books book = lm.getBookByName(title);

                if (book != null) {
                    loanManager.endLoan(user, book, "books");
                } else {
                    System.out.println("Boken hittades inte i systemet, kontrollera stavning");
                }

            } else if (loanAnswer2 == 2) {
                System.out.println("Ange tidningens namn:");
                String title = scanner.nextLine();
                Magazines magazine = lm.getMagazineByName(title);

                if (magazine != null) {
                    loanManager.endLoan(user, magazine, "magazines");
                } else {
                    System.out.println("Tidningen hittades inte i systemet, kontrollera stavning");
                }

            } else if (loanAnswer2 == 3) {
                System.out.println("Ange mediets namn:");
                String title = scanner.nextLine();
                Media mediaItem = lm.getMediaByName(title);

                if (mediaItem != null) {
                    loanManager.endLoan(user, mediaItem, "media");
                } else {
                    System.out.println("Mediet hittades inte i systemet, kontrollera stavning");
                }
            } else {
                System.out.println("Ogiltigt val, ange snälla en siffra 1-3");
            }
        }
    }

    /**
     * Hanterar flödet för att registrera ett nytt lån av en publikation (bok, tidning eller media).
     * Kontrollerar giltigheten för både användaren och mediet innan lånet verkställs.
     *
     * @param scanner     det {@link Scanner}-objekt som används för inläsning.
     * @param lm          den {@link LibraryManager} som innehåller publikationerna.
     * @param um          den {@link UserManager} som innehåller användarna.
     * @param loanManager den {@link LoanManager} som registrerar lånet.
     */
    private static void handleLoanPublication(Scanner scanner, LibraryManager lm, UserManager um,
            LoanManager loanManager) {
        while(true){
        int loanAnswer = getIntInput("Ange vilken typ du vill låna:"
                + "\n" + "1. Bok" + "\n" + "2. Tidning" + "\n" + "3. Media" + "\n", scanner);
        if (loanAnswer == 1) {
            System.out.println("Ange bokens namn:");
            String loanBookAnswer = scanner.nextLine();
            System.out.println("Ange din email:");
            String bokEmailAnswer = scanner.nextLine();

            Users user = um.findUser(bokEmailAnswer);
            Books book = lm.getBookByName(loanBookAnswer);

            if (user != null && book != null) {
                loanManager.registerLoan(user, book, "books");
            }
            return;
        } else if (loanAnswer == 2) {
            System.out.println("Ange tidningens namn:");
            String loanMagazineAnswer = scanner.nextLine();
            System.out.println("Ange din email:");
            String magEmailAnswer = scanner.nextLine();

            Users user = um.findUser(magEmailAnswer);
            Magazines magazine = lm.getMagazineByName(loanMagazineAnswer);

            if (user != null && magazine != null) {
                loanManager.registerLoan(user, magazine, "magazines");
            } else {
                System.out.println("Kunde inte hitta användare eller tidning.");
            }
            return;
        } else if (loanAnswer == 3) {
            System.out.println("Ange mediets namn:");
            String loanMediaAnswer = scanner.nextLine();
            System.out.println("Ange din email:");
            String mediaEmailAnswer = scanner.nextLine();

            Users user = um.findUser(mediaEmailAnswer);
            Media media = lm.getMediaByName(loanMediaAnswer);

            if (user != null && media != null) {
                loanManager.registerLoan(user, media, "media");
            } else {
                System.out.println("Kunde inte hitta användare eller media.");
            }
            return;
        }else{
            System.out.println("Ange en siffra 1-3 är du snäll");
        }
    }
    }

    /**
     * Skriver ut administrationsmenyn och dess valmöjligheter till konsolen.
     */
    private static void displayLibraryMenu() {
        System.out.println("\n========================================================");
        System.out.println("                BIBLIOTEKSSYSTEM - ADMIN                ");
        System.out.println("========================================================");
        System.out.println("  1. Visa alla böcker           9.  Sök bok (titel)");
        System.out.println("  2. Visa alla tidningar        10. Sök tidning (titel)");
        System.out.println("  3. Lägg till bok              11. Sök användare (email)");
        System.out.println("  4. Lägg till tidning          12. [!] TA BORT BOK");
        System.out.println("                                13. [!] TA BORT TIDNING");
        System.out.println("--------------------------------------------------------");
        System.out.println("  5. Registrera användare       6.  Visa alla användare");
        System.out.println("  7. Visa avstängda             8.  Spärra användare");
        System.out.println("  14. Aktivera spärrad användare 15. [!] TA BORT ANVÄNDARE");
        System.out.println("--------------------------------------------------------");
        System.out.println("  16. LÅNA                      17. ÅTERLÄMNA");
        System.out.println("  18. Visa alla aktiva lån      19. Visa media");
        System.out.println("--------------------------------------------------------");
        System.out.println("  0. AVSLUTA PROGRAMMET");
        System.out.println("========================================================");
        System.out.print("Ditt val: ");
    }

}