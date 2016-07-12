package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import java.util.Calendar;
import java.util.Date;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
class DateProviderImpl implements DateProvider {

    private final BaseProducer baseProducer;

    public DateProviderImpl(BaseProducer baseProducer, TimeProvider timeProvider) {
        this.baseProducer = baseProducer;
    }

    @Override
    public Date provideDate() {
        return new Date(baseProducer.randomLong(System.currentTimeMillis()));
    }

    @Override
    public Date provideDateInThePast(int maxYearsEarlier) {
        return new Date(provideCalendarInThePast(maxYearsEarlier).getTimeInMillis());
    }

    @Override
    public Date provideDateInTheFuture(int maxYearsInTheFuture) {
        return new Date(provideCalendarInTheFuture(maxYearsInTheFuture).getTimeInMillis());
    }

    @Override
    public Date provideDateBetweenYearAndNow(int fromYear) {
        return new Date(provideCalendarBetweenYearAndNow(fromYear).getTimeInMillis());
    }

    @Override
    public Date provideDateBetweenTwoDates(Date from, Date to) {
        Calendar cFrom = Calendar.getInstance();
        cFrom.setTimeInMillis(from.getTime());

        Calendar cTo = Calendar.getInstance();
        cTo.setTimeInMillis(to.getTime());

        return new Date(provideCalendarBetweenTwoCalendars(cFrom, cTo).getTimeInMillis());
    }

    @Override
    public Date provideDateBetweenTwoYears(int fromYear, int toYear) {
        return new Date(provideCalendarBetweenTwoYears(fromYear, toYear).getTimeInMillis());
    }

    @Override
    public Calendar provideCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(baseProducer.randomLong(calendar.getTimeInMillis()));
        return calendar;
    }

    @Override
    public Calendar provideCalendarInThePast(int maxYearsEarlier) {
        Calendar latestDateInThePast = Calendar.getInstance();
        int seconds = latestDateInThePast.get(Calendar.SECOND);
        latestDateInThePast.set(Calendar.SECOND, seconds - 1);

        Calendar maxYearsEarlierDate = Calendar.getInstance();
        int years = maxYearsEarlierDate.get(Calendar.YEAR);
        maxYearsEarlierDate.set(Calendar.YEAR, years - maxYearsEarlier);

        return provideCalendarBetweenTwoCalendars(maxYearsEarlierDate, latestDateInThePast);
    }

    @Override
    public Calendar provideCalendarInTheFuture(int maxYearsInTheFuture) {
        Calendar earlierDateInTheFuture = Calendar.getInstance();
        int seconds = earlierDateInTheFuture.get(Calendar.SECOND);
        earlierDateInTheFuture.set(Calendar.SECOND, seconds + 1);

        Calendar maxYearsEarlierDate = Calendar.getInstance();
        int years = maxYearsEarlierDate.get(Calendar.YEAR);
        maxYearsEarlierDate.set(Calendar.YEAR, years + maxYearsInTheFuture);

        return provideCalendarBetweenTwoCalendars(earlierDateInTheFuture, maxYearsEarlierDate);
    }

    @Override
    public Calendar provideCalendarBetweenYearAndNow(int fromYear) {
        int actualYear = Calendar.getInstance().get(Calendar.YEAR);
        return provideCalendarInThePast(actualYear - fromYear);
    }

    @Override
    public Calendar provideCalendarBetweenTwoCalendars(Calendar from, Calendar to) {
        Calendar calendar = Calendar.getInstance();
        long left = from.getTimeInMillis();
        long right = to.getTimeInMillis();
        calendar.setTimeInMillis(baseProducer.randomBetween(left, right));
        return calendar;
    }

    @Override
    public Calendar provideCalendarBetweenTwoYears(int fromYear, int toYear) {
        if (fromYear > toYear) {
            throw new IllegalArgumentException("fromYear must be greater or equal to toYear");
        }

        Calendar fromDate = Calendar.getInstance();
        fromDate.set(Calendar.DAY_OF_MONTH, 1);
        fromDate.set(Calendar.MONTH, Calendar.JANUARY);

        Calendar toDate = Calendar.getInstance();
        toDate.set(Calendar.MONTH, Calendar.DECEMBER);
        toDate.set(Calendar.DAY_OF_MONTH, toDate.getActualMaximum(Calendar.DAY_OF_MONTH));

        return provideCalendarBetweenTwoCalendars(fromDate, toDate);
    }
}
