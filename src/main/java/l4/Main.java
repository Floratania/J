package l4;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Create some Tax objects
        Tax tax1 = new Tax.TaxBuilder("Income Tax")
                .amount(1000.0)
                .date(LocalDate.of(2023, 1, 15))
                .paid(true)
                .dateOfPaid(LocalDate.of(2023, 1, 20))
                .build();

        Tax tax2 = new Tax.TaxBuilder("Property Tax")
                .amount(500.0)
                .date(LocalDate.of(2023, 2, 28))
                .paid(false)
                .build();

        // Create a TaxPayer with the taxes
        TaxPayer taxpayer = new TaxPayer.Builder("John Doe")
                .id(1)
                .addTax(tax1)
                .addTax(tax2)
                .build();

        // Create a TaxInspector and add the TaxPayer
        TaxInspector taxInspector = new TaxInspector.Builder("Inspector Smith")
                .id(101)
                .addTaxPayer(taxpayer)
                .build();

        // Print information
        System.out.println("Tax Inspector: " + taxInspector);
        System.out.println("Tax Payer: " + taxpayer);
        System.out.println("Tax : " + tax2);


        // Attempt to create an invalid Tax object to test validation
        try {
            Tax invalidTax = new Tax.TaxBuilder("Invalid Tax")
                    .amount(-100.0)  // This amount is invalid
                    .date(LocalDate.of(2023, 3, 10))
                    .paid(true)
                    .dateOfPaid(LocalDate.of(2023, 12, 15))
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating invalid Tax: " + e.getMessage());
        }
    }
}
