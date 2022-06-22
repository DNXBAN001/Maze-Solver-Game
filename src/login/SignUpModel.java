package login;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseHandle;
import javafx.concurrent.Task;
import user.model.User;
import utils.Utilities;

public class SignUpModel
{
	private User user;
    private DatabaseHandle database;

    /**
     * Creates Task instance and initializes the current user
     * @param user
     */
    public SignUpModel(User user)
    {
        this.user = user;
        this.database = DatabaseHandle.getInstance(user);
        System.out.println("Username : "+user.getUserName());
        System.out.println("Password : "+user.getUserPassword());
        System.out.println("User Status : "+user.getUserStatus());
    }
}
