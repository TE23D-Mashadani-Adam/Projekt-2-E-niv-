package UserManagementPackage;

public class Users implements Comparable{
    private String id;
    private String name;
    private String email;

    public Users(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    

    @Override
    public String toString() {
        return "Users [id=" + id + ", name=" + name + ", email=" + email + "]";
    }

    @Override
    public int compareTo(Object o) {
        Users otherUser = (Users) o;
        return this.name.compareTo(otherUser.getName());
    }

    

    
}
