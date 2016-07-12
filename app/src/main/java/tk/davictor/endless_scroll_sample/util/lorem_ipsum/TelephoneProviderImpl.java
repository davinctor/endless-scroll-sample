package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import tk.davictor.endless_scroll_sample.util.lorem_ipsum.model.TelephoneNumber;

/**
 * 15.06.16
 * Created by Victor Ponomarenko
 */
public class TelephoneProviderImpl implements TelephoneProvider {

    private final DataProvider dataProvider;
    private final BaseProducer baseProducer;

    public TelephoneProviderImpl(BaseProducer baseProducer, DataProvider dataProvider) {
        this.baseProducer = baseProducer;
        this.dataProvider = dataProvider;
    }

    @Override
    public TelephoneNumber provideNumber() {
        String countryCodeFormat = dataProvider.getString(Contract.TELEPHONE_NUMBER_COUNTRY_CODE_FORMAT);
        String operatorFormat = dataProvider.getString(Contract.TELEPHONE_NUMBER_OPERATOR_FORMAT);
        String telephoneFormat = dataProvider.getRandString(Contract.TELEPHONE_NUMBER_FORMAT);

        String countyCode = baseProducer.numerify(countryCodeFormat);
        String operatorCode = baseProducer.numerify(operatorFormat);
        String telephone = baseProducer.numerify(telephoneFormat);

        return new TelephoneNumber(countyCode, operatorCode, telephone);
    }

}
