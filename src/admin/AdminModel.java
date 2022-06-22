package admin;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import user.model.User;
import database.DatabaseHandle;

public class AdminModel extends Task<Boolean>
{
	
	private Set<User> listOfAllUsers;
	private User user;
    private DatabaseHandle database;
    private long count = 0; // keeps count of many data has been processed with respect to size

    /**
     * Constructs the Background Task given the list of babies to store into database as well
     * as a list of users to be inserted into database
     * @param babies
     * @param userList
     */
    public AdminModel(Set<User> userList)
	{
        this.listOfAllUsers = userList; // initialize list of users to store into database
    }
    public AdminModel(User user)
   	{
           this.user = user; // initialize a user to store into database
           database = DatabaseHandle.getInstance(this.user);
           System.out.println("Username : "+this.user.getUserName());
           System.out.println("User Password : "+this.user.getUserPassword());
       	   System.out.println("User Status : "+this.user.getUserStatus());
    }
	    
    /**
     * Method uploads all users received into database
     * @param list
     */
    private void uploadUsersToDatabase(Set<User> list) 
	{
        list.stream().parallel()
                .forEach(this::storeUsersIntoDb);
        count = 0;
    }    
	    
	 /**
     * Method inserts ONE user into database and updates progressbar and label on the GUI with
     * the current user details (username, password, user status)
     * @param user
     */
    private void storeUsersIntoDb(User user) 
	{
        updateProgress(this.count,listOfAllUsers.size()); this.count++;
        updateMessage(user.getUserName()+ " " + user.getUserPassword() + " " + user.getUserStatus());
        database.Insert(user);
    }
    /**
     * This method will load the users from the database and load them into 
     * the TableView object
	 * @throws SQLException 
	 * @throws NoSuchAlgorithmException 
     */
	public ObservableList<User> fetchUsers() throws SQLException, NoSuchAlgorithmException 
	{
	   ObservableList<User> allUsers = FXCollections.observableArrayList();
	   ResultSet result = database.Select("SELECT * FROM users ");
	            
        // create users objects from each record
        while (result.next())
        {
            
        	User user = new User (result.getString(1), result.getString(2), result.getString(3)); 
            allUsers.add(user);
        }
       
        //userTable.setItems(allUsers);
        return allUsers;
	}

	/**
     * Core Method that execute the Background Task.
     * @return
     */
    @Override
    protected Boolean call() 
	{
        uploadUsersToDatabase(listOfAllUsers);
        return true;
    }

}
