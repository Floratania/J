package l5;

import l4.Tax;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Create Tax table
        TaxDB.createTaxTable();

        Tax tax1 = new Tax.TaxBuilder("Income Tax")
                .id(1)
                .amount(1000.0)
                .date(LocalDate.of(2023, 1, 15))
                .paid(true)
                .dateOfPaid(LocalDate.of(2023, 1, 20))
                .build();

        Tax tax2 = new Tax.TaxBuilder("Property Tax")
                .id(2)
                .amount(500.0)
                .date(LocalDate.of(2023, 2, 28))
                .paid(false)
                .dateOfPaid(null)
                .build();

        // Insert sample tax records

        TaxDB.insertTax(tax1);
        TaxDB.insertTax(tax2);

        // Retrieve and print all taxes
        List<Tax> allTaxes = TaxDB.getAllTax();
        System.out.println("All Taxes:");
        for (Tax tax : allTaxes) {
            System.out.println(tax);
        }

//        // Update a tax record
        tax1.setAmount(1200.0);
        TaxDB.updateTax(tax1);

         //Retrieve and print all taxes after update
        allTaxes = TaxDB.getAllTax();
        System.out.println("\nAll Taxes after Update:");
        for (Tax tax : allTaxes) {
            System.out.println(tax);
        }

        // Create TaxPayer table
        TaxPayerDB.createTaxPayerTable();

        // Create TaxInspector table
        TaxInspectorDB.createTaxInspectorTable();
        TaxInspectorDB.dropTaxInspectorTable();
        TaxPayerDB.dropTaxPayerTable();
        // Drop Tax table
        TaxDB.dropTaxTable();
//
//        // Drop TaxPayer table

//
//        // Drop TaxInspector table

    }
}
