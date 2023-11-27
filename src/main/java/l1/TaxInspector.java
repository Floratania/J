package l1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TaxInspector {

    private String name;

    private int id;

    private List<TaxPayer> taxPayers;

    public TaxInspector(String name, int id, List<TaxPayer> taxPayers) {
        // You can initialize default values if needed
        this.name = name;
        this.id = id;
        this.taxPayers = new ArrayList<>();
    }
    public TaxInspector() {
        // You can initialize any default values here if needed
    }

    private TaxInspector(Builder builder) {
        this.name = builder.name;
        this.id = builder.id;
        this.taxPayers = builder.taxPayers;
    }

    /**
     * Returns a string representation of the tax inspector object.
     */
    @Override
    public String toString() {
        return "TaxInspector{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", taxesPayers=" + taxPayers +
                '}';
    }

    /**
     * Checks if two tax inspector objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxInspector that = (TaxInspector) o;
        return id == that.id;
    }

    /**
     * Returns the hash code of the tax inspector object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public List<TaxPayer> getTaxPayers() {
        return taxPayers;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<TaxPayer> getTaxPayer(){
        return taxPayers;
    }


    public void addAllTaxPayer(List<TaxPayer> taxPayers) {
        this.taxPayers.addAll(taxPayers);
    }

    public void addTaxPayer(TaxPayer taxPayer) {
        if (this.taxPayers == null) {
            this.taxPayers = new ArrayList<>();
        }
        this.taxPayers.add(taxPayer);
    }


    /**
     * A builder class for constructing tax inspector objects.
     */
    public static class Builder {
        private final String name;
        private final int id;
        private final List<TaxPayer> taxPayers;

        /**
         * Constructor for the tax inspector builder.
         *
         * @param name The name of the inspector.
         * @param id   The identifier of the inspector.
         */
        public Builder(String name, int id) {
            this.name = name;
            this.id = id;
            this.taxPayers =  new ArrayList<>();
        }

        /**
         * Set the list of taxpayers associated with the inspector.
         *
         * @param taxPayer The list of taxpayers.
         * @return A tax inspector builder.
         */
        public Builder addTaxPayer(TaxPayer taxPayer) {
            this.taxPayers.add(taxPayer);
            return this;
        }


        /**
         * Build a tax inspector object.
         *
         * @return A tax inspector object.
         */
        public TaxInspector build() {
            return new TaxInspector(this);
        }

        public Builder addAllTaxPayers(List<TaxPayer> taxPayers) {
            this.taxPayers.addAll(taxPayers);
            return this;
        }

    }
}
