package tk.davictor.endless_scroll_sample.util.lorem_ipsum;


import tk.davictor.endless_scroll_sample.util.lorem_ipsum.model.Company;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
class CompanyProviderImpl implements CompanyProvider {

    private static final String DOT = ".";
    private static final String SPACE = " ";
    private static final String SIGN_AT = "@";

    private final BaseProducer baseProducer;
    private final DataProvider dataProvider;

    public CompanyProviderImpl(BaseProducer baseProducer, DataProvider dataProvider) {
        this.baseProducer = baseProducer;
        this.dataProvider = dataProvider;
    }

    @Override
    public Company provideCompany() {
        String name = dataProvider.getRandString(Contract.COMPANY_NAME);
        if (baseProducer.trueOrFalse()) {
            name += SPACE + dataProvider.getRandString(Contract.COMPANY_SUFFIX);
        }
        String domain = TextUtilsExtension.removeWhitespaces(name.toLowerCase()) + DOT +
                dataProvider.getRandString(Contract.COMPANY_DOMAIN);
        String email = dataProvider.getRandString(Contract.COMPANY_EMAIL) + SIGN_AT + domain;

        return new Company(name, domain, email);
    }
}
