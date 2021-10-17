package com.diegoparra.gamescanner.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

public class EventObserver<T> implements Observer<Event<T>> {

    private final OnEventUnhandledContent<T> onEventUnhandledContent;

    public EventObserver(@NonNull OnEventUnhandledContent<T> onEventUnhandledContent) {
        this.onEventUnhandledContent = onEventUnhandledContent;
    }

    @Override
    public void onChanged(Event<T> event) {
        if (event != null) {
            T content = event.getContentIfNotHandled();
            if (content != null) {
                onEventUnhandledContent.perform(content);
            }
        }
    }

    public interface OnEventUnhandledContent<T> {
        void perform(T content);
    }
}
