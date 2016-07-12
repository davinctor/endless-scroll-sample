package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import java.util.Calendar;
import java.util.Date;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
public interface DateProvider {

    /**
     * Provide generated instance of {@link Date}
     */
    Date provideDate();

    /**
     * Provide random date in the past
     * @param maxYearsEarlier max years in the past from today
     */
    Date provideDateInThePast(int maxYearsEarlier);

    /**
     * Provide random date in the future
     * @param maxYearsInTheFuture max years in the future from today
     */
    Date provideDateInTheFuture(int maxYearsInTheFuture);

    /**
     * Provide random date from 'fromYear' and today
     * @param fromYear left range value
     * @return {@link Date} value in range
     */
    Date provideDateBetweenYearAndNow(int fromYear);

    /**
     * Provide date in specified range by two dates
     * @param from date from
     * @param to date to
     * @return {@link Date} value in range
     */
    Date provideDateBetweenTwoDates(Date from, Date to);

    /**
     * Provide date in specified years range
     * @param fromYear year from
     * @param toYear year to
     * @return {@link Date} value in range
     */
    Date provideDateBetweenTwoYears(int fromYear, int toYear);

    /**
     * Provide generated instance of {@link java.util.Calendar}
     */
    Calendar provideCalendar();

    /**
     * Provide random date in the past
     * @param maxYearsEarlier max years in the past from today
     * @return {@link Calendar} value in range
     */
    Calendar provideCalendarInThePast(int maxYearsEarlier);

    /**
     * Provide random date in the future
     * @param maxYearsInTheFuture max years in the future from today
     * @return {@link Calendar} value in range
     */
    Calendar provideCalendarInTheFuture(int maxYearsInTheFuture);

    /**
     * Provide random date from 'fromYear' and today
     * @param fromYear left range value
     * @return {@link Calendar} value in range
     */
    Calendar provideCalendarBetweenYearAndNow(int fromYear);

    /**
     * Provide date in specified range by two dates
     * @param from date from
     * @param to date to
     * @return {@link Calendar} value in range
     */
    Calendar provideCalendarBetweenTwoCalendars(Calendar from, Calendar to);

    /**
     * Provide date in specified years range
     * @param fromYear year from
     * @param toYear year to
     * @return {@link Calendar} value in range
     */
    Calendar provideCalendarBetweenTwoYears(int fromYear, int toYear);
}
