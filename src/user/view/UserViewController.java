package user.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import utils.Direction;
import utils.Utilities;

/**
 * 
 * @author Barnez
 *
 */
public class UserViewController extends Scene implements Utilities
{

	private Ant ant;
	private Pane pane;
	private int x = 10, y= 1;
	private Maze maze;
	private Direction direction = Direction.LEFT;
	private Group myAnt;
	
	private Button manualButton;
	private Button autoButton;
	private Button stopButton;
	private Button chooseMazeButton;
	private HBox buttonControls;
	private VBox vbox;
	
	private Label pointsLabel = new Label(), timerLabel = new Label();
	
	private String path; // path of file selected
	private FileChooser fileChooser; // file dialog box
	private String fileContent;
	
	private Timeline animation;
//	private Timeline animation = new Timeline(new KeyFrame(Duration.seconds(.2), new HandleActionEvent()));//for animating our ant
	
	public UserViewController()
	{	
		super(new Pane());
		this.pane = new Pane();
		this.setRoot(pane);
		
		this.ant = new Ant();//Instantiate the ant
		this.myAnt = ant.getAnt();
		createContent();

		//Handle the events of the buttons
		manualButton.setOnAction(e -> new HandleKeyEvent());
		autoButton.setOnAction(e -> new HandleActionEvent());
		chooseMazeButton.setOnAction(e -> {
											try 
											{
												openUpload();
											}
											catch(IOException e1)
											{
												e1.printStackTrace();
											}
									});
	}
	/**
     * This method receives the event from chooseMaze button and process
     *  with the file that is selected.
	 * @throws IOException 
     */
	public void openUpload() throws IOException
	{
		
		fileChooser = defaultDirectory(fileChooser);
		path = getFileOrDirectoryPath();
		if(path != null)
		{
			openFile();
			MazeModel mazeModelObj = new MazeModel(fileContent);
		}

	}
	
	/**
     * This method displays a file dialog box and the get the path of
     * the selected file.
     * @return a <code> String</code> specifying file's path.
     */
	private String getFileOrDirectoryPath()
	{
		File file = null;
		file = this.fileChooser.showOpenDialog(null);

		return file != null?file.getAbsolutePath(): null;
	}
	/**
	 * This method opens the selected file using the Scanner's object
	 * @throws IOException 
	 */
	private void openFile() throws IOException
	{
		try(Stream<String> stream = Files.lines(Paths.get(path)))
		{
			fileContent = stream.collect(Collectors.joining("\n"));//set file content
			//System.out.println(fileContent);
		}
		catch (FileNotFoundException e)
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Open File Failure");
			alert.setContentText(e.getMessage());
			alert.show();
		}
	}
	/**
	 * @param x
	 * @param y
	 */
	public void drawTranslated(int x, int y)
	{
		this.myAnt.relocate(x*25, y*25);
	}
	
	/**
	 * this method is for re-populating the scene
	 */
	private void createContent()
	{
		this.maze = new Maze(fileContent);
		this.vbox = new VBox(10);
		this.buttonControls = new HBox(10);
		populateGUI();
	}
	private void populateGUI()
	{
		this.pane.getChildren().clear();
		this.vbox.getChildren().clear();
		this.buttonControls.getChildren().clear();
		
		this.manualButton = new Button("Manual Mode");
		this.autoButton = new Button("Auto Mode");
		this.stopButton = new Button("Stop");
		this.chooseMazeButton = new Button("Choose Maze");
			
		this.ant.drawTranslated(x, y);//put ant in the x, y position
		
		this.buttonControls.getChildren().addAll(manualButton, autoButton, stopButton, chooseMazeButton);//put buttons in a horizontal box
		
		this.vbox.getChildren().addAll(this.maze, this.buttonControls, timerLabel, pointsLabel);
		this.pane.getChildren().addAll(this.myAnt, this.vbox);
	}
	
	
	/**
	 * This method checks if the ant has reached the destination
	 * If it has reached the end location the animation must be stopped
	 */
	private void hasReachedDestination()
	{
		if(this.maze.isEndLocation(x, y))
			{animation.stop(); System.out.println("Destination reached");}
	}
	private boolean destinationReached()
	{
		if(this.maze.isEndLocation(x, y))
			{System.out.println("Destination reached");return true;}
		return false;
	}

	/** 
	 * This method is for continue the ant moving in the forward direction
	 * @param face
	 */
	private void moveForward()
	{	
		if(direction == Direction.LEFT)
			this.drawTranslated(x--, y);
		
		else if (direction == Direction.RIGHT)
			this.drawTranslated(x++, y);

		else if(direction ==  Direction.UP)
			this.drawTranslated(x, y--);

		else if(direction ==  Direction.DOWN)
			this.drawTranslated(x, y++);
	}
	
	
	/**
	 * This method defines the left-wall follower algorithm
	 * 
	 */
	private void followLeftWall()
	{
		//check if the ant is facing to the left and takes actions based on that
		if(this.myAnt.getRotate()==0)//is facing left?
		{
			if(this.maze.isOpenPath(x, y+1))//check left if is open path
				{this.myAnt.setRotate(-90);//turn left
				direction = Direction.DOWN; moveForward();}
			else if(this.maze.isOpenPath(x-1, y))//check front
				moveForward();
			else if(this.maze.isOpenPath(x, y-1))//check right
				{this.myAnt.setRotate(90);//turn right
				direction = Direction.UP;moveForward();}
			else if(this.maze.isOpenPath(x+1, y))
			{this.myAnt.setRotate(180); direction = Direction.RIGHT; moveForward();}	
		}
		
		else if(this.myAnt.getRotate()==90)//check if ant is facing up first
		{
			if(this.maze.isOpenPath(x-1, y))//check left
				{this.myAnt.setRotate(0);//turn left
				direction = Direction.LEFT; moveForward();}
			else if(this.maze.isOpenPath(x, y-1))//check front
				moveForward();
			else if(this.maze.isOpenPath(x+1, y))//check right
				{this.myAnt.setRotate(180);//turn right
				direction = Direction.RIGHT; moveForward();}
			else if(this.maze.isOpenPath(x, y+1))
				{this.myAnt.setRotate(-90); direction = Direction.DOWN; moveForward();}
		}
		
		else if(this.myAnt.getRotate()==180)//check if ant is facing right
		{
			if(this.maze.isOpenPath(x, y-1))//check left
				{this.myAnt.setRotate(90);//turn left
				direction = Direction.UP; moveForward();}
			else if(this.maze.isOpenPath(x+1, y))//check front
				moveForward();
			else if(this.maze.isOpenPath(x, y+1))//check right
				{this.myAnt.setRotate(-90);//turn right
				direction = Direction.DOWN; moveForward();}
			else if(this.maze.isOpenPath(x-1, y))
				{this.myAnt.setRotate(0); direction = Direction.LEFT; moveForward();}
		}
		
		else if(this.myAnt.getRotate()==-90)//check if ant is facing down
		{
			if(this.maze.isOpenPath(x+1, y))//check left
				{this.myAnt.setRotate(180);//turn left
				direction = Direction.RIGHT; moveForward();}
			else if(this.maze.isOpenPath(x, y+1))//check front
				moveForward();
			else if(this.maze.isOpenPath(x-1, y))//check right
				{this.myAnt.setRotate(0);//turn right
				direction = Direction.LEFT; moveForward();}
			else if(this.maze.isOpenPath(x, y-1))
				{this.myAnt.setRotate(90); direction = Direction.UP; moveForward();}
		}
	}

	/**
	 * This method defines the right-wall follower algorithm
	 * 
	 */
	private void followRightWall()
	{
		//check if the ant is facing to the left and takes actions based on that
		if(this.myAnt.getRotate()==0)//check if the ant is facing left
		{
			if(this.maze.isOpenPath(x, y-1))//check right cell if is open
				{this.myAnt.setRotate(90);//turn right
				direction = Direction.UP; moveForward();}
			else if(this.maze.isOpenPath(x-1, y))//check front cell
				moveForward();
			else if(this.maze.isOpenPath(x, y+1))//check left cell
				{this.myAnt.setRotate(-90);//turn left
				direction = Direction.DOWN; moveForward();}
			else
				{this.myAnt.setRotate(180); direction = Direction.RIGHT; moveForward();}
		}
		
		else if(this.myAnt.getRotate()==90)//check if ant is facing up
		{
			if(this.maze.isOpenPath(x+1, y))//check right
				{this.myAnt.setRotate(180);//turn right
				direction = Direction.RIGHT; moveForward();}
			
			else if(this.maze.isOpenPath(x, y-1))//check front
				moveForward();
			else if(this.maze.isOpenPath(x-1, y))//check left
				{this.myAnt.setRotate(0);//turn left
				direction = Direction.LEFT; moveForward();}
			else
				{this.myAnt.setRotate(-90); direction = Direction.DOWN; moveForward();}
		}
		
		else if(this.myAnt.getRotate()==180)//check if ant is facing right
		{
			if(this.maze.isOpenPath(x, y+1))//check right
				{this.myAnt.setRotate(-90);//turn right
				direction = Direction.DOWN; moveForward();}
			else if(this.maze.isOpenPath(x+1, y))//check front
				moveForward();
			else if(this.maze.isOpenPath(x, y-1))//check left
				{this.myAnt.setRotate(90);//turn left
				direction = Direction.UP; moveForward();}
			else
				{this.myAnt.setRotate(0);
				direction = Direction.LEFT; moveForward();}
		}
		
		else if(this.myAnt.getRotate()==-90)//check if ant is facing down
		{
			if(this.maze.isOpenPath(x-1, y))//check right
				{this.myAnt.setRotate(0);//turn right
				direction = Direction.LEFT; moveForward();}
			else if(this.maze.isOpenPath(x, y+1))//check front
				moveForward();
			else if(this.maze.isOpenPath(x+1, y))//check left
				{this.myAnt.setRotate(180);//turn left
				direction = Direction.RIGHT; moveForward();}
			else
				{this.myAnt.setRotate(90); direction = Direction.UP; moveForward();}
		}
	}

	 /*
	 * This is an inner class inside the Controller class
	 * The purpose of the class is to handle the action event
	 */
	class HandleActionEvent implements EventHandler<ActionEvent>
	{
		int points = 0;
		int timer = 0;
		Timeline animation = new Timeline(new KeyFrame(Duration.seconds(.2), this));//for animating our ant
		public HandleActionEvent()
		{
			animation.setCycleCount(Timeline.INDEFINITE);
			animation.play();
			stopButton.setOnAction(e -> animation.stop());
		}
		public HandleActionEvent(String text)
		{
			System.out.println(text);
			animation.stop();
		}

		
		@Override
		public void handle(ActionEvent e)
		{
			System.out.println("(x = " + x + ", y = " + y + ")" );
			
			//Where the code for traversing the maze must go in
			followLeftWall();
			//followRightWall();
			this.points += 1;
			if(points % 5 == 0)//change timer every 200 ms x 5 = 1s
			{
				timer += 1;
				timerLabel.setText("Timer : "+timer);
			}
			pointsLabel.setText("Points : "+ points);
			
			
			if(destinationReached())
				animation.stop();
			
			createContent();
		}
	}//end of HandleActionEvent class
	
	/**
	 * This class is for handling the key event when the user plays manually
	 * 
	 */
	class HandleKeyEvent implements EventHandler<KeyEvent>
	{
		int points = 0;
		int timer = 0;
		public HandleKeyEvent()
		{
			setOnKeyPressed(this);
		}
		@SuppressWarnings("incomplete-switch")
		@Override
		public void handle(KeyEvent event) 
		{
			System.out.println("(x = " + x + ", y = " + y + ")" );
			timerLabel.setText("Timer : "+timer);
			switch(event.getCode()) 
			{
				case LEFT:
					direction = Direction.LEFT;myAnt.setRotate(0);//make ant face left
					if(maze.isWall(x-1, y))
					{					}
					else if(maze.isOpenPath(x-1, y))
					{
						drawTranslated(x--, y);this.points += 1;pointsLabel.setText("Points : "+ points);
					}
					else
						System.out.println("Reached destination");break;
				case RIGHT:
					direction = Direction.RIGHT;myAnt.setRotate(180);//make ant face right
					if(maze.isWall(x+1, y))
					{					}
					else
					{
						drawTranslated(x++, y);this.points += 1;pointsLabel.setText("Points : "+ points);
						break;
					}
				case UP:
					direction = Direction.UP;myAnt.setRotate(90);//make ant face up
					if(maze.isWall(x, y-1))
					{					}
					else
					{
						drawTranslated(x, y--);this.points += 1;pointsLabel.setText("Points : "+ points);
						break;
					}
				case DOWN:
					direction = Direction.DOWN;myAnt.setRotate(-90);//make ant face down
					if(maze.isWall(x, y+1))
					{		}
					else
					{
						drawTranslated(x, y++);this.points += 1;pointsLabel.setText("Points : "+ points);
						break;
					}
			}
			createContent();
		}
		
	}//end of HandleKeyEvent class
	
}//end of Controller class
