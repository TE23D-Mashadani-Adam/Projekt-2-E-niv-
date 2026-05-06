package adam.mashadani;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import LibraryManagementPackage.LibraryManager;
import UserManagementPackage.UserManager;

public class Main {
    public static void main(String[] args) {

        LibraryManager lm = new LibraryManager();
        UserManager um = new UserManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
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
            System.out.println("  0. AVSLUTA PROGRAMMET");
            System.out.println("========================================================");
            System.out.print("Ditt val: ");
            String answer = scanner.nextLine();
            switch (answer) {
                case "1":
                    lm.showBooks();
                    break;
                case "2":
                    lm.showMagazines();
                    break;
                case "3":
                    System.out.println("Välj titel");
                    String title = scanner.nextLine();
                    System.out.print("Ange författare: ");
                    String author = scanner.nextLine();
                    System.out.print("Ange genre: ");
                    String genre = genre = scanner.nextLine();
                    System.out.print("Ange antal sidor: ");
                    int pages = scanner.nextInt();

                    scanner.nextLine();

                    boolean isAvailable = true;

                    lm.AddBook(title, isAvailable, author, genre, pages);

                    System.out.println("Boken har skickats till biblioteket!");
                    break;
                case "4":
                    System.out.print("Ange titel: ");
                    String title2 = scanner.nextLine();

                    System.out.print("Ange kategori (t.ex. Mode, Teknik): ");
                    String category = scanner.nextLine();

                    System.out.print("Ange utgåvonummer: ");
                    int issueNumber = scanner.nextInt();

                    System.out.print("Ange utgivningsår: ");
                    int publishYear = scanner.nextInt();

                    scanner.nextLine();
                    boolean isAvailable2 = true;

                    lm.AddMagazines(title2, isAvailable2, issueNumber, category, publishYear);

                    break;
                case "5":
                    System.out.print("Ange namn: ");
                    String name = scanner.nextLine();

                    System.out.print("Ange email: ");
                    String email = scanner.nextLine();

                    System.out.println(um.addUser(name, email));

                    break;
                case "6":
                    um.ShowUsersSorted();
                    break;
                case "7":
                    um.ShowSuspendedUsers();
                    break;
                case "8":
                    System.out.print("Ange användarens email: ");
                    String suspendedEmail = scanner.nextLine();

                    System.out.print("Ange anledning för avstängning ");
                    String suspendReason = scanner.nextLine();

                    System.out.println(um.suspendUser(suspendedEmail, suspendReason));
                    break;
                case "9":
                    System.out.println("Ange bokens titel: ");
                    String titleInput = scanner.nextLine();
                    System.out.println("Bok: " + lm.showBookByName(titleInput));
                    break;
                case "10":
                    System.out.println("Ange tidningens titel: ");
                    String titleInput2 = scanner.nextLine();
                    System.out.println("Bok: " + lm.showMagazineByName(titleInput2));
                    break;
                case "11":
                    System.out.println("Ange användarens email:");
                    String emailInput = scanner.nextLine();
                    System.out.println("Användare: " + um.ShowUserByName(emailInput));
                    break;
                case "12":
                    System.out.println("Ange bokens id:");
                    String idInputBook = scanner.nextLine();
                    lm.deleteBook(idInputBook);
                    break;
                case "13":
                    System.out.println("Ange tidningens id:");
                    String idInputMagazine = scanner.nextLine();
                    lm.deleteMagazine(idInputMagazine);
                    break;
                case "14":
                    System.out.println("Ange den avstängda användarens id:");
                    String suspendedUseridInput = scanner.nextLine();
                    um.activateSuspendedUser(suspendedUseridInput);
                    break;
                case "15":
                    System.out.println("Ange den avstängda användarens id:");
                    String userIdInput = scanner.nextLine();
                    um.deleteUser(userIdInput);
                    break;
                default:
                    break;
            }
        }
    }
}