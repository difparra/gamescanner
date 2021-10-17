package com.diegoparra.gamescanner.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class Resource<T> {

    @NonNull
    private final Status status;
    @Nullable
    private final T data;
    @Nullable
    private final Throwable error;

    private Resource(@NonNull Status status, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    @NonNull
    public static <T> Resource<T> Loading() {
        return new Resource<>(Status.LOADING, null, null);
    }

    @NonNull
    public static <T> Resource<T> Success(T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    @NonNull
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource<?> resource = (Resource<?>) o;
        return status == resource.status && Objects.equals(data, resource.data) && Objects.equals(error, resource.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, data, error);
    }
}
