package l3;


import l1.Tax;
import l1.TaxPayer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class TaxService implements ITaxService{

    private final List<Tax> taxes;
    private final List<TaxPayer> taxPayers;

    public TaxService(List<Tax> taxes, List<TaxPayer> taxPayers){
        this.taxes = taxes;
        this.taxPayers = taxPayers;
    }


    @Override
    public List<Tax> sortTaxByName() {
        List<Tax> result = new ArrayList<>(taxes);
        result.sort(Comparator.comparing(Tax::getName));
        return result;
    }

    @Override
    public List<Tax> sortByAmountTax() {
        List<Tax> result = new ArrayList<>(taxes);
        result.sort(Comparator.comparing(Tax::getAmount));
        return result;
    }

    @Override
    public List<Tax> sortByLastDateTax() {
        List<Tax> result = new ArrayList<>(taxes);
        result.sort(Comparator.comparing(Tax::getDate));
        return result;
    }

    @Override
    public List<Tax> getByPartNameTax(String name) {
        List<Tax> mTax = new ArrayList<>();

        for (Tax tax : taxes){
            if (tax.getName().contains(name)){
                mTax.add(tax);
            }
        }

        mTax.sort(Comparator.comparing(Tax::getName));
        return mTax;
    }


    @Override
    public List<Tax> getUnpaidTaxes() {
        // Filter out paid taxes
        List<Tax> unpaidTaxes = new ArrayList<>();
        for (Tax tax : taxes) {
            if (!tax.isPaid()) {
                unpaidTaxes.add(tax);
            }
        }

        // Sort unpaid taxes by date
        unpaidTaxes.sort(Comparator.comparing(Tax::getDate));

        return unpaidTaxes;
    }

    @Override
    public List<Tax> getPaidTaxes() {
        // Filter out paid taxes
        List<Tax> paidTaxes = new ArrayList<>();
        for (Tax tax : taxes) {
            if (tax.isPaid()) {
                paidTaxes.add(tax);
            }
        }

        // Sort unpaid taxes by date
        paidTaxes.sort(Comparator.comparing(Tax::getDate));

        return paidTaxes;
    }

    @Override
    public List<TaxPayer> sortTaxPayersByName() {
        List<TaxPayer> result = new ArrayList<>(taxPayers);
        result.sort(Comparator.comparing(TaxPayer::getName));
        return result;
    }

    @Override
    public List<TaxPayer> getByPartNameTaxPayer(String name) {
        List<TaxPayer> mTaxPayer = new ArrayList<>();

        for (TaxPayer taxPayer : taxPayers){
            if (taxPayer.getName().contains(name)){
                mTaxPayer.add(taxPayer);
            }
        }

        mTaxPayer.sort(Comparator.comparing(TaxPayer::getName));
        return mTaxPayer;
    }
}
