package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import tk.davictor.endless_scroll_sample.util.lorem_ipsum.model.Company;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
public interface CompanyProvider {

    /**
     * Return generated company
     * @return {@link Company}
     */
    Company provideCompany();

}