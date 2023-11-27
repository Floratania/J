package l4;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.*;
import java.util.Set;

public class Tax {
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private final String name;

    @Positive(message = "Amount must be non-negative or 0")
    private final double amount;
    @NotNull
    private final LocalDate date;
    private final boolean paid;
    @PastOrPresent(message = "Date of paid must be in the past or present")
    private final LocalDate dateOfPaid;

    public Tax(TaxBuilder builder) {
        this.name = builder.name;
        this.amount = builder.amount;
        this.date = builder.date;
        this.paid = builder.paid;
        this.dateOfPaid = builder.dateOfPaid;
    }

    /**
     * Returns a string representation of the tax object.
     */
    @Override
    public String toString() {
        return "Tax{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", paid=" + paid +
                ", dateOfPaid=" + dateOfPaid +
                '}';
    }



    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isPaid() {
        return paid;
    }

    /**
     * Checks if two tax objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        l4.Tax tax = (l4.Tax) o;
        return Double.compare(tax.amount, amount) == 0 &&
                paid == tax.paid &&
                Objects.equals(name, tax.name) &&
                Objects.equals(date, tax.date) &&
                Objects.equals(dateOfPaid, tax.dateOfPaid);
    }

    /**
     * Returns the hash code of the tax object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, amount, date, paid, dateOfPaid);
    }


    public static class TaxBuilder {

        private String name;


        private double amount;

        private LocalDate date;

        private boolean paid;


        private LocalDate dateOfPaid;


        public TaxBuilder(String name) {
            this.name = name;
        }

        public TaxBuilder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public TaxBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public TaxBuilder paid(boolean paid) {
            this.paid = paid;
            return this;
        }

        public TaxBuilder dateOfPaid(LocalDate dateOfPaid) {
            if (paid) {
                this.dateOfPaid = dateOfPaid;
            } else {
                throw new IllegalStateException("Cannot set dateOfPaid when paid is false");
            }
            return this;
        }


        public Tax build() {
            Tax tax = new Tax(this);
            validate(tax);
            return tax;
        }

        private void validate(Tax tax) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<String> validationMessages = new HashSet<>();
            Set<ConstraintViolation<Tax>> violations = validator.validate(tax);


            for (ConstraintViolation<Tax> violation : violations) {
                validationMessages.add(violation.getInvalidValue() + ": " + violation.getMessage());
            }

            if (!violations.isEmpty()) {
                throw new IllegalArgumentException("Invalid fields: " + String.join(", ", validationMessages));
            }
        }
    }
}