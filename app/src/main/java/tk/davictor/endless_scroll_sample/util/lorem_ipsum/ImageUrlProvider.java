package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import android.support.annotation.Nullable;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
public interface ImageUrlProvider {

    /**
     * Generate url to random image with:
     * <ul>
     * <li>random width/height from 0 to 1920</li>
     * <li>random choice: colored or grey image</li>
     * <li>random image category</li>
     * </ul>
     *
     * @return {@link String} url to image
     */
    String provideImage();

    /**
     * Generate url to random image with:
     * <ul>
     * <li><b><u>specified</u></b> width/height from 0 to 1920</li>
     * <li>random choice: colored or grey image</li>
     * <li>random image category</li>
     * </ul>
     *
     * @return {@link String} url to image
     */
    String provideImage(int width, int height);

    /**
     * Generate url to random image with:
     * <ul>
     * <li><b><u>specified</u></b> width/height from 0 to 1920</li>
     * <li><b><u>specified</u></b> choice: colored or grey image</li>
     * <li><b><u>specified</u></b> image category</li>
     * </ul>
     *
     * @return {@link String} url to image
     */
    String provideImage(@Nullable String category, boolean isColored, int width, int height);
}
