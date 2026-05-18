package LibraryManagementPackage.LoanFromLibraryManagement;

import LibraryManagementPackage.PublicationsName;
import UserManagementPackage.Users;

public class Loan {
    private String borrowedPublicationName;
    private Users user;

    

    public Loan(String borrowedPublication, Users user) {
        this.borrowedPublicationName = borrowedPublication;
        this.user = user;
    }

   
    public String getBorrowedPublicationName() {
        return borrowedPublicationName;
    }

    public Users getUser() {
        return user;
    }

    public void setBorrowedPublicationName(String borrowedPublication) {
        this.borrowedPublicationName = borrowedPublication;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    

}
