package LibraryManagementPackage;

public class Magazines extends Publications{
    private int issueNumber;
    private String category;
    private int publishYear;

    public Magazines(String title, Boolean isAvailable, int issueNumber, String category, int publishYear) {
        super(title, isAvailable);
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

    @Override
    public int compareTo(Object arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    

    

    
}
