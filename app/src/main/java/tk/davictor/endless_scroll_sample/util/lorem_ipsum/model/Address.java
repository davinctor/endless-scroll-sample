package tk.davictor.endless_scroll_sample.util.lorem_ipsum.model;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
public class Address {

    private final String postalCode;
    private final String country;
    private final String city;
    private final String street;
    private final String streetNumber;
    private final String apartmentNumber;

    public Address(String apartmentNumber, String streetNumber, String street, String city, String country, String postalCode) {
        this.apartmentNumber = apartmentNumber;
        this.streetNumber = streetNumber;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
    }

    public String postalCode() {
        return postalCode;
    }

    public String city() {
        return city;
    }

    public String country() {
        return country;
    }

    public String street() {
        return street;
    }

    public String streetNumber() {
        return streetNumber;
    }

    public String apartmentNumber() {
        return apartmentNumber;
    }
}
