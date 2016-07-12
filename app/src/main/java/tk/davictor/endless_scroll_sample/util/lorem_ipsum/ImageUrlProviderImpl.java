package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.Locale;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
class ImageUrlProviderImpl implements ImageUrlProvider {
    private static final String TAG = "Lorem";
    // %1 - width, %2 - height, %3 - category, %4 - is grey
    private static final String BASE_URL_FORMAT = "http://lorempixel.com/index.php?x=%1$d&y=%2$d&cat=%3$s&gray=%4$d";

    private static final int MAX_IMAGE_WIDTH_HEIGHT = 1920;

    private static final int MAX_CATEGORY_NUMBER = 12;

    private static final int COLORED_IMAGE = 0;
    private static final int GREY_IMAGE = 1;

    public static final String CATEGORY_ABSTRACT = "abstract";
    public static final String CATEGORY_CITY = "city";
    public static final String CATEGORY_PEOPLE = "people";
    public static final String CATEGORY_TRANSPORT = "transport";
    public static final String CATEGORY_ANIMALS = "animals";
    public static final String CATEGORY_FOOD = "food";
    public static final String CATEGORY_NATURE = "nature";
    public static final String CATEGORY_BUSINESS = "business";
    public static final String CATEGORY_NIGHTLIFE = "nightlife";
    public static final String CATEGORY_SPORTS = "sports";
    public static final String CATEGORY_FASHION = "fashion";
    public static final String CATEGORY_TECHNICS = "technics";

    public static final int CATEGORY_ABSTRACT_INDEX = 1;
    public static final int CATEGORY_CITY_INDEX = 2;
    public static final int CATEGORY_PEOPLE_INDEX = 3;
    public static final int CATEGORY_TRANSPORT_INDEX = 4;
    public static final int CATEGORY_ANIMALS_INDEX = 5;
    public static final int CATEGORY_FOOD_INDEX = 6;
    public static final int CATEGORY_NATURE_INDEX = 7;
    public static final int CATEGORY_BUSINESS_INDEX = 8;
    public static final int CATEGORY_NIGHTLIFE_INDEX = 9;
    public static final int CATEGORY_SPORTS_INDEX = 10;
    public static final int CATEGORY_FASHION_INDEX = 11;
    public static final int CATEGORY_TECHNICS_INDEX = 12;

    private final BaseProducer baseProducer;

    public ImageUrlProviderImpl(BaseProducer baseProducer) {
        this.baseProducer = baseProducer;
    }

    public String provideImage() {
        int categoryIndex = baseProducer.randomInt(MAX_CATEGORY_NUMBER);
        String category = takeCategory(categoryIndex);

        int width = baseProducer.randomInt(MAX_IMAGE_WIDTH_HEIGHT);
        int height = baseProducer.randomInt(MAX_IMAGE_WIDTH_HEIGHT);

        boolean isColored = baseProducer.trueOrFalse();

        return getUrl(width, height, category, isColored);
    }

    public String provideImage(int width, int height) {
        int categoryIndex = baseProducer.randomInt(MAX_CATEGORY_NUMBER);
        String category = takeCategory(categoryIndex);

        if (width <= 0) {
            Log.w(TAG, "provideImage(int,int): width <= 0");
            width = baseProducer.randomInt(MAX_IMAGE_WIDTH_HEIGHT);
        }

        if (height <= 0) {
            Log.w(TAG, "provideImage(int,int): height <= 0");
            height = baseProducer.randomInt(MAX_IMAGE_WIDTH_HEIGHT);
        }

        boolean isColored = baseProducer.trueOrFalse();

        return getUrl(width, height, category, isColored);
    }

    public String provideImage(@Nullable String category, boolean isColored, int width, int height) {
        if (TextUtils.isEmpty(category)) {
            Log.w(TAG, "provideImage(String, boolean, int, int): category is empty, generate by itself");
            int categoryIndex = baseProducer.randomInt(MAX_CATEGORY_NUMBER);
            category = takeCategory(categoryIndex);
        }

        if (width <= 0) {
            Log.w(TAG, "provideImage(int,int): width <= 0");
            width = baseProducer.randomInt(MAX_IMAGE_WIDTH_HEIGHT);
        }

        if (height <= 0) {
            Log.w(TAG, "provideImage(int,int): height <= 0");
            height = baseProducer.randomInt(MAX_IMAGE_WIDTH_HEIGHT);
        }

        return getUrl(width, height, category, isColored);
    }

    private static String takeCategory(int index) {
        switch (index) {
            case CATEGORY_ABSTRACT_INDEX:
                return CATEGORY_ABSTRACT;
            case CATEGORY_CITY_INDEX:
                return CATEGORY_CITY;
            case CATEGORY_PEOPLE_INDEX:
                return CATEGORY_PEOPLE;
            case CATEGORY_TRANSPORT_INDEX:
                return CATEGORY_TRANSPORT;
            case CATEGORY_ANIMALS_INDEX:
                return CATEGORY_ANIMALS;
            case CATEGORY_FOOD_INDEX:
                return CATEGORY_FOOD;
            case CATEGORY_NATURE_INDEX:
                return CATEGORY_NATURE;
            case CATEGORY_BUSINESS_INDEX:
                return CATEGORY_BUSINESS;
            case CATEGORY_NIGHTLIFE_INDEX:
                return CATEGORY_NIGHTLIFE;
            case CATEGORY_SPORTS_INDEX:
                return CATEGORY_SPORTS;
            case CATEGORY_FASHION_INDEX:
                return CATEGORY_FASHION;
            case CATEGORY_TECHNICS_INDEX:
                return CATEGORY_TECHNICS;

            default:
                return CATEGORY_ABSTRACT;
        }
    }

    private static String getUrl(int width, int height, @NonNull String category, boolean isColored) {
        return String.format(Locale.US, BASE_URL_FORMAT, width, height, category, isColored ? COLORED_IMAGE : GREY_IMAGE);
    }
}
