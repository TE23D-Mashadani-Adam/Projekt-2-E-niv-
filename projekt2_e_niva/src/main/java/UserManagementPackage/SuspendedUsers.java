package UserManagementPackage;

public class SuspendedUsers implements Comparable{
    private String id;
    private String userId;
    private String reason;

    public SuspendedUsers(String userId, String reason) {
        this.userId = userId;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
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
