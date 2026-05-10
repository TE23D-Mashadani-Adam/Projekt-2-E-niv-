package LibraryManagementPackage.LoanFromLibraryManagement;

public interface Borrowable {
    //Returnerar ifall publiceringen är tillgänglig eller inte
    boolean isAvailabe();
    //Sätter värdet för tillgänlighet
    void setAvailable(boolean available);
    
    String getId();
    String getTitle();
}
