package adam.mashadani;

public class Magazines extends Publications{
    private int issueNumber;
    private String category;
    private int publishYear;

    public Magazines(int id, String title, Boolean isAvailable, int issueNumber, String category, int publishYear) {
        super(id, title, isAvailable);
        this.issueNumber = issueNumber;
        this.category = category;
        this.publishYear = publishYear;
    }

    @Override
    public String getInfo() {
        return null;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public String getCategory() {
        return category;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setIssueNumber(int issueNumber) {
        this.issueNumber = issueNumber;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    

    
}
