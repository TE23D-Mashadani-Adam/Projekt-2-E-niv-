package LibraryManagementPackage;

import LibraryManagementPackage.LoanFromLibraryManagement.Borrowable;

public abstract class Publications implements Comparable<Publications>, Borrowable {
    private String id;
    private String title;
    private Boolean isAvailable;

    public Publications(String title, Boolean isAvailable) {
        this.title = title;
        this.isAvailable = isAvailable;
    }

    public abstract String getInfo();

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public int compareTo(Publications other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    @Override
    public boolean isAvailabe() {
        return this.isAvailabe();
    }

    @Override
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public abstract String getTypeByName();


   
    

}
