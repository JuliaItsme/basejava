package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class SectionList extends Section {
    private final List<String> part;

    public SectionList(List<String> part) {
        Objects.requireNonNull(part, "part must not be null");
        this.part = part;
    }

    public List<String> getPart() {
        return part;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionList that = (SectionList) o;
        return part.equals(that.part);
    }

    @Override
    public int hashCode() {
        return part.hashCode();
    }

    @Override
    public String toString() {
        return part.toString();
    }
}
