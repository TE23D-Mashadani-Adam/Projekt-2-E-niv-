package LibraryManagementPackage.PublicationChildClasses;

import LibraryManagementPackage.Publications;

public class Media extends Publications {
    private String type;
    private String genre;
    private int age;

    public Media(String title, Boolean isAvailable, String type, String genre, int age,
            boolean isAvailbe) {
        super(title, isAvailable);
        this.type = type;
        this.genre = genre;
        this.age = age;
    }

    @Override
    public String getInfo() {
        return "Media [id=" + this.getId() + ", type=" + type + ", title=" + this.getTitle() + ", genre=" + genre + ", age=" + age
                + ", isAvailbe=" + this.isAvailabe() + "]";
    }



    public String getType() {
        return type;
    }

    public String getGenre() {
        return genre;
    }

    public int getAge() {
        return age;
    }


    public void setType(String type) {
        this.type = type;
    }


    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String getTypeByName() {
        return this.type;
    }

    

  
    

}
