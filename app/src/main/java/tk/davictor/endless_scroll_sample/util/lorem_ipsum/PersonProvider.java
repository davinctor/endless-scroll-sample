package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import tk.davictor.endless_scroll_sample.util.lorem_ipsum.model.Person;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
public interface PersonProvider {

    /**
     * Generate person
     * @return new instance of {@link Person} with generated fields
     */
    Person providePerson();
}
