package l3;

import l1.Tax;
import l1.TaxPayer;

import java.util.List;

/**
 * The {@code ITaxService} interface defines methods for managing taxes and taxpayers.
 * Implementing classes should provide functionality for sorting taxes and taxpayers,
 * retrieving taxes based on various criteria, and distinguishing between paid and unpaid taxes.
 */
public interface ITaxService {

    /**
     * Sorts the list of taxes alphabetically by name.
     *
     * @return A list of taxes sorted by name.
     */
    List<Tax> sortTaxByName();

    /**
     * Sorts the list of taxes by the amount of tax.
     *
     * @return A list of taxes sorted by the amount of tax.
     */
    List<Tax> sortByAmountTax();

    /**
     * Sorts the list of taxes by the last payment date.
     *
     * @return A list of taxes sorted by the last payment date.
     */
    List<Tax> sortByLastDateTax();

    /**
     * Retrieves a list of taxes based on a partial match of the taxpayer's name.
     *
     * @param name The partial name to search for.
     * @return A list of taxes matching the specified partial name.
     */
    List<Tax> getByPartNameTax(String name);

    /**
     * Retrieves a list of unpaid taxes.
     *
     * @return A list of unpaid taxes.
     */
    List<Tax> getUnpaidTaxes();

    /**
     * Retrieves a list of paid taxes.
     *
     * @return A list of paid taxes.
     */
    List<Tax> getPaidTaxes();

    /**
     * Sorts the list of taxpayers alphabetically by name.
     *
     * @return A list of taxpayers sorted by name.
     */
    List<TaxPayer> sortTaxPayersByName();

    /**
     * Retrieves a list of taxpayers based on a partial match of the taxpayer's name.
     *
     * @param name The partial name to search for.
     * @return A list of taxpayers matching the specified partial name.
     */
    List<TaxPayer> getByPartNameTaxPayer(String name);
}
