package adam.mashadani;

public abstract class Publications {
    private int id;
    private String title;
    private Boolean isAvailable;

    public Publications(int id, String title, Boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.isAvailable = isAvailable;
    }

    public abstract String getInfo();

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

}
