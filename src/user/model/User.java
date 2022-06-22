package user.model;

public class User 
{
	private final String userName;
    private final String userPassword;
    private final String userStatus;

    /**
     * Constructs an instance of User and set all its attributes (username,
     * password and user status)
     * @param userName
     * @param userPassword
     * @param userStatus
     */
    public User(String userName, String userPassword, String userStatus) 
    {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userStatus = userStatus;
    }

    /**
     * Gets  username of instance variable
     * @return
     */
    public String getUserName() 
    {
        return userName;
    }

    /**
     * Gets user password of instance variable
     * @return
     */
    public String getUserPassword() 
    {
        return userPassword;
    }

    /**
     * Gets user status of instance variable
     * @return
     */
    public String getUserStatus() 
    {
        return userStatus;
    }
}
