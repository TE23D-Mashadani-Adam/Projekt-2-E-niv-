package LibraryManagementPackage.PublicationChildClasses;

import LibraryManagementPackage.Publications;

public class Media extends Publications {
    private String id;
    private String type;
    private String title;
    private String genre;
    private int age;
    private boolean isAvailbe;

    public Media(String title, Boolean isAvailable, String id, String type, String title2, String genre, int age,
            boolean isAvailbe) {
        super(title, isAvailable);
        this.id = id;
        this.type = type;
        title = title2;
        this.genre = genre;
        this.age = age;
        this.isAvailbe = isAvailbe;
    }

    @Override
    public String getInfo() {
        return "Media [id=" + id + ", type=" + type + ", title=" + title + ", genre=" + genre + ", age=" + age
                + ", isAvailbe=" + isAvailbe + "]";
    }

  
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getAge() {
        return age;
    }

    public boolean isAvailbe() {
        return isAvailbe;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAvailbe(boolean isAvailbe) {
        this.isAvailbe = isAvailbe;
    }

  
    

}
