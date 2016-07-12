package tk.davictor.endless_scroll_sample.util.lorem_ipsum.model;

import java.util.Date;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
public class Person {
    public static final String MALE = "male";
    public static final String FEMALE = "female";

    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String passportNumber;
    private String login;
    private String password;
    private String email;
    private String avatar;
    private Person.Sex sex;
    private int age;
    private Date birthdate;
    private TelephoneNumber telephoneNumber;
    private Address address;
    private Company company;

    public Person(long id, String firstName, String middleName, String lastName, String passportNumber,
                  String login, String password, String email, String avatar, Sex sex, int age, Date birthdate,
                  TelephoneNumber telephoneNumber, Address address, Company company) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.passportNumber = passportNumber;
        this.login = login;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.sex = sex;
        this.age = age;
        this.birthdate = birthdate;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
        this.company = company;
    }

    public long id() {
        return id;
    }

    public String firstName() {
        return firstName;
    }

    public String middleName() {
        return middleName;
    }

    public String lastName() {
        return lastName;
    }

    public String passportNumber() {
        return passportNumber;
    }

    public String login() {
        return login;
    }

    public String password() {
        return password;
    }

    public String email() {
        return email;
    }

    public String avatar() {
        return avatar;
    }

    public Person.Sex sex() {
        return sex;
    }

    public int age() {
        return age;
    }

    public Date birthdate() {
        return birthdate;
    }

    public TelephoneNumber telephoneNumber() {
        return telephoneNumber;
    }

    public Address address() {
        return address;
    }

    public Company company() {
        return company;
    }

    public enum Sex {
        MALE(Person.MALE),
        FEMALE(Person.FEMALE);

        private final String sex;

        /**
         * {@link String} value of sex
         */
        public String val() {
            return sex;
        }

        Sex(String sex) {
            this.sex = sex;
        }
    }
}
