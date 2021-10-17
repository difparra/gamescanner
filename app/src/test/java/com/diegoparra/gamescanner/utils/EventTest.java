package com.diegoparra.gamescanner.utils;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class EventTest {

    @Test
    public void getContentIfNotHandled_firstTime_returnValue() {
        String content = "content";
        Event<String> event = new Event<>(content);

        String response = event.getContentIfNotHandled();

        assertThat(response).isEqualTo(content);
    }

    @Test
    public void getContentIfNotHandled_secondTime_returnNull(){
        String content = "content";
        Event<String> event = new Event<>(content);

        String response1 = event.getContentIfNotHandled();
        String response2 = event.getContentIfNotHandled();

        assertThat(response1).isEqualTo(content);
        assertThat(response2).isNull();
    }


    @Test
    public void getContentIfNotHanded_peekContentDoesNotAffectGetContentIfNotHandled_returnValue(){
        String content = "content";
        Event<String> event = new Event<>(content);

        String response1 = event.peekContent();
        String response2 = event.getContentIfNotHandled();

        assertThat(response2).isEqualTo(content);
    }

    @Test
    public void peekContent_simpleCase_returnValue(){
        String content = "content";
        Event<String> event = new Event<>(content);

        String response = event.peekContent();

        assertThat(response).isEqualTo(content);
    }

    @Test
    public void peekContent_getContentIfNotHandledResturnNull_peekContentReturnValue() {
        String content = "content";
        Event<String> event = new Event<>(content);

        String response1 = event.getContentIfNotHandled();
        String response2 = event.getContentIfNotHandled();
        String response3 = event.peekContent();

        assertThat(response2).isNull();
        assertThat(response3).isEqualTo(content);
    }

}