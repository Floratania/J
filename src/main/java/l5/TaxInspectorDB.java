package l5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
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
                    "name VARCHAR(50) NOT NULL," +
                    "taxPayer_id BIGINT NOT NULL," +
                    "FOREIGN KEY (taxPayer_id) REFERENCES tax_payers(id) ON DELETE CASCADE" +  // Removed unnecessary comma
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


}

