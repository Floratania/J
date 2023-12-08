package l5;

import l4.Tax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static l5.DatabaseConnection.getConnection;

public class TaxDB {

    private static final Logger logger = LoggerFactory.getLogger(TaxDB.class);

    public static void createTaxTable() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS taxes (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(50) NOT NULL," +
                    "amount DOUBLE PRECISION NOT NULL," +
                    "date DATE NOT NULL," +
                    "paid BOOLEAN NOT NULL," +
                    "dateOfPaid DATE," +
                    "taxPayer_id BIGINT NOT NULL," +
                    "FOREIGN KEY (taxPayer_id) REFERENCES tax_payers(id) ON DELETE CASCADE" +
                    ")";
            statement.execute(createTableQuery);
        } catch (SQLException e) {
            logger.error("An error occurred:", e);
        }
    }

    public static void dropTaxTable() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String dropTableQuery = "DROP TABLE IF EXISTS taxes";
            statement.execute(dropTableQuery);
        } catch (SQLException e) {
            logger.error("An error occurred while dropping the table:", e);
        }
    }

    public static List<Tax> getAllTaxes() {
        List<Tax> taxes = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String selectQuery = "SELECT * FROM taxes";

            try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    Tax tax = buildTaxFromResultSet(resultSet);
                    taxes.add(tax);
                }
            }
        } catch (SQLException e) {
            logger.error("An error occurred while fetching taxes:", e);
        }
        return taxes;
    }

    public static List<Tax> selectTax(long id) {
        Tax tax = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM taxes WHERE id = ?")) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    tax = buildTaxFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error("An error occurred while fetching tax with ID {}:", id, e);
        }
        return (List<Tax>) tax;
    }

    public static Tax selectTaxes(long id) {
        Tax tax = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM taxes WHERE id = ?")) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    tax = buildTaxFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error("An error occurred while fetching tax with ID {}:", id, e);
        }
        return tax;
    }
    public static List<Tax> selectTaxesByTaxPayerId(long id) {
        List<Tax> taxes = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM taxes WHERE taxPayer_id = ?")) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Tax tax = buildTaxFromResultSet(resultSet);
                    taxes.add(tax);
                }
            }
        } catch (SQLException e) {
            logger.error("An error occurred while fetching taxes for taxPayer ID {}:", id, e);
        }
        return taxes;
    }

    public static void updateTax(Tax tax) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE taxes SET name=?, amount=?, date=?, paid=?, dateOfPaid=? WHERE id=?")) {
            setTaxParameters(preparedStatement, tax);
            preparedStatement.setLong(6, tax.getId());

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                logger.info("Tax with ID {} updated successfully.", tax.getId());
            } else {
                logger.warn("No tax record found for ID {}.", tax.getId());
            }
        } catch (SQLException e) {
            logger.error("An error occurred while updating tax with ID " + tax.getId() + ":", e);
        }
    }
    public static void insertTax(Tax tax) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO taxes (name, amount, date, paid, dateOfPaid, taxPayer_id) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, tax.getName());
            preparedStatement.setDouble(2, tax.getAmount());
            preparedStatement.setDate(3, java.sql.Date.valueOf(tax.getDate()));
            preparedStatement.setBoolean(4, tax.isPaid());

            if (tax.getDateOfPaid() != null) {
                preparedStatement.setDate(5, java.sql.Date.valueOf(tax.getDateOfPaid()));
            } else {
                preparedStatement.setNull(5, java.sql.Types.DATE);
            }

            // Ensure a valid taxPayer_id
            preparedStatement.setLong(6, tax.getTaxPayerId());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                logger.info("Tax inserted successfully with ID {}.", tax.getId());
            } else {
                logger.warn("Failed to insert tax.");
            }
        } catch (SQLException e) {
            logger.error("An error occurred while inserting tax:", e);
        }
    }


    public static void deleteTax(long taxId) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM taxes WHERE id=?")) {
            preparedStatement.setLong(1, taxId);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                logger.info("Tax with ID {} deleted successfully.", taxId);
            } else {
                logger.warn("No tax record found for ID {}.", taxId);
            }
        } catch (SQLException e) {
            logger.error("An error occurred while deleting tax with ID " + taxId + ":", e);
        }
    }

    private static Tax buildTaxFromResultSet(ResultSet resultSet) throws SQLException {
        LocalDate dateOfPaid = null;
        java.sql.Date sqlDateOfPaid = resultSet.getDate("dateOfPaid");
        if (sqlDateOfPaid != null) {
            dateOfPaid = sqlDateOfPaid.toLocalDate();
        }

        Tax tax = new Tax.TaxBuilder(resultSet.getString("name"))
                .id(resultSet.getLong("id"))
                .amount(resultSet.getDouble("amount"))
                .date(resultSet.getDate("date").toLocalDate())
                .paid(resultSet.getBoolean("paid"))
                .dateOfPaid(dateOfPaid)
                .build();

// Set the taxPayerId property using the result set
        tax.setTaxPayerId(resultSet.getLong("taxPayer_id"));
        return tax;

    }

    private static void setTaxParameters(PreparedStatement preparedStatement, Tax tax) throws SQLException {
        preparedStatement.setString(1, tax.getName());
        preparedStatement.setDouble(2, tax.getAmount());
        preparedStatement.setDate(3, java.sql.Date.valueOf(tax.getDate()));
        preparedStatement.setBoolean(4, tax.isPaid());

        if (tax.getDateOfPaid() != null) {
            preparedStatement.setDate(5, java.sql.Date.valueOf(tax.getDateOfPaid()));
        } else {
            preparedStatement.setNull(5, java.sql.Types.DATE);
        }
    }
}
