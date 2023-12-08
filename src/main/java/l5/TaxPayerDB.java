package l5;

import l4.TaxPayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static l5.DatabaseConnection.getConnection;
import static l5.TaxDB.selectTax;

public class TaxPayerDB {

    private static final Logger logger = LoggerFactory.getLogger(TaxPayerDB.class);

    public static void createTaxPayerTable() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS tax_payers (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(50) NOT NULL," +
                    "taxInspector_id BIGINT NOT NULL," +
                    "FOREIGN KEY (taxInspector_id) REFERENCES tax_inspectors(id) ON DELETE CASCADE" +
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

    public static List<TaxPayer> getAllTaxPayers() {
        List<TaxPayer> taxPayers = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String selectQuery = "SELECT * FROM tax_payers";

            try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    TaxPayer taxPayer = new TaxPayer.Builder(resultSet.getString("name"))
                            .id((int) resultSet.getLong("id"))
                            .taxes(selectTax(resultSet.getLong("id")))
                            .build();

                    taxPayers.add(taxPayer);
                }
            }
        } catch (SQLException e) {
            logger.error("An error occurred while fetching tax payers:", e);
        }
        return taxPayers;
    }

    public static void insertTaxPayer(TaxPayer taxPayer) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO tax_payers (taxInspector_id, name) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, taxPayer.getTaxInspectorId());
            preparedStatement.setString(2, taxPayer.getName());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long generatedId = generatedKeys.getLong(1);
                        taxPayer.setId((int) generatedId);
                        logger.info("TaxPayer inserted successfully with ID {}.", generatedId);
                    } else {
                        logger.warn("Failed to get generated ID for inserted tax payer.");
                    }
                }
            } else {
                logger.warn("Failed to insert tax payer.");
            }
        } catch (SQLException e) {
            logger.error("An error occurred while inserting tax payer:", e);
        }
    }


    public static TaxPayer selectTaxPayer(long id) {
        TaxPayer taxPayer = null;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String selectQuery = "SELECT * FROM tax_payers WHERE id=" + id;

            try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                if (resultSet.next()) {
                    taxPayer = new TaxPayer.Builder(resultSet.getString("name"))
                            .id((int) resultSet.getLong("id"))
                            .taxes(selectTax(resultSet.getLong("id")))
                            .build();
                }
            }
        } catch (SQLException e) {
            logger.error("An error occurred while fetching tax payer:", e);
        }
        return taxPayer;
    }

    public static void updateTaxPayer(TaxPayer taxPayer) {
        try (Connection connection = getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE tax_payers SET taxInspector_id=?, name=? WHERE id=?")) {
            preparedStatement.setLong(1, taxPayer.getTaxInspectorId());
                preparedStatement.setString(2, taxPayer.getName());
                preparedStatement.setLong(3, taxPayer.getId());

                int rowsUpdated = preparedStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    logger.info("TaxPayer with ID {} updated successfully.", taxPayer.getId());
                } else {
                    logger.warn("No tax payer record found for ID {}.", taxPayer.getId());
                }
            }
        } catch (SQLException e) {
            logger.error("An error occurred while updating tax payer with ID " + taxPayer.getId() + ":", e);
        }
    }

    public static void deleteTaxPayer(long taxPayerId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM tax_payers WHERE id=?")) {
            preparedStatement.setLong(1, taxPayerId);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                logger.info("TaxPayer with ID {} deleted successfully.", taxPayerId);
            } else {
                logger.warn("No tax payer record found for ID {}.", taxPayerId);
            }
        } catch (SQLException e) {
            logger.error("An error occurred while deleting tax payer with ID " + taxPayerId + ":", e);
        }
    }
}
