package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Information {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    public Information(LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "start must not be null");
        Objects.requireNonNull(endDate, "end must not be null");
        Objects.requireNonNull(title, "title must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Information info = (Information) o;
        return Objects.equals(startDate, info.startDate) &&
                Objects.equals(endDate, info.endDate) &&
                Objects.equals(title, info.title) &&
                description.equals(info.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, title, description);
    }

    @Override
    public String toString() {
        return "Information (" + startDate + endDate + title + description + ')';
    }
}
