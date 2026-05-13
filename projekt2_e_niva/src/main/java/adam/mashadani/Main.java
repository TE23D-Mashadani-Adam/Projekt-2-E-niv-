package adam.mashadani;

import UserManagementPackage.Users;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import LibraryManagementPackage.LibraryManager;
import LibraryManagementPackage.LoanFromLibraryManagement.LoanManager;
import LibraryManagementPackage.PublicationChildClasses.Books;
import LibraryManagementPackage.PublicationChildClasses.Magazines;
import LibraryManagementPackage.PublicationChildClasses.Media;
import UserManagementPackage.UserManager;

public class Main {
    public static void main(String[] args) {

        LibraryManager lm = new LibraryManager();
        UserManager um = new UserManager();
        LoanManager loanManager = new LoanManager();
        Scanner scanner = new Scanner(System.in);

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
                default:
                    return; // Avslutar programmet
            }
        }

    }

    // Kontrollerar att användaren skrivit rätt integer input för att undvika
    // programkrasch
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

    private static void handleAddUser(UserManager um, Scanner scanner) {
        System.out.print("Ange namn: ");
        String name = scanner.nextLine();
        System.out.print("Ange email: ");
        String email = scanner.nextLine();
        System.out.println(um.addUser(name, email));
    }

    private static void handleSuspendUser(UserManager um, Scanner scanner) {
        System.out.print("Ange användarens email: ");
        String suspendedEmail = scanner.nextLine();
        System.out.print("Ange anledning: ");
        String suspendReason = scanner.nextLine();
        um.suspendUser(suspendedEmail, suspendReason);
    }

    private static void handleSearchBook(LibraryManager lm, Scanner scanner) {
        System.out.print("Ange bokens titel: ");
        String titleInput = scanner.nextLine();
        System.out.println("Bok: " + lm.showBookByName(titleInput));
    }

    private static void handleSearchMagazine(LibraryManager lm, Scanner scanner) {
        System.out.print("Ange tidningens titel: ");
        String titleInput2 = scanner.nextLine();
        System.out.println("Tidning: " + lm.showMagazineByName(titleInput2));
    }

    private static void handleSearchUser(UserManager um, Scanner scanner) {
        System.out.print("Ange email: ");
        String emailInput = scanner.nextLine();
        System.out.println("Användare: " + um.ShowUserByName(emailInput));
    }

    private static void handleRemoveBook(LibraryManager lm, Scanner scanner) {
        System.out.print("Ange bokens id: ");
        String idInputBook = scanner.nextLine();
        lm.deleteBook(idInputBook);
    }

    private static void handleRemoveMagazine(LibraryManager lm, Scanner scanner) {
        System.out.print("Ange tidningens id: ");
        String idInputMagazine = scanner.nextLine();
        lm.deleteMagazine(idInputMagazine);
    }

    private static void handleActivateUser(UserManager um, Scanner scanner) {
        System.out.print("Ange avstängnings-id: ");
        String suspendedUseridInput = scanner.nextLine();
        um.activateSuspendedUser(suspendedUseridInput);
    }

    private static void handleRemoveUser(UserManager um, Scanner scanner) {
        System.out.print("Ange användarens id: ");
        String userIdInput = scanner.nextLine();
        um.deleteUser(userIdInput);
    }

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

    private static void handleLoanPublication(Scanner scanner, LibraryManager lm, UserManager um,
            LoanManager loanManager) {
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
        }
    }

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
        System.out.println("  16. LÅNA       17. ÅTERLÄMNA");
        System.out.println("  18. Visa alla aktiva lån");
        System.out.println("--------------------------------------------------------");
        System.out.println("  0. AVSLUTA PROGRAMMET");
        System.out.println("========================================================");
        System.out.print("Ditt val: ");
    }

}
