package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

/**
 * 15.06.16
 * Created by Victor Ponomarenko
 */
public class PassportProviderImpl implements PassportProvider {
    private final BaseProducer baseProducer;
    private final DataProvider dataProvider;

    public PassportProviderImpl(BaseProducer baseProducer, DataProvider dataProvider) {
        this.baseProducer = baseProducer;
        this.dataProvider = dataProvider;
    }

    @Override
    public String providePassportNumber() {
        String prefixFormat = dataProvider.getString(Contract.PASSPORT_PREFIX);
        String suffixFormat = dataProvider.getString(Contract.PASSPORT_SUFFIX);

        String prefix = baseProducer.letterify(prefixFormat);
        String suffix = baseProducer.numerify(suffixFormat);

        return prefix + suffix;
    }

}
