package admin;

import static utils.Utilities.closeFXMLView;
import java.io.File;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import user.model.ProcessUser;
import user.model.User;
import utils.Utilities;
import utils.Utilities;

public class AdminViewController implements Initializable, Utilities
{

    @FXML private Button uploadButton;
    @FXML private ProgressBar progressBar;
    @FXML private Label updateLabel;
    @FXML private Button viewAllUsersButton;
    @FXML private Button backToLogin;
    
    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> UserName;
    @FXML private TableColumn<User, String> UserPassword;
    @FXML private TableColumn<User, String> UserStatus;
    @FXML private Button newUserButton;
    @FXML private Button editUserButton;
    @FXML private Button toUploadViewButton;
    
    @FXML private TextField usernameField;
    @FXML private TextField userPasswordField;
    @FXML private TextField userStatusField;
    @FXML private Button saveUserButton;
    
    private AdminModel adminModelObj;
    private ProcessUser processUser;
    private AdminModel task;

    /**
     * You are currently on uploadview and want to go back to the main view
     * @param event
     */
    @FXML
    void backToMain(ActionEvent event) 
    {
    	loadFXMLView("ADMIN","/login/LoginView.fxml");
    	Utilities.closeFXMLView(uploadButton);
        killTask();
    }
    
    @FXML
    void uploadAllFiles(ActionEvent event) 
    {
    	File directory = Utilities.loadFile(); // collect folder containing data
    	 if(directory != null) 
    	 {
             updateStatus(true);

             processUser = new ProcessUser(); // initialize user instance

             Set<User> userList = processUser.getListName(); // get username

             executeTask(userList); // fire task to store babies to database
    	 }
    }
    /**
     * You are currently on uploadview and want to go back to the AllUsersView to be able to view all registered users
     * @param event
     * @throws SQLException 
     * @throws NoSuchAlgorithmException 
     */
    @FXML
    void ViewAllUsers(ActionEvent event) throws NoSuchAlgorithmException, SQLException 
    {
    	loadFXMLView("ADMIN","/admin/AllUsersView.fxml");
    	Utilities.closeFXMLView(backToLogin);
    	initialize();
    }
    /**
     * You are currently on AllUsersView and want to go to the NewUserView to be able to create the new user
     * @param event
     */
    @FXML
    void addNewUser(ActionEvent event) 
    {
    	loadFXMLView("ADMIN","/admin/NewUserView.fxml");
    	Utilities.closeFXMLView(newUserButton);
    }
    /**
     * You are currently on NewUserView and want to go to AllUserView after saving the new user
     * @param event
     */
    @FXML
    void saveUserButtonPressed(ActionEvent event)
    {
    	//The user needs to be added on the database and then we are directed to the AllUsersView
    	if(!usernameField.getText().equals("")&&!userPasswordField.getText().equals(""))
    	{
    		User user = new User(usernameField.getText(), userPasswordField.getText(), userStatusField.getText());
        	this.adminModelObj = new AdminModel(user);
        	loadFXMLView("ADMIN","/admin/AllUsersView.fxml");
        	Utilities.closeFXMLView(saveUserButton);
        	System.out.println("User added on the database");
        	initialize();
    	}
    	else
    	{
    		System.out.println("Username and/or password cannot be null...");
    	}
    	
    	
    }
    
    @FXML
    void toLoadFilesView(ActionEvent event) 
    {
    	loadFXMLView("ADMIN", "/admin/AdminView.fxml");
    	Utilities.closeFXMLView(toUploadViewButton);
    }
    /**
     * Method creates Background task given a list of babies and a list of users to be inserted
     * into database. The task is wrapped into a thread for its execution.
     * @param list
     * @param userList
     */
    private void executeTask(Set<User> userList) 
    {
        task = new AdminModel(userList);
        task.setOnSucceeded(e -> updateStatus(false));
        task.setOnCancelled(event -> System.out.println("Task cancelled"));
        setEvents();

        new Thread(task).start();
    }
    
    /**
     * Method that links the progressbar and updatelabel to task's properties (progress, message)
     */
    private void setEvents() 
    {
        progressBar.progressProperty().bind(task.progressProperty());
        updateLabel.textProperty().bind(task.messageProperty());
    }
    /**
     *  Once the user closes this window the task must be cancelled.
     */
    private void killTask() 
    {
        if(task.isRunning()) task.cancel(true);
    }
    /**
     * Method to enable/disable the visibility of progress bar and update label
     */
    private void updateStatus(boolean state) 
    {
//        progressBar.setVisible(state);
//        updateLabel.setVisible(state);
    }
    private void initialize()
    {
    	 UserName.setCellValueFactory(new PropertyValueFactory<User, String>("UserName"));
         UserPassword.setCellValueFactory(new PropertyValueFactory<User, String>("UserPassword"));
         UserStatus.setCellValueFactory(new PropertyValueFactory<User, String>("UserStatus"));
         
         try
         {
         	this.userTable.setItems(adminModelObj.fetchUsers());
         }
         catch (SQLException | NoSuchAlgorithmException e)
         {
             System.err.println(e.getMessage());
         }
    }
    /**
     * Method to initialize the Admin View with defaults
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        updateStatus(false);
        //initialize();
    }

}
