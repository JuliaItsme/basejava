package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class SectionOrganization extends Section {
    private final List<Organization> orgs;

    public SectionOrganization(List<Organization> orgs) {
        Objects.requireNonNull(orgs, "org must not be null");
        this.orgs = orgs;
    }

    public List<Organization> getOrgs() {
        return orgs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionOrganization that = (SectionOrganization) o;
        return orgs.equals(that.orgs);
    }

    @Override
    public int hashCode() {
        return orgs.hashCode();
    }

    @Override
    public String toString() {
        return orgs.toString();
    }
}
