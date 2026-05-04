package UserManagementPackage;

public class SuspendedUsers implements Comparable{
    private String id;
    private int userId;
    private String reason;

    public SuspendedUsers(String id, int userId, String reason) {
        this.id = id;
        this.userId = userId;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public int compareTo(Object arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    
    
    
}
