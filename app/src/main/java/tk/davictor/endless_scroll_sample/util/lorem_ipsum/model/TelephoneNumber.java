package tk.davictor.endless_scroll_sample.util.lorem_ipsum.model;

/**
 * 15.06.16
 * Created by Victor Ponomarenko
 */
public class TelephoneNumber {

    private final String countryCode;
    private final String operatorCode;
    private final String phoneNumber;

    public TelephoneNumber(String countryCode, String operatorCode, String phoneNumber) {
        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.phoneNumber = phoneNumber;
    }

    public String countryCode() {
        return countryCode;
    }

    public String operatorCode() {
        return operatorCode;
    }

    public String phoneNumber() {
        return  phoneNumber;
    }

    public String fullPhoneNumber() {
        return "+" + countryCode + operatorCode +  phoneNumber;
    }
}
