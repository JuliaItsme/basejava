package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homePage;
    private final List<Information> info;

    public Organization(String name, String url, Information... info) {
        this.homePage = new Link(name, url);
        this.info = Arrays.asList(info);
    }

    public Organization(String name, String url, ArrayList<Information> info) {
        this.homePage = new Link(name, url);
        this.info = info;
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
