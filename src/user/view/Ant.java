package user.view;

//import javafx.event.EventHandler;
import javafx.scene.Group;
//import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Ant extends Pane 
{
	private int x = 0; 
	private int y = 0;
	private Group ant;

	/**
	 * Ant constructor
	 */
	public Ant()
	{
		drawFullAnt();
	}

	/**
	 * @return head circle
	 */
	private Circle drawHead()
	{
		Circle head = new Circle(415,100,5);
		head.setFill(Color.BURLYWOOD);
		head.setStroke(Color.BROWN);
		return head;
	}
	
	/**
	 * @return body circle
	 */
	private Circle drawBody()
	{
		Circle body = new Circle(425,100,8);
		body.setFill(Color.BROWN);
		return body;

	}
	
	/**
	 * @return rightAntenna
	 */
	private Line drawRightAntenna()
	{
		Line rightAntenna = new Line(410,95,405,90);
		rightAntenna.setStroke(Color.RED);rightAntenna.setStrokeWidth(2);
		return rightAntenna;
	}
	
	/**
	 * @return leftAntenna
	 */
	private Line drawLeftAntenna()
	{
		Line leftAntenna = new Line (410,105,405,110);
		leftAntenna.setStroke(Color.RED);leftAntenna.setStrokeWidth(2);
		return leftAntenna;
	}
	
	/**
	 * draw full ant and grouping the parts
	 */
	private void drawFullAnt() 
	{
		this.ant = new Group();
		this.ant.getChildren().addAll(drawHead(), drawBody(),drawRightAntenna(),drawLeftAntenna());	
	}

	/**
	 * @return the ant
	 */
	public Group getAnt() 
	{
		return ant;
	}
	
	
	/**
	 * This method repositions the ant to he desired location/position
	 * @param x
	 * @param y
	 */
	public void drawTranslated(int x, int y)
	{
		this.ant.relocate(x*25, y*25);
	}
	
	/**
	 * This method checks if the ant is facing up/north
	 * @param ant
	 * @return
	 */
	public boolean isFaceNorth(Group ant)
	{
		if(ant.getRotate()==90)
		{
			return true;
		}
		return false;
	}

	/**
	 * This method checks if the ant is facing down/south
	 * @param ant
	 * @return
	 */
	public boolean isFaceSouth(Group ant)
	{
		if(ant.getRotate()==-90)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * This method checks if the ant is facing left
	 * @param ant
	 * @return
	 */
	public boolean isFaceLeft(Group ant)
	{
		if(ant.getRotate()==0)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * This method checks if the ant is facing right
	 * @param ant
	 * @return
	 */
	public boolean isFaceRight(Group ant)
	{
		if(ant.getRotate()==180)
		{
			return true;
		}
		return false;
	}
	
	
}