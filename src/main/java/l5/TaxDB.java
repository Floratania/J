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
                    "dateOfPaid DATE" +  // Removed unnecessary comma
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

    public static List<Tax> getAllTax() {
        List<Tax> taxes = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String selectQuery = "SELECT * FROM taxes";

            try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    LocalDate dateOfPaid = null;
                    java.sql.Date sqlDateOfPaid = resultSet.getDate("dateOfPaid");
                    if (sqlDateOfPaid != null) {
                        dateOfPaid = sqlDateOfPaid.toLocalDate();
                    } else {
                        System.out.println("SQL Date of Paid is null for ID: " + resultSet.getLong("id"));
                    }

                    Tax tax = new Tax.TaxBuilder(resultSet.getString("name"))
                            .id(resultSet.getLong("id"))
                            .amount(resultSet.getDouble("amount"))
                            .date( resultSet.getDate("date").toLocalDate())
                            .paid(resultSet.getBoolean("paid"))
                            .dateOfPaid(dateOfPaid).build();

                    // Check the retrieved values
                    System.out.println("ID: " + tax.getId() + ", Name: " + tax.getName() +
                            ", Amount: " + tax.getAmount() + ", Date: " + tax.getDate() +
                            ", Paid: " + tax.isPaid() + ", Date of Paid: " + dateOfPaid);

                    taxes.add(tax);
                }
            }
        } catch (SQLException e) {
            logger.error("An error occurred while fetching taxes:", e);
        }
        return taxes;
    }

    public static void updateTax(Tax tax) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE taxes SET name=?, amount=?, date=?, paid=?, dateOfPaid=? WHERE id=?")) {
            preparedStatement.setString(1, tax.getName());
            preparedStatement.setDouble(2, tax.getAmount());
            preparedStatement.setDate(3, java.sql.Date.valueOf(tax.getDate()));
            preparedStatement.setBoolean(4, tax.isPaid());

            if (tax.getDateOfPaid() != null) {
                preparedStatement.setDate(5, java.sql.Date.valueOf(tax.getDateOfPaid()));
            } else {
                preparedStatement.setNull(5, java.sql.Types.DATE);
            }

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
                     "INSERT INTO taxes (name, amount, date, paid, dateOfPaid) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, tax.getName());
            preparedStatement.setDouble(2, tax.getAmount());
            preparedStatement.setDate(3, java.sql.Date.valueOf(tax.getDate()));
            preparedStatement.setBoolean(4, tax.isPaid());

            if (tax.getDateOfPaid() != null) {
                preparedStatement.setDate(5, java.sql.Date.valueOf(tax.getDateOfPaid()));
            } else {
                preparedStatement.setNull(5, java.sql.Types.DATE);
            }

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

}



