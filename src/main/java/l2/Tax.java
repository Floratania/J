package l2;

import java.time.LocalDate;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlRootElement
public class Tax {
    @JsonProperty
    @XmlElement
    private String name;
    @JsonProperty
    @XmlElement
    private double amount;
    @JsonProperty
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate date;
    @JsonProperty
    @XmlElement
    private boolean paid;
    // Default constructor with no arguments
    public Tax() {
        // You can initialize any default values here if needed
    }

    public Tax(String name, double amount, LocalDate date, boolean paid) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.paid = paid;
    }
    private Tax(Builder builder) {
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

    public String getName() {
        return name;
    }

    public String getAmount() {
        return String.valueOf(amount);
    }

    public LocalDate getDate() {
        return date;
    }

    public String isPaid() {
        return String.valueOf(paid);
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
