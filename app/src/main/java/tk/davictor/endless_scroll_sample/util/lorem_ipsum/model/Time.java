package tk.davictor.endless_scroll_sample.util.lorem_ipsum.model;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
public class Time {

    private int hours;
    private int minutes;
    private int seconds;
    private int milliseconds;

    public Time(int hours, int minutes, int seconds, int milliseconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
    }

    public int hours() {
        return hours;
    }

    public int minutes() {
        return minutes;
    }

    public int seconds() {
        return seconds;
    }

    public int milliseconds() {
        return milliseconds;
    }
}
