    package l5;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    import java.sql.Connection;
    import java.sql.SQLException;
    import java.sql.Statement;

    import static l5.DatabaseConnection.getConnection;

    public class TaxPayerDB {

        private static final Logger logger = LoggerFactory.getLogger(TaxPayerDB.class);
        public static void createTaxPayerTable() {
            try (Connection connection = getConnection();
                 Statement statement = connection.createStatement()) {
                String createTableQuery = "CREATE TABLE IF NOT EXISTS tax_payers (" +
                        "id SERIAL PRIMARY KEY," +
                        "tax_id BIGINT NOT NULL," +
                        "name VARCHAR(50) NOT NULL," +
                        "FOREIGN KEY (tax_id) REFERENCES taxes(id) ON DELETE CASCADE" +  // Removed unnecessary comma
                        ")";
                statement.execute(createTableQuery);
            } catch (SQLException e) {
                logger.error("An error occurred:", e);
            }
        }

        public static void dropTaxPayerTable() {
            try (Connection connection = getConnection();
                 Statement statement = connection.createStatement()) {
                String dropTableQuery = "DROP TABLE IF EXISTS tax_payers";
                statement.execute(dropTableQuery);
            } catch (SQLException e) {
                logger.error("An error occurred while dropping the table:", e);
            }
        }
    }
