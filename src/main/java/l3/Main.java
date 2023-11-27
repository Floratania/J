package l3;


import l1.Tax;
import l1.TaxPayer;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Create some taxes
        Tax tax1 = new Tax.Builder("Income Tax")
                .amount(1000)
                .date(LocalDate.of(2023, 1, 15))
                .paid(true)
                .build();

        Tax tax2 = new Tax.Builder("Property Tax")
                .amount(500)
                .date(LocalDate.of(2023, 2, 28))
                .paid(false)
                .build();

        Tax tax3 = new Tax.Builder("Sales Tax")
                .amount(200)
                .date(LocalDate.of(2023, 3, 10))
                .paid(true)
                .build();

        // Create a taxpayer and add taxes
        TaxPayer taxPayer1 = new TaxPayer.Builder("John Doe", 1)
                .addTax(tax1)
                .addTax(tax2)
                .build();

        TaxPayer taxPayer2 = new TaxPayer.Builder("Jane Smith", 2)
                .addTax(tax3)
                .build();

        // Create lists of taxes and taxpayers
        List<Tax> taxes = Arrays.asList(tax1, tax2, tax3);
        List<TaxPayer> taxPayers = Arrays.asList(taxPayer1, taxPayer2);

        // Create TaxService and TaxServiceStream instances
        ITaxService taxService = new TaxService(taxes, taxPayers);
        ITaxService taxServiceStream = new TaxServiceStream(taxes, taxPayers);

        // Use the methods of ITaxService
        System.out.println("\nSorted Taxes by Name:");
        taxService.sortTaxByName().forEach(System.out::println);

        System.out.println("\nSorted Taxes by Amount:");
        taxService.sortByAmountTax().forEach(System.out::println);

        System.out.println("\nSorted Taxes by Date:");
        taxService.sortByLastDateTax().forEach(System.out::println);

        System.out.println("\nGet Taxes by part Name:");
        taxService.getByPartNameTax("come").forEach(System.out::println);


        System.out.println("\nUnpaid Taxes:");
        taxService.getUnpaidTaxes().forEach(System.out::println);


        System.out.println("\nPaid Taxes:");
        taxService.getPaidTaxes().forEach(System.out::println);

        System.out.println("\nTaxpayers Sorted by Name:");
        taxService.sortTaxPayersByName().forEach(System.out::println);

        System.out.println("\nGet Tax payer by part Name:");
        taxService.getByPartNameTaxPayer("Ja").forEach(System.out::println);

        // Use the methods of ITaxServiceStream
        System.out.println("\nSorted Taxes by Name using Stream:");
        taxServiceStream.sortTaxByName().forEach(System.out::println);

        System.out.println("\nSorted Taxes by Amount using Stream:");
        taxServiceStream.sortByAmountTax().forEach(System.out::println);

        System.out.println("\nSorted Taxes by Date using Stream:");
        taxServiceStream.sortByLastDateTax().forEach(System.out::println);

        System.out.println("\nGet Taxes by part Name using Stream:");
        taxServiceStream.getByPartNameTax("come").forEach(System.out::println);


        System.out.println("\nUnpaid Taxes using Stream:");
        taxServiceStream.getUnpaidTaxes().forEach(System.out::println);


        System.out.println("\nPaid Taxes using Stream:");
        taxServiceStream.getPaidTaxes().forEach(System.out::println);

        System.out.println("\nTaxpayers Sorted by Name using Stream:");
        taxServiceStream.sortTaxPayersByName().forEach(System.out::println);

        System.out.println("\nGet Tax payer by part Name using Stream:");
        taxServiceStream.getByPartNameTaxPayer("Ja").forEach(System.out::println);


    }
}
