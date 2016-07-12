package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 13.06.16
 * Created by Victor Ponomarenko
 */
public final class Lorem {

    @SuppressWarnings("FieldCanBeLocal")
    private final BaseProducer baseProducer;
    private final CompanyProvider companyProvider;
    private final ImageUrlProvider imageUrlProvider;
    private final AddressProvider addressProvider;
    private final DateProvider dateProvider;
    private final TimeProvider timeProvider;
    private final PassportProvider passportProvider;
    private final TelephoneProvider telephoneProvider;

    private final PersonProvider personProvider;

    private static Lorem INSTANCE;

    private Lorem(@NonNull Context context) {
        context = context.getApplicationContext();
        this.baseProducer = new BaseProducer();
        DataProvider dataProvider = new DataProviderImpl(context, baseProducer);
        this.companyProvider = new CompanyProviderImpl(baseProducer, dataProvider);
        this.imageUrlProvider = new ImageUrlProviderImpl(baseProducer);
        this.addressProvider = new AddressProviderImpl(baseProducer, dataProvider);
        this.timeProvider = new TimeProviderImpl(baseProducer);
        this.dateProvider = new DateProviderImpl(baseProducer, timeProvider);
        this.telephoneProvider = new TelephoneProviderImpl(baseProducer, dataProvider);
        this.passportProvider = new PassportProviderImpl(baseProducer, dataProvider);
        this.personProvider = new PersonProviderImpl(baseProducer, dataProvider, addressProvider,
                companyProvider, passportProvider, dateProvider, telephoneProvider, imageUrlProvider);
    }

    public static void init(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Lorem(context);
        }
    }

    public static Lorem instance() {
        return INSTANCE;
    }

    /**
     * Get instance {@link CompanyProvider}
     */
    public CompanyProvider company() {
        return companyProvider;
    }

    /**
     * Get instance {@link ImageUrlProvider}
     */
    public ImageUrlProvider imageUrl() {
        return imageUrlProvider;
    }

    /**
     * Get instance {@link AddressProvider}
     */
    public AddressProvider address() {
        return addressProvider;
    }

    /**
     * Get instance {@link DateProvider}
     */
    public DateProvider date() {
        return dateProvider;
    }

    /**
     * Get instance {@link TimeProvider}
     */
    public TimeProvider time() {
        return timeProvider;
    }

    /**
     * Get instance {@link PassportProvider}
     */
    public PassportProvider passport() {
        return passportProvider;
    }

    /**
     * Get instance {@link TelephoneProvider}
     */
    public TelephoneProvider telephone() {
        return telephoneProvider;
    }

    /**
     * Get instance {@link PersonProvider}
     */
    public PersonProvider person() {
        return personProvider;
    }
}
