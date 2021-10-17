package com.diegoparra.gamescanner.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class EventObserverTest {

    @Test
    public void onChanged_calledTwice_runOnlyFirstTimeWhenNotHandled() {
        AtomicBoolean called = new AtomicBoolean(false);
        EventObserver<String> eventObserver = new EventObserver<>(content -> {
            if(called.get()) {
                Assert.fail("This function has already been called!");
            }
            called.set(true);
        });

        String content = "content";
        Event<String> event = new Event<>(content);
        eventObserver.onChanged(event);
        eventObserver.onChanged(event);
    }

}