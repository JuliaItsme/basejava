package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class SectionOrganization extends Section {
    private final List<Organization> org;

    public SectionOrganization(List<Organization> org) {
        Objects.requireNonNull(org, "org must not be null");
        this.org = org;
    }

    public List<Organization> getOrg() {
        return org;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionOrganization that = (SectionOrganization) o;
        return org.equals(that.org);
    }

    @Override
    public int hashCode() {
        return org.hashCode();
    }

    @Override
    public String toString() {
        return org.toString();
    }
}
