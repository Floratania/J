package l4;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.*;

public class TaxPayer {

    @NotBlank(message = "Name cannot be blank")
    private final String name;
    @Positive(message = "ID must be a positive integer")
    private int id;
    private long taxInspectorId;
    private final List<Tax> taxes;

    // Private constructor for the builder


    public TaxPayer(Builder builder) {
        this.name = builder.name;
        this.id = builder.id;
        this.taxes = builder.taxes;
        this.taxInspectorId = builder.taxInspectorId;
    }



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

    public int getId() {
        return id;
    }

    public void addTax(Tax tax) {
        this.taxes.add(tax);
    }

    public int getTaxPayerId() {
        return id;
    }

    public long getTaxInspectorId() {
        return taxInspectorId;
    }

    public void setTaxInspectorId(long taxInspectorId) {
        this.taxInspectorId = taxInspectorId;
    }

    public void setId(int generatedId) {
        this.id = generatedId;
    }

    /**
     * A builder class for constructing taxpayer objects.
     */
    public static class Builder {
        private final String name;
        private int id;
        private List<Tax> taxes = new ArrayList<>();
        private long taxInspectorId;

        /**
         * Constructor for the taxpayer builder.
         *
         * @param name The name of the taxpayer.
         */
        public Builder(String name) {
            this.name = name;
        }



        public Builder id(int id){
            this.id = id;
            return this;
        }
        public Builder taxInspectorId(long i) {
            this.taxInspectorId = i;
            return this;
        }

        public Builder taxes(List<Tax> taxes){
            this.taxes = taxes;
            return this;
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
            TaxPayer taxpayer = new TaxPayer(this);
            validate(taxpayer);
            return taxpayer;
        }

        private void validate(TaxPayer taxpayer) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            StringBuilder validationMessages = new StringBuilder();
            Set<ConstraintViolation<TaxPayer>> violations = validator.validate(taxpayer);

            for (ConstraintViolation<TaxPayer> violation : violations) {
                validationMessages.append(violation.getPropertyPath()).append(": ")
                        .append(violation.getInvalidValue()).append(": ")
                        .append(violation.getMessage()).append(", ");
            }

            if (!validationMessages.isEmpty()) {
                validationMessages.delete(validationMessages.length() - 2, validationMessages.length());  // Remove trailing comma and space
                throw new IllegalArgumentException("Invalid fields: " + validationMessages.toString());
            }
        }
    }
}