package l5;

import l4.Tax;
import l4.TaxPayer;

import java.time.LocalDate;
import java.util.List;

import static l5.TaxDB.selectTax;

public class Main {

    public static void main(String[] args) {
        // Create tables
        TaxInspectorDB.createTaxInspectorTable();
        TaxPayerDB.createTaxPayerTable();
        TaxDB.createTaxTable();

        // Insert some tax inspectors
        TaxInspectorDB.insertTaxInspector( "Inspector 1");
        TaxInspectorDB.insertTaxInspector("Inspector 2");

        TaxPayer taxPay = new TaxPayer.Builder("TaxPayer 1")
                .id(1)
                .taxInspectorId(1) // Assuming there's a tax inspector with ID 1
                .build();
        TaxPayerDB.insertTaxPayer(taxPay);

        TaxPayerDB.insertTaxPayer(new TaxPayer.Builder("TaxPayer 2")
                .id(2)
                .taxInspectorId(2) // Assuming there's a tax inspector with ID 2
                .build());

//         Insert some taxes for TaxPayer 1
        TaxDB.insertTax(new Tax.TaxBuilder("Income Tax")
                .id(1)
                .amount(1000.0)
                .date(LocalDate.parse("2023-01-01"))
                .paid(false)
                .dateOfPaid(null)
                .taxPayerId(1) // Assuming there's a tax payer with ID 1
                .build());
//
        TaxDB.insertTax(new Tax.TaxBuilder("Property Tax")
                .id(2)
                .amount(500.0)
                .date(LocalDate.parse("2023-02-01"))
                .paid(true)
                .dateOfPaid(LocalDate.parse("2023-02-15"))
                .taxPayerId(2) // Assuming there's a tax payer with ID 1
                .build());
//
//        // Fetch all taxes
        List<Tax> allTaxes = TaxDB.getAllTaxes();
        System.out.println("All Taxes: " + allTaxes);

//        // Update a tax
          Tax taxToUpdate = TaxDB.selectTaxes(1);
          System.out.println(taxToUpdate);
        taxToUpdate.setAmount(1200.0);
        TaxDB.updateTax(taxToUpdate);


        // Drop tables (cleanup)
//        TaxDB.dropTaxTable();
//        TaxPayerDB.dropTaxPayerTable();
//        TaxInspectorDB.dropTaxInspectorTable();
    }
}
