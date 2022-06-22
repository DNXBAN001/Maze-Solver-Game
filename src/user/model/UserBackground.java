package user.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javafx.concurrent.Task;
import utils.Utilities;
import database.DatabaseHandle;

public class UserBackground //extends Task<Set<Baby>> 
{
    private DatabaseHandle databaseHandle;
    private  long count = 0;

//    /**
//     * Creates a task and initializes its database instance.
//     * @param database
//     */
//    public UserBackground(DatabaseHandle database) {
//       this.databaseHandle = database;
//    }
//
//    /**
//     * Core of task: this method executes the task
//     * @return
//     */
//    @Override
//    protected Set<Baby> call() {
//        Set<Baby> list;
//        ResultSet resultSet = databaseHandle.Select("select * from Baby");
//        list = collectBabiesFromDB(resultSet);
//        updateMessage("Task completed");
//        return list;
//    }
//
//    /**
//     * Fetches all babies from database (Table Baby) and updates progressbar on GUI
//     * @param resultSet
//     * @return
//     */
//    private Set<Baby> collectBabiesFromDB(ResultSet resultSet) {
//        Set<Baby> list = new HashSet<>();
//
//        try{
//            int size = databaseHandle.getNumberOfRows("Baby");
//            while(resultSet.next()){
//                list.add(new Baby(resultSet.getString(1), resultSet.getString(2),resultSet.getString(3),
//                        resultSet.getInt(4),resultSet.getShort(5)));
//                count++;
//                updateProgress(count,size);
//            }
//
//        }catch (SQLException ex){
//            IUtilities.showErrorMessage("Select Users Failure",ex.getMessage());
//        }
//        return  list;
//    }
}
