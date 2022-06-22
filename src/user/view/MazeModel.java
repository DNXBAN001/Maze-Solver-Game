package user.view;

import utils.Utilities;

public class MazeModel implements Utilities
{

	private int rows, columns;
	private String fileContent;
	private String [] tempVar;
	
	
	private char [][] maze;
	
	public MazeModel(String fileContent)
	{
		this.fileContent = fileContent;
		tempVar = this.fileContent.split("[;]");//separate by a quote mark
		
		this.rows = tempVar.length;
		this.columns = tempVar[2].toCharArray().length;
		
		maze = new char[rows][columns];
		createMazeArray();
	}
	private void createMazeArray()
	{
		for(int i = 0; i < rows; i++)
		{
			char [] ch = tempVar[i].toCharArray();
			for(int j = 0; j < columns; j++)
			{
				maze[i][j] = ch[j];
			}
		}
		//print the newly created maze on the console
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				System.out.print(maze[i][j]);
			}
			System.out.println();
		}
	}
	public char[][] getMaze()
	{
		return this.maze;
	}
	public int getRows() 
	{
		return this.rows;
	}
	public int getColumns() 
	{
		return this.columns;
	}
}
