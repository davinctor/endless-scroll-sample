package tk.davictor.endless_scroll_sample.util.lorem_ipsum.model;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
public class Company {

    private String name;
    private String domain;
    private String email;

    public Company(String name, String domain, String email) {
        this.name = name;
        this.domain = domain;
        this.email = email;
    }

    public String name() {
        return name;
    }

    public String domain() {
        return domain;
    }

    public String email() {
        return email;
    }
}
