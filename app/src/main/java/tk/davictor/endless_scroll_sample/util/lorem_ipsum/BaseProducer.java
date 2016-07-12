package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
class BaseProducer {
    private final Random rand = new Random();

    public boolean trueOrFalse() {
        return rand.nextBoolean();
    }

    /**
     * Returns random int value
     *
     * @param min value of the random number to be returned.  Must be positive.
     * @param max value of the random number to be returned.  Must be positive.
     * @return random {@code int} value between {@code min} (inclusive) and {@code max} (inclusive)
     */
    public int randomBetween(int min, int max) {
        int range = max - min + 1;
        int randomInt = range > 0 ? rand.nextInt(range) : 0;
        return min + randomInt;
    }

    /**
     * Returns random long value
     *
     * @param min value of the random number to be returned.  Must be positive.
     * @param max value of the random number to be returned.  Must be positive.
     * @return random {@code int} value between {@code min} (inclusive) and {@code max} (inclusive)
     */
    public long randomBetween(long min, long max) {
        long range = max - min + 1L;
        long randomInt = range > 0L ? rand.nextLong() : 0L;
        return min + randomInt;
    }

    /**
     * Returns random element from passed vararg
     *
     * @param <T> element generic type
     * @param elements objects to process
     * @return random element
     */
    @SafeVarargs
    public final <T> T randomElement(T... elements) {
        if (elements == null) {
            return null;
        }

        return elements[randomBetween(0, elements.length - 1)];
    }

    /**
     * Returns random element from passed List
     *
     * @param <T> element generic type
     * @param elements collection to process
     * @return random list element
     */
    public <T> T randomElement(Collection<T> elements) {
        if (elements == null || elements.isEmpty()) {
            return null;
        }

        Iterator<T> it = elements.iterator();
        T result = null;
        int i = 0;
        int needPosition = randomBetween(0, elements.size() - 1);
        while (it.hasNext()) {
            if (needPosition == i++) {
                result = it.next();
                break;
            }
            it.next();
        }

        return result;
    }

    /**
     * Returns random int value
     *
     * @param max value of the random number to be returned.  Must be positive.
     * @return random {@code int} value between 0 (inclusive) and {@code max} (inclusive)
     */
    public int randomInt(int max) {
        return randomBetween(0, max);
    }

    /**
     * Returns random int value
     *
     * @param max value of the random number to be returned.  Must be positive.
     * @return random {@code int} value between 0 (inclusive) and {@code max} (inclusive)
     */
    public long randomLong(long max) {
        return randomBetween(0, max);
    }

    /**
     * Replaces all {@code '?'} characters with random chars from [a - z] range
     *
     * @param letterString text to process
     * @return text with replaces {@code '?'} chars
     */
    public String letterify(String letterString) {
        return letterify(letterString, 'a', 'z');
    }

    /**
     * Replaces all {@code '?'} characters with random chars from [{@code from} - {@code to}] range
     *
     * @param letterString text to process
     * @param from         start of the range
     * @param to           end of the range
     * @return text with replaced {@code '?'} chars
     */
    public String letterify(String letterString, char from, char to) {
        return replaceSymbolWithCharsFromTo(letterString, '?', from, to);
    }

    /**
     * Replaces all {@code '#'} characters with random numbers from [0 - 9] range
     *
     * @param numberString text to process
     * @return text with replaced '#' characters
     */
    public String numerify(String numberString) {
        return numerify(numberString, 0, 9);
    }

    /**
     * Replaces all {@code '#'} characters with random numbers from [{@code from} - {@code to}] range
     *
     * @param numberString text to process
     * @param from         start of the range
     * @param to           end of the range
     * @return text with replaced '#' characters
     */
    public String numerify(String numberString, int from, int to) {
        return replaceSymbolWithCharsFromTo(numberString, '#', Character.forDigit(from, 10), Character.forDigit(to, 10));
    }

    private String replaceSymbolWithCharsFromTo(String string, char symbol, char from, char to) {
        StringBuilder result = new StringBuilder();
        for (char aChar : string.toCharArray()) {
            if (aChar == symbol) {
                result.append(randomBetween(from, to));
            } else {
                result.append(aChar);
            }
        }
        return result.toString();
    }
}
