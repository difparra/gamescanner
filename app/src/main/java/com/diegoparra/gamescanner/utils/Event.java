package com.diegoparra.gamescanner.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

}
