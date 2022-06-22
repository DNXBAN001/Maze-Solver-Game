package user.view;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Maze extends Pane
{

//	private static final int rows = 46, columns = 20;
	private int rows = 21, columns = 10;
	private int x, y;
	private MazeModel mazeModelObj;
	
	private char [][] maze = {{'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},
							  {'X', '#', '#', '#', '#', '#', '#', '#', '#', 'X'},
							  {'X', '#', '#', '#', '#', '#', '#', 'X', '#', 'X'},
							  {'X', '#', '#', 'X', 'X', 'X', '#', 'X', '#', 'X'},
							  {'X', '#', '#', 'X', '#', '#', '#', 'X', '#', 'X'},
							  {'X', '#', '#', 'X', '#', '#', '#', 'X', '#', 'X'},
							  {'X', '#', '#', 'X', 'X', 'X', 'X', 'X', '#', 'X'},
							  {'X', '#', '#', 'X', '#', '#', '#', 'X', '#', 'X'},
							  {'X', '#', '#', 'X', '#', 'X', '#', 'X', '#', 'X'},
							  {'X', '#', '#', 'X', '#', 'X', '#', 'X', '#', 'X'},
							  {'X', '#', 'S', 'X', '#', 'X', '#', 'X', '#', 'X'},
							  {'X', 'X', 'X', 'X', '#', 'X', '#', 'X', '#', 'X'},
							  {'E', '#', 'X', '#', '#', 'X', '#', 'X', '#', 'X'},
							  {'X', '#', 'X', '#', '#', 'X', '#', 'X', '#', 'X'},
							  {'X', '#', 'X', '#', '#', 'X', '#', 'X', '#', 'X'},
							  {'X', '#', 'X', '#', '#', 'X', '#', 'X', '#', 'X'},
							  {'X', '#', 'X', '#', '#', 'X', '#', 'X', '#', 'X'},
							  {'X', '#', '#', '#', '#', 'X', '#', 'X', '#', 'X'},
							  {'X', 'X', 'X', '#', '#', 'X', '#', 'X', '#', 'X'},
							  {'X', '#', '#', '#', '#', 'X', '#', '#', '#', 'X'},
							  {'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'},};
							  
					
	/**
	 * 
	 */
	public Maze(String fileContent)
	{
		if(fileContent != null)
		{
			this.mazeModelObj = new MazeModel(fileContent);
			this.rows = mazeModelObj.getRows();
			this.columns = mazeModelObj.getColumns();
			this.maze = mazeModelObj.getMaze();
			
		}
		drawMaze();
		
	}
	

	/**
	 *   This method draws the entire maze
	 */
	private void drawMaze()
	{
		for(int i = 0; i<maze.length;i++)
		{
			for(int j = 0; j<columns; j++)
			{
				drawCell(i, j);
			}
		}
	}
	/**
	 * @param i
	 * @param j
	 */
	private void drawCell(int i, int j)
	{ 
		if(this.maze[i][j] == '+'||this.maze[i][j] == 'X')//if element of the maze is X/+ color-fill square
		{
			Rectangle rectangle = new Rectangle(i* 25,j * 25,25,25);
			//rectangle.setFill(Color.GREEN);
			this.getChildren().add(rectangle);

		}
		else if(this.maze[i][j]==' '||this.maze[i][j]=='#')//if element of the maze is #/free space draw a white square
		{
			Rectangle rect = new Rectangle(i* 25,j * 25,25,25);
			rect.setFill(null);
			rect.setStroke(Color.BLACK);
			this.getChildren().add(rect);
		}
		else if(this.maze[i][j]=='E')//square for the end location
		{
			Rectangle rect = new Rectangle(i* 25,j * 25,25,25);
			rect.setFill(null);
			rect.setStroke(Color.BLACK);
			this.getChildren().add(rect);
		}
		else
		{
			Rectangle rect = new Rectangle(i* 25,j * 25,25,25);
			rect.setFill(null);
			rect.setStroke(Color.BLACK);
			this.getChildren().add(rect);
		}
	}
	/**
	 * This method checks if a cell is valid or not
	 * If it is valid return true
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isOpenPath(int x, int y)
	{
		return (this.maze[x][y]==' '||this.maze[x][y]=='#'||this.maze[x][y]=='E'||this.maze[x][y]=='S') ? true : false;
		
	}
	
	/**
	 * The method checks if a next cell is a wall or not
	 * @param x
	 * @param y
	 * @return true if there is wall in the position searched
	 */
	public boolean isWall(int x, int y)
	{
		return this.maze[x][y]=='+'||this.maze[x][y]=='X' ? true : false;
	}
	/**
	 * The method checks if a cell is the end location or not
	 * @return true if the c
	 */
	public boolean isEndLocation(int x, int y)
	{
		return this.maze[x][y]=='E' ? true : false;
	}
	
}
