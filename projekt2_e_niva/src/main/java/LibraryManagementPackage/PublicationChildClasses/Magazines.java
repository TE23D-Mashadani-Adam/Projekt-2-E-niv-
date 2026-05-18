package LibraryManagementPackage.PublicationChildClasses;

import LibraryManagementPackage.PublicationsName;

public class Magazines extends PublicationsName{
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
        return "Magazines [issueNumber=" + issueNumber + ", category=" + category + ", publishYear=" + publishYear
                + "]";
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
    public String getTypeByName() {
        return "Tidningen";
    }

  


    

    

    
}
