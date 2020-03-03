package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class SectionList extends Section {
    private final List<String> parts;

    public SectionList(List<String> parts) {
        Objects.requireNonNull(parts, "part must not be null");
        this.parts = parts;
    }

    public List<String> getPart() {
        return parts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionList that = (SectionList) o;
        return parts.equals(that.parts);
    }

    @Override
    public int hashCode() {
        return parts.hashCode();
    }

    @Override
    public String toString() {
        return parts.toString();
    }
}
