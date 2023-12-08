package l5;

import l4.TaxInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static l5.DatabaseConnection.getConnection;

public class TaxInspectorDB {

    private static final Logger logger = LoggerFactory.getLogger(TaxInspectorDB.class);

    public static void createTaxInspectorTable() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS tax_inspectors (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(50) NOT NULL" +
                    ")";
            statement.execute(createTableQuery);
        } catch (SQLException e) {
            logger.error("An error occurred:", e);
        }
    }

    public static void dropTaxInspectorTable() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String dropTableQuery = "DROP TABLE IF EXISTS tax_inspectors";
            statement.execute(dropTableQuery);
        } catch (SQLException e) {
            logger.error("An error occurred while dropping the table:", e);
        }
    }

    public static void insertTaxInspector(String name) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO tax_inspectors (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long generatedId = generatedKeys.getLong(1);
                        logger.info("Tax Inspector inserted successfully with ID {}.", generatedId);
                    } else {
                        logger.warn("Failed to get generated ID for inserted tax inspector.");
                    }
                }
            } else {
                logger.warn("Failed to insert tax inspector.");
            }
        } catch (SQLException e) {
            logger.error("An error occurred while inserting tax inspector:", e);
        }
    }

    public static void updateTaxInspector(long id, String newName) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE tax_inspectors SET name=? WHERE id=?")) {
            preparedStatement.setString(1, newName);
            preparedStatement.setLong(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                logger.info("Tax Inspector with ID {} updated successfully.", id);
            } else {
                logger.warn("No tax inspector record found for ID {}.", id);
            }
        } catch (SQLException e) {
            logger.error("An error occurred while updating tax inspector with ID " + id + ":", e);
        }
    }

    public static void deleteTaxInspector(long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM tax_inspectors WHERE id=?")) {
            preparedStatement.setLong(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                logger.info("Tax Inspector with ID {} deleted successfully.", id);
            } else {
                logger.warn("No tax inspector record found for ID {}.", id);
            }
        } catch (SQLException e) {
            logger.error("An error occurred while deleting tax inspector with ID " + id + ":", e);
        }
    }

    public static TaxInspector selectTaxInspector(long id) {
        TaxInspector taxInspector = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM tax_inspectors WHERE id=?")) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    taxInspector = new TaxInspector(resultSet.getString("name"));
                    taxInspector.setId(resultSet.getLong("id"));
                }
            }
        } catch (SQLException e) {
            logger.error("An error occurred while fetching tax inspector with ID {}:", id, e);
        }
        return taxInspector;
    }
}
