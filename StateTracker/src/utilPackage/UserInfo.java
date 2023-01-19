package utilPackage;

public class UserInfo {
	
	private User 	user;
	private Machine machine;

	public synchronized Machine getMachine() {
		return machine;
	}

	public synchronized void setMachine(Machine machine) {
		this.machine = machine;
	}

	public UserInfo()  {
		super();
		user    = new User();
		machine = new Machine();
		
	}

	public synchronized User getUser() 
	{
		return user;
	}

	public synchronized void setUser(User user) 
	{
		this.user = user;
	}

}
