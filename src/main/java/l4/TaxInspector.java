package l4;

import java.util.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class TaxInspector {

    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotNull
    @Min(value = 1, message = "id cant be less than 1")
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

    public TaxInspector(Builder builder) {
        this.name = builder.name;
        this.id = builder.id;
        this.taxPayers = builder.taxPayers;
    }

    public TaxInspector(String name) {
        this.name = name;
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

    public void setId(long id) {
        this.id = (int) id;
    }


    /**
     * A builder class for constructing tax inspector objects.
     */
    public static class Builder {


        private String name;

        private int id;

        private List<TaxPayer> taxPayers;

        public Builder(String name) {
            this.name = name;
        }

        public Builder id(int id){
            this.id = id;
            return this;
        }

        public Builder taxPayers(List<TaxPayer> taxPayers){
            this.taxPayers = taxPayers;
            return this;
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
            TaxInspector inspector = new TaxInspector(this);
            validate(inspector);
            return inspector;
        }

        private void validate(TaxInspector inspector) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<String> validationMessages = new HashSet<>();
            Set<ConstraintViolation<TaxInspector>> violations = validator.validate(inspector);

            for (ConstraintViolation<TaxInspector> violation : violations) {
                validationMessages.add(violation.getInvalidValue() + ": " + violation.getMessage());
            }

            if (!violations.isEmpty()) {
                throw new IllegalArgumentException("Invalid fields: " + String.join(", ", validationMessages));
            }
        }
    }
}