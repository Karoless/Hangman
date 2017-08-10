package pl.hangman.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import pl.hangman.models.Game;
import java.io.IOException;
import java.sql.SQLException;

public class DbManager {

    private static final String JDBC_URL_ADDRESS = "jdbc:h2:./hangmanDB";
    private static final String USER = "admin";
    private static final String PASS = "admin";
    private static final Logger LOGGER = LoggerFactory.getLogger(DbManager.class);
    private static ConnectionSource connectionSource;

    public static void initDatabase() {
        createConnectionSource();
        createTable();
        closeConnectionSource();
    }

    private static void createConnectionSource() {
        try {
            connectionSource = new JdbcConnectionSource(JDBC_URL_ADDRESS,USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionSource getConnectionSource(){
        if(connectionSource == null){
            createConnectionSource();
        }
        return connectionSource;
    }

    private static void createTable(){
        try {
            TableUtils.createTableIfNotExists(connectionSource, Game.class);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    private  static  void  dropTable(){
        try {
            TableUtils.dropTable(connectionSource, Game.class, true);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    public static void closeConnectionSource(){
        if(connectionSource!=null){
            try {
                connectionSource.close();
            } catch (IOException e) {
                LOGGER.warn(e.getMessage());
            }
        }
    }
}