package database;

import static utils.Utilities.showErrorMessage;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DatabaseHandle;
import user.model.User;

public class DatabaseHandle 
{

	private Connection connect = null;
	private PreparedStatement statement = null;
	private static DatabaseHandle handler = null;
	private final String DRIVER = "org.sqlite.JDBC";
	private final String URL = "jdbc:sqlite:MazeGameDB.sqlite";
	
	
	
	/**
	 * Constructs the database, initiate connection and create all necessary table
	 */
	private DatabaseHandle()
	{
		createConnection();
		createUserTable();
		defaultUser();
	}
	/**
	 * Creates
	 */
	private DatabaseHandle(User user)
	{
		createConnection();
		createUserTable();
		Insert(user);
	}
	
	/**
	 *  Set the database instance and set it as static meaning this instance will be the ONLY shared
	 *  accross the board.
	 * @return
	 */
	public static DatabaseHandle getInstance()
	{
        if (handler == null)
            handler = new DatabaseHandle();
      
        return handler;
    }
	/**
	 * Sets the database instance but with a different constructor that takes a new user to be added on the database
	 * @param user
	 * @return
	 */
	public static DatabaseHandle getInstance(User user)
	{
		if (handler == null)
            handler = new DatabaseHandle(user);
      
        return handler;
	}
	
	/**
	 *  Initializes database with the ONLY user: Admin
	 */
	private void defaultUser()
	{
		this.Insert(new User("Admin","admin","admin"));
	}

	/**
	 * Makes connection with the SQLite Server using the DRIVER and URL of the JDBC
	 * (Java Database Connectivity)
	 */
	private void createConnection() 
	{
		try 
		{
            Class.forName(DRIVER);
            connect = DriverManager.getConnection(URL);
        } 
		catch (Exception e)
		{
			showErrorMessage("Database connection Failure",e.getMessage());
        }
	}
	
	/**
	 *  Creates table into database given the schema (name and attributes)
	 * @param table
	 * @param query
	 */
	private void createTable(String table,String query)
	{
		try
		{
			statement = connect.prepareStatement("CREATE TABLE IF NOT EXISTS  " + table + query);

			DatabaseMetaData dbm = connect.getMetaData();
			ResultSet tables = dbm.getTables(null, null, table.toUpperCase(), null);

			if (!tables.next())
				statement.execute();
		}
		catch(SQLException e)
		{
			showErrorMessage("Table Creation Failure",e.getMessage());
		}
	}
	
	/**
	 * Creates User Table by formatting its attributes/fields
	 */
	private void createUserTable() 
	{
		//Make our username a primary key
		createTable("Users",  "("
					+ "	userName varchar(20) PRIMARY KEY not null,\n"
					+ "	userPassword varchar(10) not null,\n"
					+ "	userStatus varchar(6) not null\n"
					+ " )");
	}
	
	/**
	 * Get the number of rows in a given table located in the database
	 * @return
	 */
	public int getNumberOfRows(String table)
	{
		int rows = 0;
		try 
		{
			ResultSet resultSet = Select("select * from " + table);
			while(resultSet.next())
				rows++;
		}
		catch(SQLException e)
		{
			showErrorMessage("Table tuples Failure",e.getMessage());
		}
		return rows;
	}
	
	/**
	 * Method pulls tuples from requested table matching the criteria
	 * @param query
	 * @return
	 */
	public ResultSet Select(String query) 
	{
        ResultSet result;
        try 
        {
            statement = connect.prepareStatement(query);
            result = statement.executeQuery();
        } 
        catch(SQLException ex) 
        {
			showErrorMessage("Select Query Failure",ex.getMessage());
            return null;
        }
        return result;
    }
	
	/**
	 * Method inserts a new User record into database
	 * @param query
	 * @param user
	 * @return
	 */
	public void Insert(User user) 
	{
		try 
		{
			statement = connect.prepareStatement("insert or ignore into Users values (?,?,?)");
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getUserPassword());
			statement.setString(3, user.getUserStatus());
			statement.executeUpdate();
		} 
		catch(SQLException ex) 
		{
			showErrorMessage("User Insertion Failure",ex.getMessage());
		}
	}
	
	/**
	 *
	 */
   public void closeSession()
   {
	   	try 
	   	{
			if(statement != null)statement.close();
			if(connect != null)connect.close();
		} 
	   	catch (SQLException ex) 
	   	{
			showErrorMessage("Session Close Failure",ex.getMessage());
		}
   }
}
