package pl.hangman.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import pl.hangman.utils.AppException;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import java.io.IOException;
import java.sql.SQLException;

import java.util.List;

public class DaoManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DaoManager.class);
    protected final ConnectionSource connectionSource;

    public DaoManager() {
        this.connectionSource = DbManager.getConnectionSource();
    }

    public <T extends BaseModel, I> void createOrUpdate(BaseModel baseModel) throws AppException {
        Dao<T, I> dao = getDao((Class<T>) baseModel.getClass());
        try {
            dao.createOrUpdate((T) baseModel);
        } catch (SQLException e) {
            LOGGER.warn(e.getCause().getMessage());
            throw new AppException("Błąd przy aktualizacji bazy danych");
        } finally {
            this.closeDbConnection();
        }
    }

    public <T extends BaseModel, I> List<T> queryForAll(Class<T> cls) throws AppException {
        try {
            Dao<T, I> dao = getDao(cls);
            return dao.queryForAll();
        } catch (SQLException e) {
            LOGGER.warn(e.getCause().getMessage());
            throw new AppException("Brak wpisu w bazie");
        } finally {
            this.closeDbConnection();

        }
    }
    private void closeDbConnection() throws AppException {
        try {
            this.connectionSource.close();
        } catch (IOException e) {
            throw new AppException("Problem z połączeniem");
        }
    }
    public <T extends BaseModel, I> Dao<T, I> getDao(Class<T> cls) throws AppException {
        try {
            return com.j256.ormlite.dao.DaoManager.createDao(connectionSource, cls);
        } catch (SQLException e) {
            LOGGER.warn(e.getCause().getMessage());
            throw new AppException("Problem z połączeniem");
        } finally {
            this.closeDbConnection();
        }
    }
}