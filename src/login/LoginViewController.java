package login;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import utils.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import login.LoginModel;
import user.model.User;
import user.view.UserViewController;

public class LoginViewController implements Utilities
{

    @FXML
    private Button loginButton;
    @FXML
    private Button signUpButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginStatus;
    
    private LoginModel task;
    private SignUpModel task2;

	
    
    /**
     * This method will process the credentials of the user (Admin or ordinary user) in order
     * to grant them access to the appropriate user interface.
     * @param event
     */
    @FXML
    public void processLogin(ActionEvent event)
    {
       executeTask();
    }
    @FXML
    void processSignUp(ActionEvent event)
    {
    	
    	//Create a new user to pass on the database so they can be added when the user presses the sign up button
    	//By default the status of the newly added user should be 'user'
    	if(usernameField.getText().equals("")&&passwordField.getText().equals(""))
    	{
    		System.out.println("Username and/or password field are blank");
    		loginStatus.setText("Username and/or password field are blank");
    	}
    	else
    	{	
    		
    		User newUser = new User(usernameField.getText(),passwordField.getText(),"user");
        	task2 = new SignUpModel(newUser);
            loginStatus.setText("Sign up was succesful, now you can Log in");
    	}
    }
    
	/**
     *  Creates a background with user's details to authenticate against database. The background is
     *  wrapped into a thread for its execution.
     */
    private void executeTask()
    {
        task = new LoginModel(new User(usernameField.getText(),passwordField.getText(),""));

        task.setOnSucceeded(e -> processUser()); // set method to execute on task completion
        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(task);
        service.shutdown();
    }

    /**
     * Collects user from database if he/she exists.
     */
    private void processUser() 
    {
        User user = task.getValue(); // get user from background task on completion

        if(user != null)
        {
        	//if the status of the user is 'admin' then load the admin view
            if (user.getUserStatus().equalsIgnoreCase("admin"))
                loadAdminView(usernameField.getText(), "/admin/AllUsersView.fxml");//"/admin/AdminView.fxml"
            
        
          //if the status of the user is 'user' then load the user view
            else if (user.getUserStatus().equalsIgnoreCase("user"))
                loadUserView("Ant Maze Solver: Wall Follower", new UserViewController());
            
            return;
        }
        Utilities.showSimpleMessage("Login Failure", "Username or password incorrect");
    }

	/**
	 * This method will close the Login interface in order to make way for the User interface
	 * which will allow any end-user to interact with it. 
	 * @throws IOException
	 */
    private void loadUserView(String title, Scene userview) 
    {
    	Stage secondaryStage = new Stage();
    	secondaryStage.setScene(new UserViewController());
    	secondaryStage.show();
        Utilities.closeFXMLView(loginButton);//close the login window
        System.out.println("Ordinary User view has been activated");
    }

    /**
     * This method will close the Login interface and make a way for the Admin interface
     * which will allow only the Admin to interact with it.
     * @throws IOException
     */
    private void loadAdminView(String username, String path)
    {
    	loadFXMLView(username, path);
        Utilities.closeFXMLView(loginButton);//close the login window
        System.out.println("Admin view has been activated");
    }
	
}
