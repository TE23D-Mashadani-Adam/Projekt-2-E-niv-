package LibraryManagementPackage.PublicationChildClasses;

import LibraryManagementPackage.PublicationsName;

public class Books extends PublicationsName {
    private String author;
    private String genre;
    private int pages;

    public Books(String title, Boolean isAvailable, String author, String genre, int pages) {
        super(title, isAvailable);
        this.author = author;
        this.genre = genre;
        this.pages = pages;
    }

    @Override
    public String getInfo() {
        return "Books [author=" + author + ", genre=" + genre + ", pages=" + pages + "]";
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public int getPages() {
        return pages;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String getTypeByName() {
        return "Boken";
    }

  
    

}
