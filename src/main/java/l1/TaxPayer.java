package l1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TaxPayer implements Comparable<TaxPayer>{

    private String name;

    private int id;

    private List<Tax> taxes;
    public TaxPayer() {
        // You can initialize any default values here if needed
    }

    public TaxPayer(String name, int id) {
        this.name = name;
        this.id = id;
        this.taxes = new ArrayList<>();
    }
    private TaxPayer(Builder builder) {
        this.name = builder.name;
        this.id = builder.id;
        this.taxes = builder.taxes;
    }

    /**
     * Returns a string representation of the taxpayer object.
     */
    @Override
    public String toString() {
        return "TaxPayer{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", taxes=" + taxes +
                '}';
    }

    /**
     * Checks if two taxpayer objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxPayer taxPayer = (TaxPayer) o;
        return id == taxPayer.id;
    }



    /**
     * Returns the hash code of the taxpayer object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns the name of the taxpayer.
     *
     * @return The name of the taxpayer.
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the list of taxes associated with the taxpayer.
     *
     * @return The list of taxes.
     */

    public List<Tax> getTaxes() {
        return taxes;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public void addTax(Tax tax) {
        this.taxes.add(tax);
    }

    @Override
    public int compareTo(TaxPayer taxPayer) {
        return 0;
    }


    /**
     * A builder class for constructing taxpayer objects.
     */
    public static class Builder {
        private final String name;
        private final int id;
        private final List<Tax> taxes;


        /**
         * Constructor for the taxpayer builder.
         *
         * @param name The name of the taxpayer.
         * @param id   The identifier of the taxpayer.
         */
        public Builder(String name, int id) {
            this.name = name;
            this.id = id;
            this.taxes =  new ArrayList<>();
        }

        /**
         * Add a tax to the taxpayer.
         *
         * @param tax The tax to add.
         * @return A taxpayer builder.
         */
        public Builder addTax(Tax tax) {
            this.taxes.add(tax);
            return this;
        }




        /**
         * Build a taxpayer object.
         *
         * @return A taxpayer object.
         */
        public TaxPayer build() {
            return new TaxPayer(this);
        }

        public Builder addAllTaxes(List<Tax> taxes) {
            this.taxes.addAll(taxes);
            return this;
        }

    }
}
