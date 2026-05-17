package LibraryManagementPackage.LoanFromLibraryManagement;

import LibraryManagementPackage.Publications;
import UserManagementPackage.Users;

public class Loan {
    private Publications borrowedPublication;
    private Users user;

    

    public Loan(Publications borrowedPublication, Users user) {
        this.borrowedPublication = borrowedPublication;
        this.user = user;
    }

    public Loan(){}

    public Publications getBorrowedPublication() {
        return borrowedPublication;
    }

    public Users getUser() {
        return user;
    }

    public void setBorrowedPublication(Publications borrowedPublication) {
        this.borrowedPublication = borrowedPublication;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    

}
