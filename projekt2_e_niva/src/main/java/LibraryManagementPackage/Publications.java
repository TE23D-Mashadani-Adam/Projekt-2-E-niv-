package LibraryManagementPackage;

public abstract class Publications implements Comparable {
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

}
