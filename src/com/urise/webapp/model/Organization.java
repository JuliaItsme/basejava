package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private final Information info;

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.homePage = new Link(name, url);
        this.info = new Information(startDate, endDate, title, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(homePage, that.homePage) &&
                Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, info);
    }

    @Override
    public String toString() {
        return "Organization(" + homePage + info + ')';
    }
}
