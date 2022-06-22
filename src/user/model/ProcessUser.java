package user.model;

import static utils.Utilities.USERPATH;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import user.model.User;
import utils.Utilities;

public class ProcessUser 
{

	 private Set<User> listUsers;

    /**
     *  Constructs an instance of ProcessUser and proceed to collect users' data from
     *  specified directory and stores them into a set.
     */
    public ProcessUser() 
    {
        this.listUsers = uploadUsers();
    }

    /**
     *  Method read data (users' credentials) from file and transform them into
     *  user instances (objects) before storing them into a set.
     * @return
     */
    private Set<User> uploadUsers() 
    {
        return Utilities.getFileLines(getPath()).stream()
                .map(Utilities.tokenize(";"))
                .map(createUser())
                .collect(Collectors.toSet());
    }

    /**
     * Gets the directory and path of user's file
     * @return
     */
    private Path getPath() 
    {
        return Paths.get(Paths.get("").toAbsolutePath().toString() + USERPATH);
    }

    /**
     * Creates an instance User and populate its attributes with data collected from
     * file's line
     * @return
     */
    private Function<String[], User> createUser() 
    {
        return t -> new User(t[0],t[1],t[2]);
    }


    /**
     * Gets the set of all users collected from the file.
     * @return
     */
    public Set<User> getListName()
    {
        return this.listUsers;
    }
}
