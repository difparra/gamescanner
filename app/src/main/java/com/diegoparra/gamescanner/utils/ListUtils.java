package com.diegoparra.gamescanner.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ListUtils {

    @NonNull
    public static <T, R> List<R> map(@NonNull List<T> list, @NonNull Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(list);
        Objects.requireNonNull(mapper);

        List<R> newList = new ArrayList<R>(list.size());
        for (T element : list) {
            newList.add(mapper.apply(element));
        }
        return newList;
    }

    @Nullable
    public static <T> T find(@NonNull List<T> list, @NonNull Function<? super T, Boolean> block) {
        Objects.requireNonNull(list);
        Objects.requireNonNull(block);

        for (T element : list) {
            if (block.apply(element)) {
                return element;
            }
        }
        return null;
    }

    @NonNull
    public static <T> String joinToString(@NonNull List<T> list, @NonNull Function<T, String> print, @NonNull String separator) {
        Objects.requireNonNull(list);
        Objects.requireNonNull(print);
        Objects.requireNonNull(separator);

        StringBuilder str = new StringBuilder();
        for (T element : list) {
            String printStr = print.apply(element);
            str.append(printStr).append(separator);
        }
        str.delete(str.length() - separator.length(), str.length());
        return str.toString();
    }

    @NonNull
    public static <T> String joinToString(@NonNull List<T> list, @NonNull String separator) {
        return joinToString(list, Objects::toString, separator);
    }

}
