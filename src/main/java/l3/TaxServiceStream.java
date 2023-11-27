package l3;

import l1.Tax;
import l1.TaxPayer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaxServiceStream implements ITaxService {

    private final List<Tax> taxes;
    private final List<TaxPayer> taxPayers;

    public TaxServiceStream(List<Tax> taxes, List<TaxPayer> taxPayers){
        this.taxes = taxes;
        this.taxPayers = taxPayers;
    }

    @Override
    public List<Tax> sortTaxByName() {
        List<Tax> result = new ArrayList<>(taxes);
        return result.stream()
                .sorted(Comparator.comparing(Tax::getName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Tax> sortByAmountTax() {
        List<Tax> result = new ArrayList<>(taxes);
        return result.stream()
                .sorted(Comparator.comparing(Tax::getAmount))
                .collect(Collectors.toList());
    }

    @Override
    public List<Tax> sortByLastDateTax() {
        List<Tax> result = new ArrayList<>(taxes);
        return result.stream()
                .sorted(Comparator.comparing(Tax::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Tax> getByPartNameTax(String name) {
        return taxes.stream()
                .filter(tax -> tax.getName().contains(name))
                .sorted(Comparator.comparing(Tax::getName))
                .collect(Collectors.toList());
    }




    @Override
    public List<Tax> getUnpaidTaxes() {
        return taxes.stream()
                .filter(tax -> !tax.isPaid())
                .collect(Collectors.toList());
    }

    @Override
    public List<Tax> getPaidTaxes() {
        return taxes.stream()
                .filter(Tax::isPaid)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaxPayer> sortTaxPayersByName() {
        return taxPayers.stream()
                .sorted(Comparator.comparing(TaxPayer::getName))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaxPayer> getByPartNameTaxPayer(String name) {
        return taxPayers.stream()
                .filter(tax -> tax.getName().contains(name))
                .sorted(Comparator.comparing(TaxPayer::getName))
                .collect(Collectors.toList());
    }
}
