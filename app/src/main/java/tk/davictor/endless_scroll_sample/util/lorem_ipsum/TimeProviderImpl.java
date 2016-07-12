package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import tk.davictor.endless_scroll_sample.util.lorem_ipsum.model.Time;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
class TimeProviderImpl implements TimeProvider {

    private static final int MAX_HOURS = 23;
    private static final int MAX_MINUTES = 59;
    private static final int MAX_SECONDS = 59;
    private static final int MAX_MILLIS = 999;

    private final BaseProducer baseProducer;

    public TimeProviderImpl(BaseProducer baseProducer) {
        this.baseProducer = baseProducer;
    }

    @Override
    public Time provideTime() {
        int hours =  baseProducer.randomInt(MAX_HOURS);
        int minutes = baseProducer.randomInt(MAX_MINUTES);
        int seconds = baseProducer.randomInt(MAX_SECONDS);
        int millis = baseProducer.randomInt(MAX_MILLIS);

        return new Time(hours, minutes, seconds, millis);
    }
}
