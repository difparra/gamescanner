package com.diegoparra.gamescanner.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class Resource<T> {

    @NonNull private Status status;
    @Nullable private T data;
    @Nullable private Throwable error;

    private Resource(@NonNull Status status, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static <T> Resource<T> Loading() {
        return new Resource<>(Status.LOADING, null, null);
    }

    public static <T> Resource<T> Success(T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> Error(Throwable throwable) {
        return new Resource<>(Status.ERROR, null, throwable);
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }

    public enum Status {
        LOADING,
        SUCCESS,
        ERROR
    }
}
