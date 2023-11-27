package l2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of the EntitySerializer interface for serializing and deserializing objects to/from plain text (TXT) format.
 *
 * @param <T> The type of entity that this serializer works with.
 */
public class TxtEntitySerializer<T> implements EntitySerializer<T> {
    private static final Logger logger = LoggerFactory.getLogger(TxtEntitySerializer.class);

    /**
     * Serializes an entity to a plain text (TXT) format.
     *
     * @param entity The entity to be serialized.
     * @return The plain text representation of the entity.
     */
    @Override
    public String serialize(T entity) {
        if (entity instanceof TaxInspector) {
            TaxInspector inspector = (TaxInspector) entity;
            StringBuilder sb = new StringBuilder();
            sb.append(inspector.getName()).append("\n");
            sb.append(inspector.getId()).append("\n");

            // Serialize associated TaxPayers
            for (TaxPayer taxPayer : inspector.getTaxPayers()) {
                sb.append("TaxPayer\n");
                sb.append(taxPayer.getName()).append("\n");
                sb.append(taxPayer.getId()).append("\n");

                // Serialize associated Taxes for the TaxPayer
                for (Tax tax : taxPayer.getTaxes()) {
                    sb.append("Tax\n");
                    sb.append(tax.getName()).append("\n");
                    sb.append(tax.getAmount()).append("\n");
                    sb.append(tax.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE)).append("\n");
                    sb.append(tax.isPaid()).append("\n");
                }
            }

            return sb.toString();
        } else {
            return null;
        }
    }

    /**
     * Deserializes an entity from a plain text (TXT) representation.
     *
     * @param data The plain text representation of the entity.
     * @return The reconstructed entity object.
     */
    @Override
    public T deserialize(String data) {
        try (Scanner scanner = new Scanner(new ByteArrayInputStream(data.getBytes()))) {
            String inspectorName = scanner.nextLine();
            int inspectorId = Integer.parseInt(scanner.nextLine());

            TaxInspector inspector = new TaxInspector.Builder(inspectorName, inspectorId).build();
            TaxPayer currentTaxPayer = null;
            Tax currentTax = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals("TaxPayer")) {
                    String taxpayerName = scanner.nextLine();
                    int taxpayerId = Integer.parseInt(scanner.nextLine());
                    currentTaxPayer = new TaxPayer.Builder(taxpayerName, taxpayerId).build();
                    inspector.addTaxPayer(currentTaxPayer);

                } else if (line.equals("Tax")) {
                    String taxName = scanner.nextLine();
                    double taxAmount = Double.parseDouble(scanner.nextLine());
                    LocalDate taxDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);
                    boolean taxPaid = Boolean.parseBoolean(scanner.nextLine());
                    currentTax = new Tax.Builder(taxName)
                            .amount(taxAmount)
                            .date(taxDate)
                            .paid(taxPaid)
                            .build();
                    currentTaxPayer.addTax(currentTax);
                }
            }

            return (T) inspector;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Writes the serialized entity to a plain text (TXT) file.
     *
     * @param entity   The entity to be written to the file.
     * @param fileName The name of the TXT file to write the entity to.
     */
    @Override
    public void writeToFile(T entity, String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println(serialize(entity));
        } catch (IOException e) {
            logger.error("An error occurred:", e);
        }
    }

    /**
     * Reads an entity from a plain text (TXT) file and returns it as an object.
     *
     * @param fileName The name of the TXT file to read the entity from.
     * @return The reconstructed entity object from the file.
     */
    @Override
    public T readFromFile(String fileName) {
        try {
            String data = Files.readString(Paths.get(fileName));
            return deserialize(data);
        } catch (IOException e) {
            logger.error("An error occurred:", e);
            return null;
        }
    }
}
