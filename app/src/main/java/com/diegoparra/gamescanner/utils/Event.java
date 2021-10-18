package com.diegoparra.gamescanner.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class Event<T> {

    private final T content;
    private boolean hasBeenHandled;

    public Event(@NonNull T content) {
        this.content = content;
    }

    @Nullable
    public T getContentIfNotHandled() {
        if (hasBeenHandled) {
            return null;
        } else {
            hasBeenHandled = true;
            return content;
        }
    }

    @NonNull
    public T peekContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event<?> event = (Event<?>) o;
        return hasBeenHandled == event.hasBeenHandled && Objects.equals(content, event.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, hasBeenHandled);
    }
}
