package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import java.util.Calendar;
import java.util.Date;

import tk.davictor.endless_scroll_sample.util.lorem_ipsum.model.Address;
import tk.davictor.endless_scroll_sample.util.lorem_ipsum.model.Company;
import tk.davictor.endless_scroll_sample.util.lorem_ipsum.model.Person;
import tk.davictor.endless_scroll_sample.util.lorem_ipsum.model.TelephoneNumber;


/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
class PersonProviderImpl implements PersonProvider {

    private static final String SYMBOL_DOT = ".";
    private static final String SIGN_AT = "@";

    private final DataProvider dataProvider;
    private final AddressProvider addressProvider;
    private final CompanyProvider companyProvider;
    private final ImageUrlProvider imageUrlProvider;
    private final PassportProvider passportProvider;
    private final TelephoneProvider telephoneProvider;
    private final DateProvider dateProvider;

    private final BaseProducer baseProducer;

    public PersonProviderImpl(BaseProducer baseProducer, DataProvider dataProvider,
                              AddressProvider addressProvider, CompanyProvider companyProvider,
                              PassportProvider passportProvider, DateProvider dateProvider,
                              TelephoneProvider telephoneProvider, ImageUrlProvider imageUrlProvider) {
        this.dataProvider = dataProvider;
        this.addressProvider = addressProvider;
        this.companyProvider = companyProvider;
        this.imageUrlProvider = imageUrlProvider;
        this.baseProducer = baseProducer;
        this.passportProvider = passportProvider;
        this.telephoneProvider = telephoneProvider;
        this.dateProvider = dateProvider;
    }

    @Override
    public Person providePerson() {
        long id = baseProducer.randomLong(Long.MAX_VALUE - 1L);
        Person.Sex sex = baseProducer.trueOrFalse() ? Person.Sex.MALE : Person.Sex.FEMALE;
        String firstName = dataProvider.getRandString(Contract.FIRST_NAME, sex.val());
        String middleName = baseProducer.trueOrFalse() ?
                dataProvider.getRandString(Contract.FIRST_NAME, sex.val()) : "";
        String lastName = dataProvider.getRandString(Contract.LAST_NAME, sex.val());
        String login = firstName.toLowerCase() + SYMBOL_DOT + lastName.toLowerCase();
        String email = login + SIGN_AT + dataProvider.getRandString(Contract.PERSONAL_EMAIL);
        String avatar = imageUrlProvider.provideImage();
        int age = baseProducer.randomBetween(1, 130);
        Date birthdate = generateBirthdate(age);
        String passport = passportProvider.providePassportNumber();
        TelephoneNumber telephoneNumber = telephoneProvider.provideNumber();
        Company company = companyProvider.provideCompany();
        Address address = addressProvider.providerAddress();

        return new Person(id, firstName, middleName, lastName, passport, login, "", email, avatar,
                sex, age, birthdate, telephoneNumber, address, company);
    }

    private Date generateBirthdate(int age) {
        Calendar right = Calendar.getInstance();
        right.set(Calendar.YEAR, right.get(Calendar.YEAR) - age);

        Calendar left = Calendar.getInstance();
        left.set(Calendar.YEAR, right.get(Calendar.YEAR) - 1);

        return new Date(dateProvider.provideCalendarBetweenTwoCalendars(left, right).getTimeInMillis());
    }
}
