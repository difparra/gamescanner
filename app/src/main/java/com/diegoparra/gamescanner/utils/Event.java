package com.diegoparra.gamescanner.utils;

public class Event<T> {

    private final T content;
    private boolean hasBeenHandled;

    public Event(T content) {
        this.content = content;
    }

    public T getContentIfNotHandled() {
        if (hasBeenHandled) {
            return null;
        } else {
            hasBeenHandled = true;
            return content;
        }
    }

    public T peekContent() {
        return content;
    }

}
