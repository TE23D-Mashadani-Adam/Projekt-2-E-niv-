package adam.mashadani;

public class Books extends Publications{
    private String author;
    private String genre;
    private int pages;
    
    public Books(int id, String title, Boolean isAvailable, String author, String genre, int pages) {
        super(id, title, isAvailable);
        this.author = author;
        this.genre = genre;
        this.pages = pages;
    }

    @Override
    public String getInfo() {
        return null;
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

    

}
