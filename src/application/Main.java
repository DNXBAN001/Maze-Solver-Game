package application;

import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import user.view.UserViewController;
import utils.Utilities;


/**
 * Date 22 October 2019
 * @author Bandile Danxa
 *
 */

public class Main extends Application
{

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Parent root = FXMLLoader.load(getClass().getResource("/login/LoginView.fxml"));//"/admin/NewUserView.fxml"
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
	}
	@Override
    public void stop() throws Exception
	{
        super.stop();
    }

}