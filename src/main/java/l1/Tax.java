package l1;

import java.time.LocalDate;
import java.util.Objects;

public class Tax implements Comparable<Tax> {
    private final String name;
    private final double amount;
    private final LocalDate date;
    private final boolean paid;

    public Tax(Builder builder) {
        this.name = builder.name;
        this.amount = builder.amount;
        this.date = builder.date;
        this.paid = builder.paid;
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
        Tax tax = (Tax) o;
        return Double.compare(tax.amount, amount) == 0 &&
                paid == tax.paid &&
                Objects.equals(name, tax.name) &&
                Objects.equals(date, tax.date);
    }

    /**
     * Returns the hash code of the tax object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, amount, date, paid);
    }

    @Override
    public int compareTo(Tax otherTax) {
        // Compare by date first
        int dateComparison = this.date.compareTo(otherTax.date);
        if (dateComparison != 0) {
            return dateComparison;
        }

        // If dates are equal, compare by amount
        int amountComparison = Double.compare(this.amount, otherTax.amount);
        if (amountComparison != 0) {
            return amountComparison;
        }

        // If dates and amounts are equal, compare by name
        return this.name.compareTo(otherTax.name);
    }



    /**
     * A builder class for constructing tax objects.
     */
    public static class Builder {
        private final String name;
        private double amount;
        private LocalDate date;
        private boolean paid;

        /**
         * Constructor for the tax builder.
         *
         * @param name The name of the tax.
         */
        public Builder(String name) {
            this.name = name;
        }

        /**
         * Set the amount of the tax.
         *
         * @param amount The amount of the tax.
         * @return A tax builder.
         */
        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        /**
         * Set the date of the tax.
         *
         * @param date The date of the tax.
         * @return A tax builder.
         */
        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        /**
         * Set whether the tax is paid or not.
         *
         * @param paid Whether the tax is paid or not.
         * @return A tax builder.
         */
        public Builder paid(boolean paid) {
            this.paid = paid;
            return this;
        }

        /**
         * Build a tax object.
         *
         * @return A tax object.
         */
        public Tax build() {
            return new Tax(this);
        }
    }
}