package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import tk.davictor.endless_scroll_sample.util.lorem_ipsum.model.Address;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
public class AddressProviderImpl implements AddressProvider {

    private final BaseProducer baseProducer;
    private final DataProvider dataProvider;

    public AddressProviderImpl(BaseProducer baseProducer, DataProvider dataProvider) {
        this.baseProducer = baseProducer;
        this.dataProvider = dataProvider;
    }

    @Override
    public Address providerAddress() {
        String postalCodeFormat = dataProvider.getRandString(Contract.POSTAL_CODE_FORMAT);
        String postalCode = baseProducer.numerify(postalCodeFormat);

        String city = dataProvider.getRandString(Contract.CITY);
        String country = dataProvider.getRandString(Contract.COUNTRY);
        String street = dataProvider.getRandString(Contract.STREET);
        String streetNumber = String.valueOf(baseProducer.randomInt(100));
        String apartmentNumber = baseProducer.trueOrFalse() ? String.valueOf(baseProducer.randomInt(350)) : "";

        return new Address(apartmentNumber, streetNumber, street, city, country, postalCode);
    }
}
