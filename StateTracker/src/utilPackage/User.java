package utilPackage;

public class User {
	
	String userName;
	String userId;
	String userRole;
	
	public User() {
		super();
		userName = System.getProperty("user.name");
		System.out.println("User userName: " + userName);
	}
	
	
	public synchronized String getUserName() {
		return userName;
	}
	public synchronized void setUserName(String userName) {
		this.userName = userName;
	}
	public synchronized String getUserId() {
		return userId;
	}
	public synchronized void setUserId(String userId) {
		this.userId = userId;
	}
	

}
