package adam.mashadani;

import java.util.Scanner;

import LibraryManagementPackage.LibraryManager;
import UserManagementPackage.UserManager;

public class Main {
    public static void main(String[] args) {
        LibraryManager lm = new LibraryManager();
        UserManager um = new UserManager();
        Scanner scanner = new Scanner(System.in);

        
        
        while (true) {
            System.out.println("Välkommen till meny, välj bland alternativen " + "\n" + "1. Skriv ut böcker"
                    + "\n" + "2. Skriv ut tidnignar" + "\n" + "3. Lägg till en bok" + "\n"
                    + "4. Lägg till en tidning" + "\n" + "5. Avsluta");
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

                    System.out.println("\nTidningen har registrerats!");
                    break;
                case "5":
                    break;
                default:
                    break;
            }
        }
    }
}